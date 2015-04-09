/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.PrintStream;

/**
 * This is a utility class for printing out information and error messages. It
 * has no real impact on the rest of the program.
 * 
 * @author Myndert Papenhuyzen
 */
public class Messenger
{
    private static final PrintStream stderr = System.err;
    private static final PrintStream stdout = System.out;
    
    public static void printUsage()
    {
        stdout.println("USAGE: java -jar proj1.jar <function> <arguments>\n");
        stdout.println("Functions:");
        stdout.println("    (s)imulate    dfa_description_file suspect_strings_file -- simulates a DFA created from the description on the given file of suspect strings.");
        stdout.println("    (h)omomorphic dfa_description_file homomorph_desc_file  -- will generate the homomorphic reverse image of the given DFA using the given homomorphism.");
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
