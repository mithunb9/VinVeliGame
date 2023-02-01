/**
 * This class is used to initialize and test the passive behaviors of our game.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.events;

public class PassiveBehavior implements Behavior {
    String behavior;

    /**
     * Constructor that initializes the behavior.
     * @param behavior the behavior being initialized.
     */
    public PassiveBehavior(String behavior) {
        this.behavior = behavior;
    }

    /**
     * toString to return the behavior.
     * @return String of the behavior.
     */
    @Override
    public String toString() {
        return behavior;
    }

    /**
     * A boolean on whether or not the behavior being checked is active.
     * @return false, because the behavior is not active.
     */
    @Override
    public boolean isActive() {
        return false;
    }
}
