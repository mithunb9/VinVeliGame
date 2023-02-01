/**
 * The ActiveBehavior class contains active behaviors for objects within the game to utilize.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.events;

public class ActiveBehavior implements Behavior {
    private String behavior;

    /**
     * Constructor for the ActiveBehavior class
     * @param behavior the assigned behavior name
     */
    public ActiveBehavior(String behavior) {
        this.behavior = behavior;
    }

    /**
     * Returns whether the behavior is active
     * @return true, because the behavior is active
     */
    @Override
    public boolean isActive() {
        return true;
    }
}
