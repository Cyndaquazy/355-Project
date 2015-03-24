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

/**
 *
 * @author cyndaquazy
 */
public class DFABuilder
{
    public static DFA loadFromFile(String fileName) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        
        int numberStates;
        ArrayList<Integer> acceptingStates = new ArrayList<>();
        String alphabet;
        
        
        // First line is the number of states, given as "Number of states: #"
        try
        {
            numberStates = Integer.parseInt(reader.readLine().substring(18));
            Messenger.info(numberStates + " states");
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
                Messenger.info(i + " accepts");
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
        Messenger.info("Sigma := " + alphabet);
        
        
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
                Messenger.info(String.format("%d -> %d on %s", stateID, Integer.parseInt(transitions[transIdx]), alphabet.charAt(transIdx)));
                theDFA.addTransition(stateID, Integer.parseInt(transitions[transIdx]), alphabet.charAt(transIdx));
            }
        }
        
        return theDFA;
    }
}
