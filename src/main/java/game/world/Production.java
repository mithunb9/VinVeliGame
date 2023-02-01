/**
 * This class is used to produce objects of entity and behavior target types to use
 * throughout the game. Also includes methods to find the target objects and types
 * of different objects, and can also find out the type of a chosen object as well.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.world;

import main.java.game.entities.Entity;
import main.java.game.entities.WarshipEntity;
import main.java.game.events.Behavior;

public class Production {
    private Entity entityTarget;
    private Behavior behaviorTarget;
    private boolean isEntityType = false;

    /**
     * One of two constructors for the class based on the entityTarget of the object.
     * @param entityTarget the target for the object.
     */
    public Production(Entity entityTarget) {
        this.entityTarget = entityTarget;
        isEntityType = true;
    }

    /**
     * Two of two constructors for the class based on the behaviorTarget of the object.
     * @param behaviorTarget the target for the object.
     */
    public Production(Behavior behaviorTarget) {
        this.behaviorTarget = behaviorTarget;
        isEntityType = false;
    }

    /**
     * gets the behaviorTarget of the chosen object.
     * @return the Behavior target.
     */
    public Behavior getBehaviorTarget() {
        return behaviorTarget;
    }

    /**
     * gets the Entity Target of the chosen object.
     * @return the Entity target.
     */
    public Entity getEntityTarget() {
        return entityTarget;
    }

    /**
     * returns true or false on if the chosen object is Entity type.
     * @return true if EntityType, false if not.
     */
    public boolean isEntityType() {
        return isEntityType;
    }

    /**
     * If the object is entity target, it will return string of warship, else it will return
     * string saying improvement.
     * @return String of either warship or improvement.
     */
    @Override
    public String toString() {
        if (isEntityType) {
            if (entityTarget instanceof WarshipEntity) return "Warship";
        } else {
            return "Improvement";
        }

        return null;
    }
}
