import java.util.HashMap;
import java.util.HashSet;

/**
 * This class implements the inverse homomorphic image functionality. It accepts
 * a DFA and a Homomorphism and produces a DFA that is the inverse homomorphic
 * image of the original DFA.
 * 
 * @author Myndert Papenhuyzen
 */
public class Homomorphic
{
    public static void run(String dfaFileName, String homomorphFileName)
    {
        try
        {
            DFA dfa = DFABuilder.loadFromFile(dfaFileName);
            
            Homomorphism homo = DFABuilder.loadHomomorphismFromFile(homomorphFileName);
            
            String homomorphInput = homo.getInputAlphabet();
            
            // First go through an associate a number with each State in the original DFA.
            HashSet<State> Q = dfa.getStates();
            HashMap<State, Integer> numberMappings = new HashMap<State, Integer>();
            HashMap<Integer, State> reverseMappings = new HashMap<Integer, State>();
            State q_naught = dfa.getInitialState();
            
            numberMappings.put(q_naught, 0);
            reverseMappings.put(0, q_naught);

            int nextInteger = 1;

            for (State s : Q)
            {
                if (!numberMappings.keySet().contains(s))
                {
                    numberMappings.put(s, nextInteger);
                    reverseMappings.put(nextInteger, s);
                    nextInteger++;
                }
            }
            
            //Instantiate structures for the inverse homomorphic-image DFA.
            HashSet<State> Qprime = new HashSet<State>();
            HashMap<Integer, State> idMap = new HashMap<Integer, State>();

            // Preallocate all states in Qprime.
            for (int stateID = 0; stateID < Q.size(); stateID++)
            {
                State state = new State(reverseMappings.get(stateID).isFinal());

                Qprime.add(state);
                idMap.put(stateID, state);
            }

            // Then insert transitions, using the numerical identifiers as symbols of equivalence.
            for (int stateID = 0; stateID < Qprime.size(); stateID++)
            {
                State homo_state = idMap.get(stateID);
                State orig_state = reverseMappings.get(stateID);
                
                for (char c : homomorphInput.toCharArray())
                {
                    String homoMorph = homo.evaluate(c);
                    State ending_state = dfa.deltaHat(orig_state, homoMorph);
                    
                    int ending_stateID = numberMappings.get(ending_state);
                    State homo_ending = idMap.get(ending_stateID);
                    
                    homo_state.addTransition(c, homo_ending);
                }
            }
            
            DFA inverseHomo = new DFA(Qprime, homomorphInput, idMap.get(0));
            
            System.out.println(inverseHomo.toString());
            
            
        }
        catch (Exception e)
        {
            Messenger.error(e.getMessage());
        }
    }
}
