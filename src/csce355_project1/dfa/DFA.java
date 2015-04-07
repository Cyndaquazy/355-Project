/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.dfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
    
    public static final int INITIAL_STATE_ID = 0;
    
    /**
     * Initiates a new deterministic finite-state automaton over the given alphabet with an empty state set.
     * @param alphabet The alphabet the DFA operates over.
     * @param acceptingStates The list of accepting states the DFA has.
     */
    public DFA(String alphabet, ArrayList<Integer> acceptingStates)
    {
        states = new HashMap<Integer, State>();
        this.acceptingStates = acceptingStates;
        this.alphabet = alphabet;
    }
    
    /**
     * Simulates this DFA on a single character read, updating the current state as appropriate.
     * @param c 
     */
    private void readCharacter(char c)
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
        
        for (char c : alphabet.toCharArray())
        {
            s.addTransition(c, s);
        }
    }
    
    /**
     * Simulates this DFA on the given input string starting at the initial state.
     * @param input The input string to simulate the DFA over.
     * @return Whether or not the input string is recognized.
     */
    public boolean doesAccept(String input)
    {
        return partialDoesAccept(INITIAL_STATE_ID, input);
    }
    
    /**
     * Simulates this DFA on the given character starting at the given state.
     * @param startID The state to start simulating from.
     * @param input The character to advance on.
     * @return Whether or not the input string is recognized.
     */
    public boolean partialDoesAccept(int startID, char input)
    {
        int stateFinal = partialRead(startID, input);
        
        return states.get(stateFinal).isAccepting();
    }
    
    /**
     * Simulates this DFA on the given input string starting at the given state.
     * @param startID TEh state to start simulating from.
     * @param input The input string to simulate the DFA over.
     * @return Whether or not the input string is recognized.
     */
    public boolean partialDoesAccept(int startID, String input)
    {
        int stateFinal = partialRead(startID, input);
        
        return states.get(stateFinal).isAccepting();
    }
    
    /**
     * Simulates this DFA on the given input string starting from the given state.
     * @param startID The identifier of the state to start the simulation from.
     * @param input The input string to read.
     * @return The end state after reading the input.
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
     * Simulates this DFA on the given character starting from the given state.
     * @param startID The identifier of the state to start the simulation from.
     * @param input The character to read.
     * @return The end state after reading the input.
     */
    public int partialRead(int startID, char input)
    {
        return partialRead(startID, String.valueOf(input));
    }
    
    /**
     * Returns whether or not the given state is an accepting state for the DFA.
     * NOTE: if the state is not in the DFA, this function will return {@code false}.
     * @param stateID The state in question.
     * @return Whether or not the state is accepting or not.
     */
    public boolean isAcceptingState(int stateID)
    {
        if (states.containsKey(stateID))
        {
            return states.get(stateID).isAccepting();
        }
        else
        {
            return false;
        }
    }
    
    public int getNumberOfStates()
    {
        return states.size();
    }
    
    /**
     * Provides the string representation of this DFA as a block of text detailing the size of DFA, its accepting
     * states, its alphabet, and its transition function in tabular form.
     * @return The string representation of this DFA.
     */
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator", "\n");
        StringBuilder builder = new StringBuilder();
        
        builder.append("Number of states: ").append(states.size()).append(newline);
        builder.append("Accepting states:");
        
        for (int i : acceptingStates)
        {
            builder.append(" ").append(i);
        }
        
        builder.append(newline).append("Alphabet: ").append(alphabet).append(newline);
        
        Set<Integer> keySet = states.keySet();
        
        for (int i : keySet)
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
    
    
    /**
     * Directly gets the state set for this DFA.
     * @return The state set for this DFA.
     */
    public HashMap<Integer, State> getStates()
    {
        return states;
    }
    
    /**
     * Gets the final states for this DFA.
     * @return the final states for this DFA.
     */
    public ArrayList<Integer> getFinalStates()
    {
        return acceptingStates;
    }
    
    /**
     * Gets the alphabet for this DFA.
     * @return the alphabet for this DFA.
     */
    public String getAlphabet()
    {
        return alphabet;
    }
    
    /**
     * Directly adds the given state to this DFA.
     * 
     * Use this function only when you are sure that transitions out of the provided state will point to a state in
     * the DFA. Instead, use the {@link allocateNewState} function to create a new state and add transitions using
     * {@link addTransition}.
     * @param s The state to add.
     */
    void addStateDirectly(State s)
    {
        states.put(s.getIdentifier(), s);
    }
}
