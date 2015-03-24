/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csce355_project1.functions;

import csce355_project1.Messenger;

/**
 *
 * @author cyndaquazy
 */
public class ComplementIntersect
{
    public static void run(String[] args)
    {
        switch (args.length)
        {
            case 2:
                Messenger.info("Complementing");
                break;
            case 3:
                Messenger.info("Intersecting");
                break;
            default:
                Messenger.printUsage();
                break;
        }
    }
}
