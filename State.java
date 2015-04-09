import java.util.HashMap;

/**
 * This class represents a state of a DFA. This class is responsible for keeping
 * track of the transitions out of this state.
 * @author Myndert Papenhuyzen
 */
public class State
{
    private HashMap<Character, State> delta;
    private boolean isFinal;
    
    public State(boolean isFinal)
    {
        delta = new HashMap<Character, State>();
        this.isFinal = isFinal;
    }
    
    public void addTransition(char c, State s)
    {
        delta.put(c, s);
    }
    
    public State doTransition(char c)
    {
        return delta.get(c);
    }
    
    public void setFinal(boolean f)
    {
        this.isFinal = f;
    }
    
    public boolean isFinal()
    {
        return this.isFinal;
    }
}
