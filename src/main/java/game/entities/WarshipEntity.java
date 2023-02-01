/**
 * The WarshipEntity includes everything in the Entity class, while customizing the Entity object
 * with values specific to WarshipEntity objects, such
 * as weaponDamage, range, and rangeMultiplier.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */

package main.java.game.entities;

import main.java.GameManager;
import main.java.game.Player;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WarshipEntity extends Entity {
    private int weaponDamage;
    private Rectangle rangeBox;

    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;
    private static final int RANGEMULT = 7;

    /**
     * Constructor for the WarshipEntity class
     * @param icon the given icon for the warship
     * @param x the x coordinate for the warship
     * @param y the y coordinate for the warship
     * @param health the health points for the warship
     * @param mvPoints the movement points for the warship
     * @param damage the damage on the warship
     */
    public WarshipEntity(BufferedImage icon, int x, int y, int health, int mvPoints, int damage) {
        super(icon, x, y, health, mvPoints);
        this.weaponDamage = damage;

        int boxX = x - (WIDTH * (2 + 1));
        int boxY = y - (HEIGHT * (2 + 1));

        this.rangeBox = new Rectangle(boxX, boxY, WIDTH * RANGEMULT, HEIGHT * RANGEMULT);
    }

    /**
     * Sets the weapon damage for the warship
     * @param weaponDamage the given value for weapon damage
     */
    public void setWeaponDamage(int weaponDamage) {
        this.weaponDamage = weaponDamage;
    }

    /**
     * Gets the weapon damage for the warship
     * @return the given value for weapon damage
     */
    public int getWeaponDamage() {
        return weaponDamage;
    }

    /**
     * Clones a Warship object
     * @return the cloned warship object
     * @throws CloneNotSupportedException
     */
    @Override
    public WarshipEntity clone() throws CloneNotSupportedException {
        BufferedImage icon = super.getIcon();
        int x = super.getX();
        int y = super.getY();
        int hitPoints = super.getHitPoints();
        int movementPoints = super.getMovementPoints();

        return new WarshipEntity(icon, x, y, hitPoints, movementPoints, weaponDamage);
    }

    /**
     * Returns the object name
     * @return the object name
     */
    @Override
    public String toString() {
        return "Warship";
    }

    /**
     * Retrieves any enemy warships in range
     * @return an ArrayList of enemy warships in range
     */
    public ArrayList<WarshipEntity> getEnemyWarshipInRange() {
        ArrayList<WarshipEntity> output = new ArrayList<>();
        ArrayList<Entity> ships = GameManager.getOtherPlayer().getShips();

        for (Entity ship : ships) {
            if (rangeBox.contains(ship.getX(), ship.getY())) {
                ship.setHitPoints(ship.getHitPoints() - this.getWeaponDamage());
            }
        }

        return null;
    }

    /**
     * Checks whether the warship is within bounds of the game
     * @param x top x value of the ship's range
     * @param y top y value of the ship's range
     * @param x2 bottom x value of the ship's range
     * @param y2 bottom y value of the ship's range
     * @param checkX the x value of the ship to be checked
     * @param checkY the y value of the ship to be checked
     * @return true if the warship is within bounds, false if it is not
     */
    private boolean checkWithinBounds(int x, int y, int x2, int y2, int checkX, int checkY) {
        if ((checkX >= x && checkX <= x2) && (checkY >= y && checkY <= y2)) {
            return true;
        } else return false;
    }

    /**
     * gets the range of the chosen box.
     * @return a Rectangle object that will show the range of the box.
     */
    public Rectangle getRangeBox() {
        return rangeBox;
    }

    /**
     * sets the x position of the box to given value.
     * @param x the value the x position should be set to.
     */
    @Override
    public void setX(int x) {
        super.setX(x);
        this.rangeBox.setBounds(x - (WIDTH * (2 + 1)), (int) rangeBox.getY(),
                (int) rangeBox.getWidth(), (int) rangeBox.getHeight());
    }

    /**
     * sets the y position of the box to the given value.
     * @param y the value the y position should be set to.
     */
    @Override
    public void setY(int y) {
        super.setY(y);
        this.rangeBox.setBounds((int) rangeBox.getX(), y - (HEIGHT * (2 + 1)),
                (int) rangeBox.getWidth(), (int) rangeBox.getHeight());

    }
}
