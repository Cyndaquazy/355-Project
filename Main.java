
/**
 * This is the entry point for the program. It is responsible for initial argument
 * verification before delegating tasks to the appropriate parts of the program.
 * 
 * @author Myndert Papenhuyzen
 */
public class Main
{
    
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
        else if (firstArg.equals("homomorphic") || firstArg.equals("h"))
        {
            if (args.length < 3) { Messenger.printUsage(); System.exit(0);}
                
            Homomorphic.run(args[1], args[2]);
        }
        else
        {
            Messenger.error("Invalid function switch given.");
            Messenger.printUsage();
        }
        
        /* JAVA 8 Source -- incompatible with Linux Lab machines' Java 6.
        switch (args[0])
        {
            case "simulate":
            case "s":
                if (args.length < 3) { Messenger.printUsage(); System.exit(0); }
                
                Simulator.run(args[1], args[2]);
                break;
                
            case "homomorphic":
            case "h":
                if (args.length < 3) { Messenger.printUsage(); System.exit(0);}
                
                Homomorphic.run(args[1], args[2]);
                break;
               
            default:
                Messenger.error("Invalid function switch given.");
                Messenger.printUsage();
                break;
        }
        */
    }
    
}
