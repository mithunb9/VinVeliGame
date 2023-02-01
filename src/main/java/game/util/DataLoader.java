/**
 * This class is used to obtain data from text files. In the parse behavior and
 * integer methods it goes through a string and creates usable arrays. It is
 * also responsible for loading in the tech
 * tree from the given data in the text file. Finally, it will also load the list of
 * planet names from another test file.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.util;

import main.java.game.events.Behavior;
import main.java.game.events.PassiveBehavior;
import main.java.game.techtree.TechNode;
import main.java.game.techtree.TechTree;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.Scanner;

public class DataLoader {
    private static ArrayList<TechNode> techTypeList;

    private final int NUMBER_INDEX = 0;
    private final int ID_INDEX = 1;
    private final int NAME_INDEX = 2;
    private final int COST_INDEX = 3;
    private final int ADJACENCY_INDEX = 4;

    /**
     * loads Data
     * @return updated techtree
     * @throws FileNotFoundException throws a file
     * @throws URISyntaxException
     */
    public TechTree loadTechTreeData() throws FileNotFoundException, URISyntaxException {
        techTypeList = new ArrayList<TechNode>();
        InputStream in = getClass().getResourceAsStream("/main/resources/data/techtree_data.txt");
        Scanner scanner = new Scanner(in);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] lineArray = line.split("/");

            int number = Integer.parseInt(lineArray[NUMBER_INDEX]);
            String id = lineArray[ID_INDEX];
            String name = lineArray[NAME_INDEX];
            int cost = Integer.parseInt(lineArray[COST_INDEX]);
            int[] adjacentNodes = parseIntegerArray(lineArray[ADJACENCY_INDEX]);


            TechNode node = new TechNode(number, id, name, cost, adjacentNodes);
            techTypeList.add(node);

            System.out.println(node);
        }

        TechTree tree = new TechTree(techTypeList);
        System.out.println(tree);
        return tree;
    }

    /**
     * Loads the name of the planets from the text file.
     * @return an Arraylist of the planet names.
     */
    public ArrayList<String> loadPlanetNameData() {
        ArrayList<String> planetNameData = new ArrayList<>();
        InputStream in = getClass().getResourceAsStream("/data/planet_names.txt");
        Scanner scanner = new Scanner(in);

        while (scanner.hasNext()) {
            planetNameData.add(scanner.nextLine());
        }

        return planetNameData;
    }

    /**
     * Goes through a string to find all the behaviors in them.
     * @param arrayString the string being searched/going through.
     * @return an array of Behavior objects for all the behaviors in the string.
     */
    private static Behavior[] parseBehaviorArray(String arrayString) {
        if (arrayString.length() <= 2) return null;

        arrayString = arrayString.substring(1);
        arrayString = arrayString.substring(0, arrayString.length()-1);

        String[] stringArray = arrayString.split(",");
        Behavior[] output = new PassiveBehavior[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            output[i] = new PassiveBehavior(stringArray[i]);
        }

        return output;
    }

    /**
     * Goes through a given string to find integers and create an array of integers.
     * @param arrayString the string that we will go through to find strings.
     * @return an array of integer objects derived from the strings.
     */
    private static int[] parseIntegerArray(String arrayString) {
        if (arrayString.length() <= 2) return null;

        arrayString = arrayString.substring(1);
        arrayString = arrayString.substring(0, arrayString.length()-1);

        String[] stringArray = arrayString.split(",");
        int[] output = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            output[i] = Integer.parseInt(stringArray[i]);
        }

        return output;
    }

    /**
     * gets a list of tech types and returns them in a string array.
     * @return A string array of the tech types.
     */
    public static String[] getTechTypeList() {
       String[] output = new String[techTypeList.size()];

       for (int i = 0; i < techTypeList.size(); i++) {
           output[i] = techTypeList.get(i).toString();
       }

       return output;
    }
}
