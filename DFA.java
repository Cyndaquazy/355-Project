import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents a DFA. It keeps track of the state set, the initial
 * state and the alphabet. The actual transitions are handled by the State class.
 * 
 * This class is also responsible for generating the text representation of the
 * DFA according to the specifications laid out by Dr. Fenner.
 * 
 * @author Myndert Papenhuyzen
 */
public class DFA
{
    HashSet<State> Q;
    State q_naught;
    String sigma;
    
    public DFA(HashSet<State> Q, String sigma, State q_naught)
    {
        if (!Q.contains(q_naught))
        {
            throw new IllegalArgumentException("Initial state was not found in state set.");
        }
        
        this.Q = Q;
        this.q_naught = q_naught;
        this.sigma = sigma;
    }
    
    public HashSet<State> getStates()
    {
        return this.Q;
    }
    
    public State getInitialState()
    {
        return this.q_naught;
    }
    
    public String getAlphabet()
    {
        return this.sigma;
    }
    
    public void setAlphabet(String newSigma)
    {
        this.sigma = newSigma;
    }
    
    public State deltaHat(String input)
    {
        return deltaHat(q_naught, input);
    }
    
    public State deltaHat(State start, String input)
    {
        if (!Q.contains(start))
        {
            throw new IllegalArgumentException("Start state given is not in State_New set!");
        }
        
        State current = start;
        
        for (char c : input.toCharArray())
        {
            current = current.doTransition(c);
        }
        
        return current;
    }
    
    @Override
    public String toString()
    {
        HashMap<State, Integer> numberMappings = new HashMap<State, Integer>();
        HashMap<Integer, State> reverseMappings = new HashMap<Integer, State>();
        ArrayList<Integer> finalsIDs = new ArrayList<Integer>();
        
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
            
            if (s.isFinal())
            {
                finalsIDs.add(numberMappings.get(s));
            }
        }
        
        StringBuilder output = new StringBuilder();
        
        output.append(String.format("Number of states: %d", Q.size())).append("\n");
        output.append("Accepting states:");
        
        for (int i : finalsIDs)
        {
            output.append(String.format(" %d", i));
        }
        
        output.append("\n");
        output.append(String.format("Alphabet: %s", sigma)).append("\n");
        
        for (int id = 0; id < nextInteger; id++)
        {
            State s = reverseMappings.get(id);
            
            for (char c : sigma.toCharArray())
            {
                State nextState_New = s.doTransition(c);
                output.append(String.format("%d ", numberMappings.get(nextState_New)));
            }
            
            output.append("\n");
        }
        
        return output.toString();
    }
}
