/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.functions;

import csce355_project1.Messenger;
import csce355_project1.dfa.DFA;
import csce355_project1.dfa.DFABuilder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cyndaquazy
 */
public class Minimizer
{
    public static void run(String descriptionFileName)
    {
        try
        {
            // Load the DFA from the file specification.
            DFA dfa = DFABuilder.loadFromFile(descriptionFileName);
            
            DFA minDFA = minimize(dfa);
            
            // Print the new minimal DFA to standard out.
            System.out.println(minDFA.toString());
            
        }
        catch (Exception e)
        {
            
        }
    }
    
    public static DFA minimize(DFA dfa)
    {
        try
        {
            // Make that DFA sane (i.e. remove all states not accessible from the start state).
            DFA saneDFA = DFABuilder.makeSaneDFA(dfa);
            
            // Gather some properties of that sane DFA: number of states, its final states, and its alphabet.
            int numberSaneStates = saneDFA.getNumberOfStates();
            String alphabet = saneDFA.getAlphabet();
            
            // Initialize the distinguishables table.
            boolean distinguished[][] = new boolean[numberSaneStates][numberSaneStates];
            
            // First, distinguish the accepting states from the non-accepting ones.
            for (int p = 0; p < numberSaneStates; p++)
            {
                for (int q = 0; q < numberSaneStates; q++)
                {
                    boolean pFinal = saneDFA.isAcceptingState(p);
                    boolean qFinal = saneDFA.isAcceptingState(q);
                    
                    distinguished[p][q] = pFinal ^ qFinal; // NOTE: ^, normally bitwise XOR, is override for booleans to also provide logical XOR functionality.
                }
            }
            
            // Create a flag that keeps track when the distin. table is updated.
            boolean markedNewGrid;
            
            do
            {
                markedNewGrid = false; // Every new iteration in the table, reset the update flag to no changes.
                
                // Now, loop through the table (and DFA) to find like and different states...
                for (int p = 0; p < numberSaneStates-1; p++)
                {
                    for (int q = p+1; q < numberSaneStates; q++)
                    {
                        if (!distinguished[p][q])
                        {
                            // If two states aren't marked as distinguished, see if any transistion are marked.
                            for (char c : alphabet.toCharArray())
                            {
                            
                                int pTran = saneDFA.partialRead(p, c);
                                int qTran = saneDFA.partialRead(q, c);
                                
                                if (distinguished[pTran][qTran])
                                {
                                    // One pair of the transitions are distinguished, then the original states can be distinguished.
                                    // Be sure to set the flag indicating the table was updated.
                                    distinguished[p][q] = true;
                                    distinguished[q][p] = true;
                                    markedNewGrid = true;
                                    break; // breaks char for loop.
                                }
                            }
                        }
                    }
                }
            } while (markedNewGrid);
            
            
            /*//[---
            // One no more new pairs were marked, print distinguishable table to standard error (System.err)
            System.err.print("   |");
            
            for (int i = 0; i < numberSaneStates; i++) System.err.printf(" %2d", i);
            
            System.err.println();
            System.err.println();
            
            for (int p = 0; p < numberSaneStates; p++)
            {
                System.err.printf("%2d |", p);
                for (int q = 0; q < numberSaneStates; q++)
                {
                    boolean different = distinguished[p][q];
                    
                    System.err.printf(" %s", different?"**":"  ");
                }
                System.err.println();
            }
            //---]*/
            
            // Now its time to merge the states. To do so, there are three structures:
            //    An ArrayList that keeps track of states that have already been added, to ensure that the final DFA contains only distinguishable states.
            //    An ArrayList that keeps track of final states that have been kept (since some final states may be merged into existing ones).
            //    A HashMap that maps former states to their indistinguishable equivalent that has already been added.
            ArrayList<Integer> addedStates = new ArrayList<Integer>();
            ArrayList<Integer> addedFinals = new ArrayList<Integer>();
            
            HashMap<Integer, Integer> indistinguishables = new HashMap<Integer, Integer >();
            
            for (int p = 0; p < numberSaneStates; p++)
            {
                boolean currentlyUnique = true;
                boolean isFinal = saneDFA.isAcceptingState(p);
                
                // For every possible state, see if an equivalent has already been added.
                for (int q : addedStates)
                {
                    if (!distinguished[p][q])
                    {
                        // If so, map this state to its equivalent.
                        currentlyUnique = false;
                        indistinguishables.put(p, q);
                        break; // Break for (int q : addedStates)
                    }
                }
                
                // If the current state has no equivalents already added, add this state. If it accepts, then add it to the addedFinals ArrayList.
                if (currentlyUnique)
                {
                    addedStates.add(p);
                    
                    if (isFinal) addedFinals.add(p);
                }
            }
            
            // Finally, initialize a new DFA that will become the minimal one.
            DFA minimalDFA = new DFA(alphabet, addedFinals);
            
            // addedStates now contains only pair-wise distinguishable states, so allocate new states for them in the minimal DFA.
            for (int stateID : addedStates)
            {
                minimalDFA.allocateNewState(stateID);
            }
            
            // Lastly, add in the transitions to make it a functional automaton.
            for (int stateID : addedStates)
            {
                for (char c : alphabet.toCharArray())
                {
                    int trans = saneDFA.partialRead(stateID, c); // Get the original transition destination from the current state on the current character.
                    
                    if (addedStates.contains(trans)) // If that original is in the disting. list, then simply "copy" the transition to the minimal DFA. 
                    {
                        minimalDFA.addTransition(stateID, trans, c);
                    }
                    else
                    {
                        // If the original destination was indistinguishable from another state, reroute the transition to that new state.
                        int distinguishedState = indistinguishables.get(trans);
                        minimalDFA.addTransition(stateID, distinguishedState, c);
                    }
                }
            }
            
            return minimalDFA;
            
        }
        catch (Exception e)
        {
            Messenger.error(e.getMessage());
        }
        
        return null;
    }
}
