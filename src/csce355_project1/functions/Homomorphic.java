/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.functions;

import csce355_project1.Messenger;
import csce355_project1.dfa.DFA;
import csce355_project1.dfa.DFABuilder;
import csce355_project1.dfa.Homomorphism;

/**
 *
 * @author cyndaquazy
 */
public class Homomorphic
{
    public static void run(String dfaFileName, String homomorphFileName)
    {
        try
        {
            DFA dfa = DFABuilder.loadFromFile(dfaFileName);
            
            Homomorphism homo = DFABuilder.loadHomomorphismFromFile(homomorphFileName);
            
            String homoInput = homo.getInputAlphabet();
            
            DFA inverseHomo = new DFA(homoInput, dfa.getFinalStates());
            
            for (int i = 0; i < dfa.getNumberOfStates(); i++)
            {
                inverseHomo.allocateNewState(i);
            }
            
            for (int i = 0; i < dfa.getNumberOfStates(); i++)
            {
                for (char c : homoInput.toCharArray())
                {
                    String output = homo.evaluate(c);
                    int transID = dfa.partialRead(i, output);
                    
                    inverseHomo.addTransition(i, transID, c);
                }
            }
            
            System.out.println(inverseHomo.toString());
            
            
        }
        catch (Exception e)
        {
            Messenger.error(e.getMessage());
        }
    }
}
