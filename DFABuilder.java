import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This is a utility class that holds all of the functions related to reading and
 * building DFAs and Homomorphisms from text files.
 * 
 * @author Myndert Papenhuyzen
 */
public class DFABuilder
{
    /**
     * Instantiates and creates a DFA directly from a definitions file.
     * @param fileName The path to the DFA definition file.
     * @return A DFA that conforms to the definitions given.
     * @throws IOException If there is an error while reading from the file or parsing the data therein.
     */
    public static DFA loadFromFile(String fileName) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        
        int numberStates;
        ArrayList<Integer> acceptingStates = new ArrayList<Integer>();
        String alphabet;
        
        
        // First line is the number of states, given as "Number of states: #"
        try
        {
            numberStates = Integer.parseInt(reader.readLine().substring(18));
        }
        catch (NumberFormatException nfe)
        {
            Messenger.error("There is something wrong with the number of states.");
            throw new IOException("There is something wrong with the number of states.", nfe);
        }
        
        
        // Second line is the accepting states, given as "Accepting states: # # # ..."
        try
        {
            String[] states = reader.readLine().substring(18).split("\\s+");
            
            for (String s : states)
            {
                int i = Integer.parseInt(s);
                acceptingStates.add(i);
            }
        }
        catch (NumberFormatException nfe)
        {
            Messenger.error("There is something wrong with the accepting states list.");
            throw new IOException("There is something wrong with the accepting states list.", nfe);
        }
        
        
        // Third line is the alphabet string, given as "Alphabet: stringofchars"
        alphabet = reader.readLine().substring(10);
        
        
        // Now we can dynamically create the DFA!
        HashSet<State> Q = new HashSet<State>();
        HashMap<Integer, State> idMap = new HashMap<Integer, State>();
        
        // Preallocate all states.
        for (int stateID = 0; stateID < numberStates; stateID++)
        {
            State state = new State(acceptingStates.contains(stateID));
            
            Q.add(state);
            idMap.put(stateID, state);
        }
        
        // Then insert transitions.
        for (int stateID = 0; stateID < numberStates; stateID++)
        {
            State state = idMap.get(stateID);
            String[] transitions = reader.readLine().split("\\s+");
            
            for (int transIdx = 0; transIdx < transitions.length; transIdx++)
            {
                int otherID = Integer.parseInt(transitions[transIdx]);
                State other = idMap.get(otherID);
                
                state.addTransition(alphabet.charAt(transIdx), other);
            }
        }
        
        DFA theDFA = new DFA(Q, alphabet, idMap.get(0));
        
        return theDFA;
    }
    
    /**
     * Reads the given file and creates a homomorphism from it.
     * @param fileName The name of the file containing the homomorphism's definition.
     * @return A homomorphism as described in the file.
     * @throws IOException If an error occurs while the file is read.
     */
    public static Homomorphism loadHomomorphismFromFile(String fileName) throws IOException
    {
        BufferedReader read = new BufferedReader(new FileReader(fileName));
        
        String sigma = read.readLine().substring(16);
        String gamma = read.readLine().substring(17);
        
        Homomorphism homo = new Homomorphism(sigma, gamma);
        
        for (int i  = 0; i < sigma.length(); i++)
        {
            String output = read.readLine();
            homo.addMapping(sigma.charAt(i), output);
        }
        
        return homo;
    }
}
