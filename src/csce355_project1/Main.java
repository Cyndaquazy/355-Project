package csce355_project1;

import csce355_project1.functions.Analyzer;
import csce355_project1.functions.ComplementIntersect;
import csce355_project1.functions.Homomorphic;
import csce355_project1.functions.Minimizer;
import csce355_project1.functions.Simulator;
import csce355_project1.functions.TextSearch;
import java.io.InputStream;
import java.io.PrintStream;
/**
 * 
 * @author cyndaquazy
 */
public class Main
{
    private static final PrintStream stderr = System.err;
    private static final InputStream stdin = System.in;
    private static final PrintStream stdout = System.out;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if (args.length <= 1)
        {
            Messenger.printUsage();
            System.exit(0);
        }
        
        /* JAVA 6 Source -- compatible with Linux Lab machines */
        String firstArg = args[0];
        
        if (firstArg.equals("simulate") || firstArg.equals("s"))
        {
            if (args.length < 3) { Messenger.printUsage(); System.exit(0); }
                
            Simulator.run(args[1], args[2]);
        }
        else if (firstArg.equals("minimize") || firstArg.equals("m"))
        {
            Minimizer.run(args[1]);
        }
        else if (firstArg.equals("textsearch") || firstArg.equals("t"))
        {
            TextSearch.run(args[1]);
        }
        else if (firstArg.equals("compl-int") || firstArg.equals("c"))
        {
            ComplementIntersect.run(args);
        }
        else if (firstArg.equals("homomorphic") || firstArg.equals("h"))
        {
            if (args.length < 3) { Messenger.printUsage(); System.exit(0);}
                
            Homomorphic.run(args[1], args[2]);
        }
        else if (firstArg.equals("analyze") || firstArg.equals("a"))
        {
            Analyzer.run(args[1]);
        }
        
        /* JAVA 8 Source -- incompatible with Linux Lab machines' Java 6.
        switch (args[0])
        {
            case "simulate":
            case "s":
                if (args.length < 3) { Messenger.printUsage(); System.exit(0); }
                
                Simulator.run(args[1], args[2]);
                break;
                
            case "minimize":
            case "m":
                Minimizer.run(args[1]);
                break;
                
            case "textsearch":
            case "t":
                TextSearch.run(args[1]);
                break;
                
            case "compl-int":
            case "c":
                ComplementIntersect.run(args);
                break;
                
            case "homomorphic":
            case "h":
                if (args.length < 3) { Messenger.printUsage(); System.exit(0);}
                
                Homomorphic.run(args[1], args[2]);
                break;
                
            case "analyze":
            case "a":
                Analyzer.run(args[1]);
                break;
                
            default:
                Messenger.error("Invalid function switch given.");
                Messenger.printUsage();
                break;
        }
        */
    }
    
}
