/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.dfa;

import csce355_project1.Messenger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cyndaquazy
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
        DFA theDFA = new DFA(alphabet, acceptingStates);
        
        // Preallocate all states.
        for (int stateID = 0; stateID < numberStates; stateID++)
        {
            theDFA.allocateNewState(stateID);
        }
        
        // Then insert transitions.
        for (int stateID = 0; stateID < numberStates; stateID++)
        {
            String[] transitions = reader.readLine().split("\\s+");
            
            for (int transIdx = 0; transIdx < transitions.length; transIdx++)
            {
                theDFA.addTransition(stateID, Integer.parseInt(transitions[transIdx]), alphabet.charAt(transIdx));
            }
        }
        
        return theDFA;
    }
    
    /**
     * Transforms the given DFA into a sane DFA. A sane DFA is a DFA where all states are accessible from the initial state.
     * @param insane The DFA to make sane.
     * @return An equivalent sane DFA.
     */
    public static DFA makeSaneDFA(DFA insane)
    {
        // Collect some of the basic properties from the original DFA.
        String originalAlphabet = insane.getAlphabet();
        char[] originalAlphabetArray = originalAlphabet.toCharArray();
        HashMap<Integer, State> originalStates = insane.getStates();
        
        // Create a list for storing reachable states and initialize it with the INITIAL_STATE.
        ArrayList<Integer> reachableStates = new ArrayList<Integer>();
        reachableStates.add(DFA.INITIAL_STATE_ID);
        
        // Also create a new list of final states, in case some in the original set are dropped.
        ArrayList<Integer> newFinals = new ArrayList<Integer>();
        
        // Now, go through every reachable state and add in all other new states directly reachable from it.
        // This loop iterates over the size of the reachableStates array, which may grow as new states are found.
        for (int idx = 0; idx < reachableStates.size(); idx++)
        {
            int stateID = reachableStates.get(idx);
            
            // If the current state was accepting in the old DFA, make it accepting in the new DFA.
            if (insane.isAcceptingState(stateID))
            {
                newFinals.add(stateID);
            }
            
            // Loop through the alphabet and add all states the current state transitions into to the list of reachable
            // states (provided they haven't been added yet).
            for (char c : originalAlphabetArray)
            {
                int reachableID = insane.partialRead(stateID, c);
                
                if (!reachableStates.contains(reachableID))
                {
                    reachableStates.add(reachableID);
                }
            }
        }

        // Propery initialize the new saneDFA object.
        DFA saneDFA = new DFA(originalAlphabet, newFinals);
        
        // Add to that DFA only the states reachable from the start state.
        // We can be sure that even when adding the states directly that there are no lost references, since if a state
        // is pointed by a state in the reachable set, then it is also in the reachable set.
        for (int stateID : reachableStates)
        {
            saneDFA.addStateDirectly(originalStates.get(stateID));
        }
        
        return saneDFA;
    }
}
