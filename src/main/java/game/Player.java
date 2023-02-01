/**
 * This class includes the various information regarding players and what they can do/own.
 * This includes methods to find what ships they have, their planets and capitals, their
 * income, their existing money, their ships, and their techtree upgrades. There are also methods
 * to edit the above items.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game;

import main.java.GameManager;
import main.java.game.entities.Entity;
import main.java.game.entities.WarshipEntity;
import main.java.game.techtree.TechNode;
import main.java.game.techtree.TechTree;
import main.java.game.util.TileMapLoader;
import main.java.game.world.Capital;
import main.java.game.world.Planet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class Player {
    private Capital capital;
    private ArrayList<Planet> ownedPlanets;
    private String name;
    private Color color;
    private static BufferedImage[] shipIcons;
    private BufferedImage transportIcon;
    private BufferedImage warshipIcon;
    private int money;
    private ArrayList<Entity> ships;
    private Entity warshipEntity;
    private int defaultMovementPoints;
    private int income;
    private Planet selectedPlanet = null;
    private Entity selectedShip = null;
    private WarshipEntity targetShip = null;
    private boolean shipSelected = false;
    private TechTree techTree;
    private HashSet<TechNode> techTreeList;
    private ArrayList<Rectangle> clickableRectangles;

    /**
     * Constructor for the class. Initializes a player object with their capital, their name,
     * color, and icon Index for ships.
     * @param capital their capital.
     * @param name their name.
     * @param color their color.
     * @param shipIconIndex the index for their ship icon.
     */
    public Player(Capital capital, String name, Color color, int shipIconIndex) {
        this.capital = capital;
        this.name = name;
        this.color = color;
        this.money = 100;
        this.income = 20;

        this.ownedPlanets = new ArrayList<>();
        ownedPlanets.add(capital);
        this.defaultMovementPoints = 2;

        try {
            shipIcons = TileMapLoader.getShipIcons();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ships = new ArrayList<>();
        techTree = null;
        techTreeList = new HashSet<>();
        clickableRectangles = new ArrayList<>();

        warshipEntity = new WarshipEntity(warshipIcon, 100, 100, 50, 3, 5);
    }

    /**
     * gets the capital of the player.
     * @return the capital of the player.
     */
    public Capital getCapital() {
        return capital;
    }

    /**
     * sets the capital of the chosen player to the given capital.
     * @param capital the capital to set the player capital to.
     */
    public void setCapital(Capital capital) {
        this.capital = capital;
    }

    /**
     * gets the name of the player.
     * @return string of the players name.
     */
    public String getName() {
        return name;
    }

    /**
     * gets the color of the player.
     * @return Color of the player.
     */
    public Color getColor() {
        return color;
    }

    /**
     * sets the color of the player to the given color in teh parameter.
     * @param color the color to set for the player.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * gets the icon for the transport ship of the player.
     * @return buffered image of player's transport ship.
     */
    public BufferedImage getTransportIcon() {
        return transportIcon;
    }

    /**
     * ges the icon for the warships of a player
     * @return buffered image of player's warships.
     */
    public BufferedImage getWarshipIcon() {
        return warshipIcon;
    }

    /**
     * returns whether or not the planet is owned by the player chosen.
     * @param planet the planet to be checked for ownership.
     * @return true if the player owns the planet, false if they don't.
     */
    public boolean owns(Planet planet) {
        return ownedPlanets.contains(planet);
    }

    /**
     * gets an Arraylist of all the planets owned by a player.
     * @return ArrayList of all the planets owned.
     */
    public ArrayList<Planet> getOwnedPlanets() {
        return ownedPlanets;
    }

    /**
     * adds a planet to the list of owned planets.
     * @param planet the planet to add to the list.
     */
    public void addPlanet(Planet planet) {
        this.ownedPlanets.add(planet);
    }

    /**
     * sets the planet given as the selected planet.
     * @param selectedPlanet the planet to set as the selected planet.
     */
    public void setSelectedPlanet(Planet selectedPlanet) {
        this.selectedPlanet = selectedPlanet;
    }

    /**
     * gets the selected planet.
     * @return Planet that is selected.
     */
    public Planet getSelectedPlanet() {
        return selectedPlanet;
    }

    /**
     * adds a ship to the ships owned by the player.
     * @param ship the ship to add to the player.
     */
    public void addShip(Entity ship) {
        this.ships.add(ship);
    }

    /**
     * gets an ArrayList of the ships owned by the player.
     * @return the ArrayList of the ships the player owns.
     */
    public ArrayList<Entity> getShips() {
        return ships;
    }

    /**
     * sets the given ships to the ships of the player.
     * @param ships the ships to set.
     */
    public void setShips(ArrayList<Entity> ships) {
        this.ships = ships;
    }

    /**
     * returns whether or not the ship is selected.
     * @return true if the ship is selected, false if not.
     */
    public boolean isShipSelected() {
        return shipSelected;
    }

    /**
     * sets the chosen ship as selected.
     * @param shipSelected set if true.
     */
    public void setShipSelected(boolean shipSelected) {
        this.shipSelected = shipSelected;
    }

    /**
     * gets the ship that was chosen to be selected.
     * @return the Entity ship that was set as selected.
     */
    public Entity getSelectedShip() {
        return selectedShip;
    }

    /**
     * sets the ship given in parameter as the selected ship.
     * @param selectedShip the ship to set selected.
     */
    public void setSelectedShip(Entity selectedShip) {
        this.selectedShip = selectedShip;
    }

    /**
     * gets the default movement points of a ship.
     * @return number of movement points a ship would have.
     */
    public int getDefaultMovementPoints() {
        return defaultMovementPoints;
    }

    /**
     * sets the default movement points a ship can have.
     * @param defaultMovementPoints the number of points to set for the ship.
     */
    public void setDefaultMovementPoints(int defaultMovementPoints) {
        this.defaultMovementPoints = defaultMovementPoints;
    }

    /**
     * gets the tech tree.
     * @return a tech tree object.
     */
    public TechTree getTechTree() {
        return techTree;
    }

    /**
     * sets the techtree to what is given in the parameter.
     * @param techTree the techtree to set the tech tree to.
     */
    public void setTechTree(TechTree techTree) {
        this.techTree = techTree;
    }

    /**
     * gets the money a player has.
     * @return integer amount of money a player has.
     */
    public int getMoney() {
        return money;
    }

    /**
     * sets the amount of money a player has to the given amount in the parameter.
     * @param money the amount to set for the player.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * adds the given amount of money to the player.
     * @param money the amount to add to the player.
     */
    public void addMoney(int money) {
        this.money = this.money + money;
    }

    /**
     * removes the given money from the player.
     * @param money the amount to remove from the player.
     */
    public void removeMoney(int money) {
        setMoney(getMoney() - money);
    }

    /**
     * sets the warshipEntity to the given entity.
     * @param warshipEntity the entity to set as the warshipEntity.
     */
    public void setWarshipEntity(Entity warshipEntity) {
        this.warshipEntity = warshipEntity;
    }

    /**
     * gets the warship entity of the player.
     * @return the Entity of the player that is the warship entity.
     */
    public Entity getWarshipEntity() {
        return warshipEntity;
    }

    /**
     * gets the income of the player
     * @return integer value the player has as income.
     */
    public int getIncome() {
        return income;
    }

    /**
     * sets the income of the player to the given amount.
     * @param income the amount to set as income for teh player.
     */
    public void setIncome(int income) {
        this.income = income;
    }

    /**
     * gets the tech tree list as a hashest.
     * @return the techtree list as a hashset.
     */
    public HashSet<TechNode> getTechTreeList() {
        return techTreeList;
    }

    /**
     * adds the given node to the tech tree list.
     * @param node the node to be added to the tech tree.
     */
    public void addTechTreeList(TechNode node) {
        techTreeList.add(node);
    }

    public ArrayList<Rectangle> getClickableRectangles() {
        return clickableRectangles;
    }

    public void addClickableRectangles(Rectangle rect) {
        clickableRectangles.add(rect);
    }
}
