/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.functions;

import csce355_project1.Messenger;
import csce355_project1.dfa.DFA;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author cyndaquazy
 */
public class TextSearch
{
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static final char[] alchars = alphabet.toCharArray();
    
    public static void run(String searchStringFileName)
    {
        try
        {
            Scanner l = new Scanner(new FileInputStream(searchStringFileName));
            String searchString = l.nextLine().trim();

            Messenger.info(searchString);

            l.close();

            int numberStates = searchString.length() + 1;
            Messenger.info(String.valueOf(numberStates));
            ArrayList<Integer> finals = new ArrayList<Integer>();
            finals.add(numberStates - 1);

            ArrayList<String> prefixes = generatePrefixes(searchString);

            DFA dfa = new DFA(alphabet, finals);

            for (int i = 0; i < numberStates; i++)
            {
                dfa.allocateNewState(i);
            }

            dfa.addTransition(0, 1, searchString.charAt(0));

            for (int i = 1; i < numberStates-1; i++)
            {
                StringBuilder prefixRead = new StringBuilder(searchString.substring(0, 0+i));

                Messenger.info(prefixRead.toString());
                
                for (char c : alchars)
                {
                    if (c == searchString.charAt(i))
                    {
                        dfa.addTransition(i, i+1, c);
                    }
                    else
                    {
                        prefixRead.append(c);
                        int s = findLongestMatchingPrefixForSuffixOfRead(prefixRead, prefixes);

                        dfa.addTransition(i, s, c);

                        prefixRead.deleteCharAt(prefixRead.length()-1);
                    }
                }
            }

            System.out.println(dfa.toString());
        }
        catch (Exception e)
        {
            Messenger.error(e.getMessage());
        }
    }
    
    private static ArrayList<String> generatePrefixes(String s)
    {
        ArrayList<String> prefs = new ArrayList<String>();
        
        for (int len = 0; len <= s.length(); len++)
        {
            prefs.add(s.substring(0, len));
        }
        
        return prefs;
    }
    
    private static int findLongestMatchingPrefixForSuffixOfRead(StringBuilder prefixRead, ArrayList<String> prefixes)
    {
        int prefLen = prefixRead.length();
        int idxOfLongestMatchingPrefixOfSearchString = 0;
                
        for (int idx = prefLen-1; idx >= 0; idx--)
        {
            String suffix = prefixRead.substring(idx);
            
            int foundPref = prefixes.indexOf(suffix);
            
            if (foundPref > idxOfLongestMatchingPrefixOfSearchString)
            {
                idxOfLongestMatchingPrefixOfSearchString = foundPref;
            }
        }
        
        return idxOfLongestMatchingPrefixOfSearchString;
    }
}
