/**
 * The Behavior class acts as an interface, working to implement both active behaviors and passive
 * behaviors in the game.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.events;

public interface Behavior {
    /**
     * Returns whether the behavior is active
     * @return true if the behavior is active, false if not
     */
    public boolean isActive();
}
