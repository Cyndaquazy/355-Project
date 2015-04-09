import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class implements the simulator functionality. It is in charge of loading
 * the DFA from the file provided and running through all of the strings in the
 * list provided and checking whether or not they are acceptable to the DFA.
 * 
 * @author Myndert Papenhuyzen
 */
public class Simulator
{
    public static void run(String dfaFileName, String textFileName)
    {
        
        try
        {
            DFA dfa = DFABuilder.loadFromFile(dfaFileName);
            
            BufferedReader reader = new BufferedReader(new FileReader(textFileName));
            
            while (reader.ready())
            {
                String input = reader.readLine().trim();
                boolean accept = dfa.deltaHat(input).isFinal();
                
                System.out.printf("%s", (accept ? "accept" : "reject"));
            }
        }
        catch (Exception e)
        {
            Messenger.error(e.getMessage());
        }
    }
}
