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
