/**
 * This class instantiates the techtree itself and includes methods to edit it as well.
 * Methods such as add or set can make changes to the techtree, while the get method just
 * retrieves information, and finally the toString method formats the techtree into a
 * legible string format.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.techtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TechTree {
    private boolean[][] techTreeGraph;
    private ArrayList<TechNode> nodeList;

    /**
     * Constructor for the techtree.
     * @param treeList
     */
    public TechTree(List<TechNode> treeList) {
        nodeList = (ArrayList<TechNode>) treeList;
        int numNodes = treeList.size();

        techTreeGraph = new boolean[numNodes][numNodes];

        for (TechNode node : treeList) {
            this.add(node);
        }
    }

    /**
     * Adds the node in the parameter to the techtree
     * @param node the node to be added to the techtree.
     */
    public void add(TechNode node) {
        int[] adjacentNodes = node.getNextNodes();

        if (adjacentNodes == null) return;

        for (int i = 0; i < adjacentNodes.length; i++) {
            techTreeGraph[node.getNumber()][adjacentNodes[i]] = true;
        }
    }

    /**
     * Gets the node at the given number.
     * @param number the number location being searched.
     * @return the tech node at the location.
     */
    public TechNode get(int number) {
        return nodeList.get(number);
    }

    /**
     * Sets a node at the given location to the given node.
     * @param number the number for the node to be changed.
     * @param node the node that it should be changed to.
     */
    public void set(int number, TechNode node) {
        nodeList.set(number, node);
    }

    /**
     * Return a formatted string of the nodes.
     * @return formatted string of the nodes.
     */
    @Override
    public String toString() {
        String output = "[\n";

        for (int i = 0; i < techTreeGraph.length; i++) {
            output += Arrays.toString(techTreeGraph[i]) + "\n";
        }

        return output + "]";
    }
}
