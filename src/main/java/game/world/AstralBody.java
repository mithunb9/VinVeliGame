/**
 * The AstralBody class manages all astral bodies in the game, including planets.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */

package main.java.game.world;

import java.awt.image.BufferedImage;

public class AstralBody {
    private BufferedImage planetIcon;
    private int x;
    private int y;

    /**
     * Constructor for the class which instantiates the icon for a planet.
     * @param planetIcon
     */
    public AstralBody(BufferedImage planetIcon) {
        this.planetIcon = planetIcon;
    }

    /**
     * gets the icon for the planet.
     * @return a buffered image of the planet icon.
     */
    public BufferedImage getPlanetIcon() {
        return planetIcon;
    }

    /**
     * gets the x position of the object chosen.
     * @return integer value of the x position of the object.
     */
    public int getX() {
        return x;
    }

    /**
     * gets the y position of the y position of the object.
     * @return integer value of the y position of the object.
     */
    public int getY() {
        return y;
    }

    /**
     * sets the coordinates of the object to the given and x and y positions.
     * @param x the x position the object should be set to.
     * @param y the y position the object should be set to.
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
