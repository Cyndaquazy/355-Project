/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.dfa;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author cyndaquazy
 */
public class DFA
{
    private final HashMap<Integer, State> states;
    private final ArrayList<Integer> acceptingStates;
    private final String alphabet;
    private State currentState;
    
    static final int INITIAL_STATE_ID = 0;
    
    /**
     * Initiates a new deterministic finite-state automaton over the given alphabet with an empty state set.
     * @param alphabet The alphabet the DFA operates over.
     * @param acceptingStates The list of accepting states the DFA has.
     */
    public DFA(String alphabet, ArrayList<Integer> acceptingStates)
    {
        states = new HashMap<>();
        this.acceptingStates = acceptingStates;
        this.alphabet = alphabet;
    }
    
    /**
     * Simulates this DFA on a single character read, updating the current state as appropriate.
     * @param c 
     */
    public void readCharacter(char c)
    {
        currentState = currentState.transitionOn(c);
    }
    
    /**
     * Adds a new character-transition to the DFA.
     * @param fromID the identifier of the state to transition from.
     * @param toID the identifier of the state to transition to.
     * @param onChar the character the transition occurs on.
     */
    public void addTransition(int fromID, int toID, char onChar)
    {
        State from = states.get(fromID);
        State to = states.get(toID);
        from.addTransition(onChar, to);
    }
    
    /**
     * Creates a new state with the given identifier then adds the empty state to this DFA.
     * @param identifier the identifier of the state to add.
     */
    public void allocateNewState(int identifier)
    {
        State s = new State(identifier, acceptingStates.contains(identifier));
        states.put(identifier, s);
    }
    
    /**
     * Simulates this DFA on the given input string starting at the initial state.
     * @param input The input string to simulate the DFA over.
     * @return Whether or not the input string is recognized.
     */
    public boolean doesAccept(String input)
    {
        int stateFinal = partialRead(INITIAL_STATE_ID, input);
        
        return states.get(stateFinal).isAccepting();
    }
    
    /**
     * Simulates this DFA on the given input string starting from the given state.
     * @param startID The identifier of the state to start the simulation from.
     * @param input
     * @return 
     */
    public int partialRead(int startID, String input)
    {
        currentState = states.get(startID);
        
        for (char c : input.toCharArray())
        {
            readCharacter(c);
        }
        
        return currentState.getIdentifier();
    }
    
    /**
     * Provides the string representation of this DFA as a block of text detailing the size of DFA, its accepting
     * states, its alphabet, and its transition function in tabular form.
     * @return The string representation of this DFA.
     */
    @Override
    public String toString()
    {
        String newline = System.lineSeparator();
        StringBuilder builder = new StringBuilder();
        
        builder.append("Number of states: ").append(states.size()).append(newline);
        builder.append("Accepting states:");
        
        for (int i : acceptingStates)
        {
            builder.append(" ").append(i);
        }
        
        builder.append(newline).append("Alphabet: ").append(alphabet).append(newline);
        
        for (char c : this.alphabet.toCharArray())
        {
            builder.append(c).append(" ");
        }
        
        builder.append(newline);
        
        for (int i = 0; i < states.keySet().size(); i++)
        {
            State s = states.get(i);
            
            for (char c : this.alphabet.toCharArray())
            {
                builder.append(s.transitionOn(c).getIdentifier()).append(" ");
            }
            
            builder.append(newline);
        }
        
        return builder.toString().trim();
    }
    
    HashMap<Integer, State> getStates()
    {
        return (HashMap<Integer, State>)states.clone();
    }
    
    ArrayList<Integer> getFinalStates()
    {
        return (ArrayList<Integer>)acceptingStates.clone();
    }
    
    String getAlphabet()
    {
        return alphabet;
    }
}
