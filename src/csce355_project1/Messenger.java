/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1;

import java.io.PrintStream;

/**
 *
 * @author cyndaquazy
 */
public class Messenger
{
    private static final PrintStream stderr = System.err;
    private static final PrintStream stdout = System.out;
    
    public static void printUsage()
    {
        stdout.println("USAGE: java -jar csce355_project1.jar <function> <arguments>\n");
        stdout.println("Functions:");
        stdout.println("    (s)imulate    dfa_description_file suspect_strings_file -- simulates a DFA created from the description on the given file of suspect strings.");
        stdout.println("    (m)inimize    dfa_description_file                      -- minimizes a DFA created from the description. Output written to stdout.");
        stdout.println("    (t)extsearch  substring                                 -- creates a DFA to accept all strings containing the specified substring.");
        stdout.println("    (c)ompl-int   dfa_desc1_file       [dfa_desc2_file]     -- if only one DFA descrition given, then will complement the DFA. If two given, then will intersect the two.");
        stdout.println("    (h)omomorphic dfa_description_file homomorph_desc_file  -- will generate the homomorphic reverse image of the given DFA using the given homomorphism.");
        stdout.println("    (a)nalyze     dfa_description_file                      -- will analyze the given DFA and print out some properties of the automaton.");
    }
    
    public static void error(String message)
    {
        stderr.println(message);
    }
    
    public static void info(String message)
    {
        stdout.println(message);
    }
}
