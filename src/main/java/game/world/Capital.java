/**
 * This class includes all the main information on the capitals in the game.
 * It includes the constructor to instantiate, and various methods to get the
 * icon of the planet, the x and y coordinates, as well as set the x and y
 * coordinates of the capital, and also a method to get the world position of the capital.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.world;

import java.awt.image.BufferedImage;


public class Capital extends Planet {
    /**
     * Constructor for the class that instantiates the planet image, name and position.
     * @param planetIcon the icon for the planet.
     * @param name the name of the planet.
     * @param worldPosition the world position of the planet.
     */
    public Capital(BufferedImage planetIcon, String name, int worldPosition) {
        super(planetIcon, name, worldPosition);
    }

    /**
     * gets the icon of the planet.
     * @return a buffered image of the planet icon.
     */
    @Override
    public BufferedImage getPlanetIcon() {
        return super.getPlanetIcon();
    }

    /**
     * gets the x position of the object.
     * @return integer value of the x position.
     */
    @Override
    public int getX() {
        return super.getX();
    }

    /**
     * gets the y position of the object.
     * @return integer value of the y position.
     */
    @Override
    public int getY() {
        return super.getY();
    }

    /**
     * sets the coordinates of the chosen object to the given x and y coordinates.
     * @param x the x position to be set to.
     * @param y the y position to be set to.
     */
    @Override
    public void setCoordinates(int x, int y) {
        super.setCoordinates(x, y);
    }

    /**
     * gets the world position of the chosen object.
     * @return integer value of position of the object in the world.
     */
    @Override
    public int getWorldPosition() {
        return super.getWorldPosition();
    }
}
