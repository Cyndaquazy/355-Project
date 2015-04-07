/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.functions;

import csce355_project1.Messenger;
import csce355_project1.dfa.DFA;
import csce355_project1.dfa.DFABuilder;
import csce355_project1.dfa.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author cyndaquazy
 */
public class ComplementIntersect
{
    public static void run(String[] args)
    {
        try
        {
            switch (args.length)
            {
                case 2:
                    DFA dfa = DFABuilder.loadFromFile(args[1]);

                    complement(dfa);
                    
                    break;
                case 3:
                    DFA dfa1 = DFABuilder.loadFromFile(args[1]);
                    DFA dfa2 = DFABuilder.loadFromFile(args[2]);
                    
                    intersect(dfa1, dfa2);
                    
                    break;
                default:
                    Messenger.printUsage();
                    break;
            }
        }
        catch (Exception e)
        {
            Messenger.error(e.getMessage());
        }
    }
    
    private static void complement(DFA dfa)
    {
        HashMap<Integer, State> states = dfa.getStates();
        ArrayList<Integer> finals = dfa.getFinalStates();
        
        finals.clear();
        
        Collection<State> statesRaw = states.values();
        
        for (State s : statesRaw)
        {
            if (s.isAccepting())
            {
                s.setAccepting(false);
            }
            else
            {
                s.setAccepting(true);
                finals.add(s.getIdentifier());
            }
        }
        
        System.out.println(dfa.toString());
    }
    
    private static void intersect(DFA dfa1, DFA dfa2)
    {
        if (!dfa1.getAlphabet().equals(dfa2.getAlphabet()))
        {
            throw new IllegalArgumentException("The two DFA's must have the same alphabet!");
        }
        
        String alphabet = dfa1.getAlphabet();
        
        ArrayList<Integer> finals1 = dfa1.getFinalStates();
        ArrayList<Integer> finals2 = dfa2.getFinalStates();
        ArrayList<Integer> intersect = new ArrayList<Integer>();
        
        int size1 = dfa1.getNumberOfStates();
        int size2 = dfa2.getNumberOfStates();
        
        for (int i : finals1)
        {
            for (int j : finals2)
            {
                int newFinal = j*size1 + i;
                intersect.add(newFinal);
            }
        }
        
        DFA newDFA = new DFA(alphabet, intersect);
        
        for (int i = 0; i < size1; i++)
        {
            for (int j = 0; j < size2; j++)
            {
                int newID = j*size1 + i;
                
                newDFA.allocateNewState(newID);
            }
        }
        
        for (int i = 0; i < size1; i++)
        {
            for (int j = 0; j < size2; j++)
            {
                for (char c : alphabet.toCharArray())
                {
                    int transID1 = dfa1.partialRead(i, c);
                    int transID2 = dfa2.partialRead(j, c);
                    
                    int startTransID = j*size1 + i;
                    int newTransID = transID2*size1 + transID1;
                    
                    newDFA.addTransition(startTransID, newTransID, c);
                }
            }
        }
        
        DFA minimumDFA = Minimizer.minimize(newDFA);
        
        System.out.println(minimumDFA.toString());
    }
}
