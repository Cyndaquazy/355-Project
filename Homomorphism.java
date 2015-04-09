import java.util.HashMap;

/**
 * This class describes a homomorphism.
 * 
 * @author Myndert Papenhuyzen
 */
public class Homomorphism
{
    private final HashMap<Character, String> h;
    private final String sigma;
    private final String gamma;
    
    public Homomorphism(String sigma, String gamma)
    {
        h = new HashMap<Character, String>();
        this.sigma = sigma;
        this.gamma = gamma;
    }
    
    public String getInputAlphabet()
    {
        return sigma;
    }
    
    public String getOutputAlphabet()
    {
        return gamma;
    }
    
    public String evaluate(char c)
    {
        return h.get(c);
    }
    
    void addMapping(char c, String s)
    {
        h.put(c, s);
    }
}
