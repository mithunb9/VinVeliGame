/**
 * The Planet class manages the planets in the game, utilizing many getter and setter methods for
 * different aspects of the planets, as well as other methods to manage the planets more
 * efficiently.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.world;

import main.java.game.util.DataLoader;
import main.java.game.util.TileMapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Planet extends AstralBody implements Comparable {
    private String name;
    private Deque<Production> productionQueue;
    private int worldPosition;
    private int radiusX;
    private int radiusY;
    private int radiusWidth;
    private int radiusHeight;
    private Rectangle radiusRect;

    /**
     * Constructor for the class. Instantiates the planet position and name.
     * @param planetIcon the Icon of the planet (image)
     * @param name name of the planet.
     * @param worldPosition position of the planet.
     */
    public Planet(BufferedImage planetIcon, String name, int worldPosition) {
        super(planetIcon);
        this.name = name;
        this.worldPosition = worldPosition;

        radiusX = getX() - (getPlanetIcon().getWidth() / 4);
        radiusY = getY() - (getPlanetIcon().getHeight() / 4);
        radiusWidth = getPlanetIcon().getWidth() + 30;
        radiusHeight = getPlanetIcon().getHeight() + 31;

        radiusRect = new Rectangle(radiusX, radiusY, radiusWidth, radiusHeight);

        productionQueue = new LinkedList<>();
    }

    /**
     * gets the name of the planet.
     * @return the name of the planet.
     */
    public String getName() {
        return name;
    }

    /**
     * gets the x position.
     * @return integer of the x position.
     */
    @Override
    public int getX() {
        return super.getX();
    }

    /**
     * gets teh y position.
     * @return integer of the y position.
     */
    @Override
    public int getY() {
        return super.getY();
    }

    /**
     * gets the planet icon.
     * @return a buffered image of the planet icon.
     */
    @Override
    public BufferedImage getPlanetIcon() {
        return super.getPlanetIcon();
    }

    /**
     * sets the coordinates to the given x and y position.
     * @param x the x position to be set to.
     * @param y the y position to be set to.
     */
    @Override
    public void setCoordinates(int x, int y) {
        super.setCoordinates(x, y);

        radiusX = getX() - (getPlanetIcon().getWidth() / 4);
        radiusY = getY() - (getPlanetIcon().getHeight() / 4);
        radiusWidth = getPlanetIcon().getWidth() + 30;
        radiusHeight = getPlanetIcon().getHeight() + 31;
    }

    /**
     * gets the world position of the chosen object.
     * @return an integer value of the position of the object.
     */
    public int getWorldPosition() {
        return worldPosition;
    }

    /**
     * compares the chosen object to the given planet in the parameter.
     * @param planet the planet to be compared to.
     * @return an integer indicating the difference between the 2 objects.
     */
    @Override
    public int compareTo(Object planet) {
        int position = this.getWorldPosition();
        int comparePosition = ((Planet) planet).getWorldPosition();

        if (position <= comparePosition) return position;
        else return comparePosition;
    }

    /**
     * Adds to the production queue
     * @param prod the item to add to the queue
     */
    public void addProduction(Production prod) {
        productionQueue.add(prod);
    }

    /**
     * Returns the production queue
     * @return the production queue
     */
    public Queue<Production> getProductionQueue() {
        return productionQueue;
    }

    /**
     * Returns the production queue poll
     * @return production queue poll
     */
    public Production calculateProductionQueue() {
        return productionQueue.poll();
    }

    /**
     * Returns the radius height
     * @return the radius height
     */
    public int getRadiusHeight() {
        return radiusHeight;
    }

    /**
     * Returns the radius width
     * @return the radius width
     */
    public int getRadiusWidth() {
        return radiusWidth;
    }

    /**
     * Returns the x-coordinate of the radius
     * @return the x-coordinate
     */
    public int getRadiusX() {
        return radiusX;
    }

    /**
     * Returns the y-coordinate of the radius
     * @return the y-coordinate
     */
    public int getRadiusY() {
        return radiusY;
    }

    /**
     * Returns the production queue as a string
     * @return the production queue as a string
     */
    public String productionQueueToString() {
        String output = "(";

        for (Production p: productionQueue) {
            output += p.toString();
            output += ", ";
        }

        output += ")";
        output = output.replace(", )", ")");
        return output;
    }

    /**
     * Returns the radius of the planet as a rectangle
     * @return the radius of the planet as a rectangle
     */
    public Rectangle getRadiusRect() {
        return radiusRect;
    }

    /**
     * Sets the radius of the planet as a rectangle
     * @param radiusRect the radius of the planet as a rectangle
     */
    public void setRadiusRect(Rectangle radiusRect) {
        this.radiusRect = radiusRect;
    }
}
