/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.functions;

import csce355_project1.Messenger;
import csce355_project1.dfa.DFA;
import csce355_project1.dfa.DFABuilder;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author cyndaquazy
 */
public class Simulator
{
    public static void run(String dfaFileName, String textFileName)
    {
        Messenger.info("Simulating");
        
        try
        {
            DFA dfa = DFABuilder.loadFromFile(dfaFileName);
            
            BufferedReader reader = new BufferedReader(new FileReader(textFileName));
            
            while (reader.ready())
            {
                String input = reader.readLine().trim();
                boolean accept = dfa.doesAccept(input);
                
                Messenger.info(String.format("%s... %s", input, (accept ? "Accept" : "Reject")));
            }
        }
        catch (Exception e)
        {
            Messenger.error(e.getMessage());
        }
    }
}
