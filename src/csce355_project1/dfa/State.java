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
class State
{
    private final HashMap<Character, State> transitions;
    private final int identifier;
    private final boolean isAccepting;
    
    State(int id, boolean isAccepting)
    {
        transitions = new HashMap<>();
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
    
    boolean isAccepting()
    {
        return this.isAccepting;
    }
    
    int getIdentifier()
    {
        return this.identifier;
    }
}