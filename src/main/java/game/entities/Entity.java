/**
 * The Entity class includes information of each entity in the game
 * which includes the various ships. It includes methods to obtain and set their icons,
 * x and y positions, health, and owner.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.entities;

import main.java.game.Player;

import java.awt.image.BufferedImage;

public abstract class Entity {
    private Player owner;
    private BufferedImage icon;
    private int hitPoints;
    private int movementPoints;
    private int x;
    private int y;

    /**
     * Constructor for the class that initializes the entity's icon, health, movement points,
     * and x and y position.
     * @param icon the icon for the objcect.
     * @param x the x position of the object.
     * @param y the y position of the object.
     * @param health the amount of health the object will have.
     * @param mvPoints the number of movement points the object will have.
     */
    public Entity(BufferedImage icon, int x, int y, int health, int mvPoints) {
        this.icon = icon;
        this.hitPoints = health;
        this.movementPoints = mvPoints;
        this.x = x;
        this.y = y;
    }

    /**
     * sets an entity's hitpoints to the number specified.
     * @param hitPoints the amount of health the entity will have.
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * gets the number of hitpoints of an entity.
     * @return an integer value of the hitpoints an entity has.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * sets the number of movements an entity has to the given parameter.
     * @param movementPoints the number of movements the entity should have.
     */
    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    /**
     * gets the number of movement points an entity can have.
     * @return an integer value of the number of movement points an entity can have.
     */
    public int getMovementPoints() {
        return movementPoints;
    }

    /**
     * sets the icon of the entity to the chosen icon in the parameter.
     * @param icon the new icon the entity's icon should be set to.
     */
    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    /**
     * gets the icon of the current entity.
     * @return a BufferedImage of the entity's icon.
     */
    public BufferedImage getIcon() {
        return icon;
    }

    /**
     * sets the owner of the entity to the parameter given.
     * @param owner the owner the entity should be given to.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * gets the owner of the entity.
     * @return the Player that is the owner of the entity.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * gets the Y position of the entity.
     * @return an integer value of the y position of the entity.
     */
    public int getY() {
        return y;
    }

    /**
     * gets the x position of the entity.
     * @return an integer value of the x position of the entity.
     */
    public int getX() {
        return x;
    }

    /**
     * sets the x position of the entity to given parameter.
     * @param x the value the x position should be set to.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * sets the y position of the entity to the given parameter.
     * @param y the value the y position should be set to.
     */
    public void setY(int y) {
        this.y = y;
    }


}
