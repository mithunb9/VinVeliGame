/**
 * The TechNode class stores all the tech nodes that users will purchase throughout the game,
 * including getter methods for certain attributes for the nodes, a toString method returning
 * all aspects of the node in one string, and methods to indicate whether a node has been
 * purchased.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */

package main.java.game.techtree;

import main.java.game.events.ActiveBehavior;
import main.java.game.events.Behavior;
import main.java.game.events.PassiveBehavior;

import java.util.Arrays;

public class TechNode {
    private int number;
    private String id;
    private String name;
    private int cost;
    private int[] adjacentNodes;
    private Behavior behavior;

    private static final String FLIGHT_ID = "fli";
    private static final String INTER_ID = "int";
    private static final String TRADE_ID = "tra";
    private static final String WEAPON_ID = "wea";
    private static final String PLANET_ID = "inf";
    private static final String STOCK_ID = "stk";
    private static final String AUTOTRADE_ID = "aut";
    private static final String DEF_ID = "def";
    private static final String ADVANCE_ID = "adv";
    private static final String OPT_ID = "opt";
    private boolean visited = false;

    /**
     * Constructor for the TechNode class
     * @param number index of the node.
     * @param id the id of the node
     * @param name the name of the node
     * @param cost the cost of the node
     * @param adjacentNodes the nodes adjacent to the current node.
     */
    public TechNode(int number, String id, String name, int cost, int[] adjacentNodes) {
        this.number = number;
        this.id = id;
        this.name = name;
        this.cost = cost;

        if (adjacentNodes != null) {
            this.adjacentNodes = adjacentNodes;
        }

        this.behavior = createBehavior(id);
    }

    /**
     * Gets the number of the node.
     * @return the value number of the node.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets the id of the node.
     * @return a string of the ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the node.
     * @return a string with the name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost of the node.
     * @return the amount the node costs.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Gets the adjacent nodes.
     * @return Array of the adjacent nodes.
     */
    public int[] getNextNodes() {
        return adjacentNodes;
    }

    /**
     * Formats the information of the node into one string.
     * @return a string with the number, id, name, and cost of a node, as well as the
     * array of the adjacent nodes.
     */
    @Override
    public String toString() {
        String output = getNumber() + " " + getId() + " " + getName() + " " + getCost() + " "
                + Arrays.toString(getNextNodes());

        return output;
    }

    /**
     * Creates the behaviors based on what ID the object has.
     * @param id is the id being checked.
     * @return the behavior for that id.
     */
    public Behavior createBehavior(String id) {
        switch (id) {
            case FLIGHT_ID:
                return new PassiveBehavior("ship_0");
            case INTER_ID:
                return new PassiveBehavior("plus_5");
            case TRADE_ID:
                return new PassiveBehavior("plus_10");
            case WEAPON_ID:
                return new ActiveBehavior("dam_10");
            case PLANET_ID:
                return new PassiveBehavior("plus_20");
            case STOCK_ID:
                return new PassiveBehavior("plus_25");
            case AUTOTRADE_ID:
                return new PassiveBehavior("plus_30");
            case DEF_ID:
                return new PassiveBehavior("plus_35");
            case ADVANCE_ID:
                return new ActiveBehavior("dam_20");
            case OPT_ID:
                return new PassiveBehavior("plus_40");
        }

        return null;
    }

    /**
     * Returns whether the tech node has been visited
     * @return true if visited, false if not
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Sets a tech node as visited or not based on input
     * @param visited inputs whether the tech node is visited or not
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
