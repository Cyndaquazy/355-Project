/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.functions;

import csce355_project1.Messenger;
import csce355_project1.dfa.DFA;
import csce355_project1.dfa.DFABuilder;
import java.util.HashSet;

/**
 *
 * @author cyndaquazy
 */
public class Analyzer
{
    public static void run(String descriptionFileName)
    {
        Messenger.info("Analyzing");
        
        try
        {
            DFA insaneDFA = DFABuilder.loadFromFile(descriptionFileName);
            
            DFA dfa = DFABuilder.makeSaneDFA(insaneDFA);
            
            char[] alphabetArray = dfa.getAlphabet().toCharArray();
            HashSet<Integer> foundStates = new HashSet<Integer>();
            HashSet<Integer> statesLoopedTo = new HashSet<Integer>();
            HashSet<Integer> finalStates = new HashSet<Integer>();
            
            foundStates.add(DFA.INITIAL_STATE_ID);
            
            boolean foundLoop = false;
            
            // Now, go through every reachable state and add in all other new states directly reachable from it.
            // This loop iterates over the size of the reachableStates array, which may grow as new states are found.
            for (int idx = 0; idx < foundStates.size(); idx++)
            {
                int stateID = foundStates.iterator().next();

                // If the current state was accepting in the old DFA, make it accepting in the new DFA.
                if (dfa.isAcceptingState(stateID))
                {
                    finalStates.add(stateID);
                }

                // Loop through the alphabet and add all states the current state transitions into to the list of reachable
                // states (provided they haven't been added yet).
                for (char c : alphabetArray)
                {
                    int reachableID = dfa.partialRead(stateID, c);

                    if (!foundStates.contains(reachableID))
                    {
                        foundStates.add(reachableID);
                    }
                    else
                    {
                        statesLoopedTo.add(reachableID);
                    }
                }
            }
            
            
        }
        catch (Exception e)
        {
            Messenger.error(e.toString());
        }
    }
}
