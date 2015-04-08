/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.dfa;

import java.util.HashMap;

/**
 *
 * @author cyndaquazy
 */
public class State
{
    private final HashMap<Character, State> transitions;
    private int identifier;
    private boolean isAccepting;
    
    State(int id, boolean isAccepting)
    {
        transitions = new HashMap<Character, State>();
        this.identifier = id;
        this.isAccepting = isAccepting;
    }
    
    void addTransition(char c, State s)
    {
        transitions.put(c, s);
    }
    
    State transitionOn(char c)
    {
        return transitions.get(c);
    }
    
    public boolean isAccepting()
    {
        return this.isAccepting;
    }
    
    public void setAccepting(boolean b)
    {
        this.isAccepting = b;
    }
    
    public int getIdentifier()
    {
        return this.identifier;
    }
    
    public void setIdentifier(int i)
    {
        this.identifier = i;
    }
    
    @Override
    public String toString()
    {
        return String.format("ID:%d;FINAL:%s", identifier, (isAccepting)?"TRUE":"FALSE");
    }
}
