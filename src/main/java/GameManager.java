/*
 * The GameManager class manages the game with methods to add, remove, and set various objects
 * such as: the game state, the screen state, the key state, the buttons, text on the screen,
 * and player information. Also includes instantiation of the game, getting the techtree,
 * accessing the other states of the game, getting the location of the mouse, what planet
 * and ship the mouse has clicked, and where the mouse has clicked in general.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java;

import main.java.game.GameState;
import main.java.game.ScreenState;
import main.java.game.entities.Entity;
import main.java.game.entities.WarshipEntity;
import main.java.game.techtree.TechNode;
import main.java.game.ui.GameButton;
import main.java.game.util.TileMapLoader;
import main.java.game.world.AstralBody;
import main.java.game.world.Capital;
import main.java.game.util.DataLoader;
import main.java.game.Player;
import main.java.game.techtree.TechTree;
import main.java.game.world.Planet;
import main.java.game.world.Production;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    private static int WINDOW_HEIGHT;
    private static int WINDOW_WIDTH;
    private static final int NUM_PLANETS = 3;
    private static final int SUN_INDEX = 9;

    private static final int FIFTY = 50;
    private static final int startBXPos = 80;
    private static final int nextBXPos = 80;
    private static final int startBYPos = 300;
    private static final int nextBYPos = 590;
    private static final int startBW = 157;
    private static final int nextBW = 257;
    private static final int BUTTONH = 50;

    private static final int ONER = 41;
    private static final int ONEG = 128;
    private static final int ONEB = 185;

    private static final int TWOR = 231;
    private static final int TWOG = 76;
    private static final int TWOB = 60;

    private static final int BUTTON_OFFSET = 85;
    private static final int BUTTON_ONE = 150;
    private static final int BUTTON_TWO = BUTTON_ONE + 55;

    //TECH NODE HANDLING
    private static final int NODE_WIDTH = 110;
    private static final int NODE_HEIGHT = 70;

    // BUY WINDOW
    private static final int SCREEN_HEIGHT = 720;
    private static final int SCREEN_WIDTH = 1280;
    private static final int WINDOW_OFFSET = 80;

    private static int turnCount = 1;

    private static TechTree techTree;
    private static ArrayList<BufferedImage> planetTileMap;
    private static ArrayList<GameButton> buyMenuButtonArray = new ArrayList<>();

    private static ScreenState screenState;
    private static ArrayList<GameState> gameState;

    private static boolean music = false;

    private static Player playerOne;
    private static Player playerTwo;
    private static Player activePlayer;
    private static Player otherPlayer;

    private static ArrayList<AstralBody> world;

    private static GameButton startGameButton;
    private static GameButton nextTurnButton;

    private static ArrayList<GameButton> gameButtons;
    private static ArrayList<String> planetNames;

    private static int planetMouseLocation = -1;
    private static int buttonMouseLocation = -1;
    private static int shipMouseLocation = -1;
    private static int selectionMouseLocationRow = -1;
    private static int selectionMouseLocationCol = -1;

    private static Rectangle2D[][] selectionMatrix;

    private static GameState keyState;

    private static BufferedImage buyWarshipButton;
    private static BufferedImage buyTransportShipButton;

    private static int globalMouseX;
    private static int globalMouseY;

    private static ArrayList<Rectangle> techTreeRectangles = new ArrayList<>();

    /**
     * This method generates the instantiation of our game, including the background screen of the
     * game, the planets, player information and capitals, planet names, and buttons used to
     * operate the turn system of the game.
     */
    public static void initStateManagement() {
        try {
            WINDOW_WIDTH = SCREEN_WIDTH - 160;
            WINDOW_HEIGHT = SCREEN_HEIGHT - 160;

            gameState = new ArrayList<>();
            screenState = ScreenState.START_SCREEN;

            DataLoader dataLoader = new DataLoader();
            techTree = dataLoader.loadTechTreeData();
            planetTileMap = TileMapLoader.loadPlanetTileMap();
            planetNames = dataLoader.loadPlanetNameData();
            BufferedImage sunIcon = planetTileMap.remove(SUN_INDEX);

            planetTileMap.remove(getRandIntInRange(0, planetTileMap.size()));
            System.out.println(planetTileMap.size());
            Collections.shuffle(planetTileMap);
            world = new ArrayList<>();

            int numNames = planetNames.size();
            String playerOneCapitalName = planetNames.remove(getRandIntInRange(0, numNames-1));
            String playerTwoCapitalName = planetNames.remove(getRandIntInRange(0, numNames-1));

            BufferedImage capitalOne = planetTileMap.get(0);
            BufferedImage capitalTwo = planetTileMap.get(1);

            Capital playerOneCapital = new Capital(capitalOne, playerOneCapitalName, world.size());
            Capital playerTwoCapital = new Capital(capitalTwo, playerTwoCapitalName, world.size());
            planetTileMap.remove(0);
            planetTileMap.remove(0);


            playerOne = new Player(playerOneCapital, "Player 1", new Color(ONER, ONEG, ONEB), 1);
            playerTwo = new Player(playerTwoCapital, "Player 2", new Color(TWOR, TWOG, TWOB), 2);

            playerOne.setTechTree(dataLoader.loadTechTreeData());
            playerTwo.setTechTree(dataLoader.loadTechTreeData());

            activePlayer = playerOne;
            otherPlayer = playerTwo;

            world.add(playerOneCapital);

            for (int i = 0; i < NUM_PLANETS; i++) {
                BufferedImage planetIcon = planetTileMap.get(i);
                planetTileMap.remove(i);

                Planet planet = new Planet(planetIcon, planetNames.remove(getRandIntInRange(0,
                        planetNames.size())), world.size());
                world.add(planet);
            }

            System.out.println(playerOne.getOwnedPlanets().size());

            world.add(new AstralBody(sunIcon));

            for (int i = 0; i < NUM_PLANETS; i++) {
                BufferedImage planetIcon = planetTileMap.get(i);
                planetTileMap.remove(i);

                Planet planet = new Planet(planetIcon, planetNames.remove(getRandIntInRange(0,
                        planetNames.size())), world.size());
                world.add(planet);
            }

            world.add(playerTwoCapital);
            gameButtons = new ArrayList<>();

            System.out.println(playerTwo.getOwnedPlanets().size());

            startGameButton = new GameButton(new Rectangle(startBXPos, startBYPos,
                    startBW, BUTTONH), "START",
                    GameState.START_GAME);
            addButtons(startGameButton);

            nextTurnButton = new GameButton(new Rectangle(nextBXPos, nextBYPos, nextBW, BUTTONH),
                    "NEXT TURN", GameState.NEXT_TURN);

            // Initialize Tech Tree Rectangles
            int globalYOffset = WINDOW_OFFSET + ((WINDOW_HEIGHT / 2) / 2);

            Rectangle flightBox = new Rectangle(WINDOW_OFFSET + 5, WINDOW_OFFSET +
                    (WINDOW_HEIGHT / 2) - 50, NODE_WIDTH, NODE_HEIGHT);
            techTreeRectangles.add(flightBox);

            Rectangle interEcon = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset - 50,
                    (NODE_WIDTH + 90), NODE_HEIGHT);
            techTreeRectangles.add(interEcon);

            Rectangle trade = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset + 50,
                    (NODE_WIDTH - 34), NODE_HEIGHT);
            techTreeRectangles.add(trade);

            Rectangle weapons = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset + 150,
                    (NODE_WIDTH - 14), NODE_HEIGHT);
            techTreeRectangles.add(weapons);

            Rectangle planetInfra = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset + 250,
                    (NODE_WIDTH + 80), NODE_HEIGHT);
            techTreeRectangles.add(planetInfra);

            Rectangle stock = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset - 50,
                    (NODE_WIDTH + 100), NODE_HEIGHT);
            techTreeRectangles.add(stock);

            Rectangle autoTrade = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 25,
                    (NODE_WIDTH + 40), NODE_HEIGHT);
            techTreeRectangles.add(autoTrade);

            Rectangle increasedDef = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 100,
                    (NODE_WIDTH + 40), NODE_HEIGHT);
            techTreeRectangles.add(increasedDef);

            Rectangle advWeap = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 175,
                    (NODE_WIDTH + 50), NODE_HEIGHT);
            techTreeRectangles.add(advWeap);

            Rectangle workOp = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 250,
                    (NODE_WIDTH + 50), NODE_HEIGHT);
            techTreeRectangles.add(workOp);

            GameManager.getPlayerOne().addClickableRectangles(flightBox);
            GameManager.getPlayerTwo().addClickableRectangles(flightBox);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the techtree.
     * @return the techtree object of upgrades.
     */
    public static TechTree getTechTree() {
        return techTree;
    }

    /**
     * Gets the current player whose turn it is.
     * @return the player who is currently playing (their turn).
     */
    public static Player getActivePlayer() {
        return activePlayer;
    }

    public static Player getOtherPlayer() {
        return otherPlayer;
    }

    public static void setOtherPlayer(Player otherPlayer) {
        GameManager.otherPlayer = otherPlayer;
    }

    public static void setActivePlayer(Player activePlayer) {
        GameManager.activePlayer = activePlayer;
    }

    /**
     * Gets player one information.
     * @return the playerOne object.
     */
    public static Player getPlayerOne() {
        return playerOne;
    }
    /**
     * Gets the player two information.
     * @return the playerTwo object.
     */
    public static Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     * This method is used to switch the active player based on their turn,
     * as well as their ships so that they can be used during their turn.
     */
    public static void switchPlayer() {
        activePlayer.addMoney(activePlayer.getIncome());

        for (Entity possibleWarship : activePlayer.getShips()) {
            if (possibleWarship instanceof  WarshipEntity) {
                ((WarshipEntity) possibleWarship).getEnemyWarshipInRange();
            }
        }

        if (activePlayer.equals(playerOne)) {
            playerOne.setShipSelected(false);
            activePlayer = playerTwo;
            otherPlayer = playerOne;
        }
        else {
            playerTwo.setShipSelected(false);
            activePlayer = playerOne;
            otherPlayer = playerTwo;

            turnCount++;
        }

        ArrayList<Entity> currentShips = GameManager.getActivePlayer().getShips();
        ArrayList<Entity> newShips = new ArrayList<>();

        for (Entity ship: currentShips) {
            ship.setMovementPoints(activePlayer.getDefaultMovementPoints());
            newShips.add(ship);
        }

        activePlayer.setShips(newShips);

        ArrayList<Planet> planets = activePlayer.getOwnedPlanets();

        for (Planet planet: planets) {
            Production production = planet.calculateProductionQueue();

            if (production == null) continue;

            if (production.isEntityType() && production.getEntityTarget() != null) {
                Entity entity = production.getEntityTarget();
                WarshipEntity clone = new WarshipEntity(entity.getIcon(), entity.getX(),
                        entity.getY(), entity.getHitPoints(), entity.getMovementPoints(),
                        ((WarshipEntity) entity).getWeaponDamage());

                activePlayer.addShip(clone);
            } else {
                production.getBehaviorTarget();
            }
        }
    }

    /**
     * returns the world.
     * @return the world.
     */
    public static ArrayList<AstralBody> getWorld() {
        return world;
    }

    /**
     * Gets the map of planets.
     * @return ArrayList of the planets.
     */
    public static ArrayList<BufferedImage> getPlanetTileMap() {
        return planetTileMap;
    }

    /**
     * gets the position of the mouse when it is clicked.
     * @param x the x position of the mouse.
     * @param y the y position of the mouse.
     * @return an integer value of the position of the mouse.
     */
    public static int getIndexAtMouseLocation(int x, int y) {
        planetMouseLocation = -1;
        buttonMouseLocation = -1;
        shipMouseLocation = -1;
//        selectionMouseLocationRow = -1;
//        selectionMouseLocationCol = -1;

        globalMouseX = x;
        globalMouseY = y;

        if (GameManager.getScreenState() == ScreenState.GAME_SCREEN) {
            for (AstralBody body : world) {
                int boundingX = body.getX();
                int boundingY = body.getY();
                BufferedImage bounding = body.getPlanetIcon();

                boolean checkXBounds = (x >= boundingX && x <= (boundingX + bounding.getWidth()));
                boolean checkYBounds = (y >= boundingY && y <= (boundingY + bounding.getHeight()));

                if (checkXBounds && checkYBounds) {
                    if (body instanceof Planet) {
                        System.out.println(((Planet) body).getName());

                        for (Entity ship: GameManager.getActivePlayer().getShips()) {
                            if (ship instanceof WarshipEntity) {
                                if (ship.getX() <= boundingX - FIFTY &&
                                        ship.getX() >= boundingX + FIFTY) {
                                    if (ship.getY() <= boundingY - FIFTY &&
                                            ship.getY() >= boundingY + FIFTY) {
                                        GameManager.getActivePlayer().addPlanet((Planet) body);
                                    }
                                }
                            }
                        }

                        planetMouseLocation = world.indexOf(body);

                        GameManager.getActivePlayer().setSelectedPlanet((Planet) body);
                        return planetMouseLocation;
                    } else {
                        continue;
                    }
                }
            }
        }

        for (GameButton gameButton : gameButtons) {
            boolean trial = gameButton.didMouseClick(x, y);
            if (trial) {
                buttonMouseLocation = gameButtons.indexOf(gameButton);
                return buttonMouseLocation;
            } else {
                continue;
            }
        }

        if (GameManager.getActivePlayer().getShips().size() != 0) {
            for (Entity ship: GameManager.getActivePlayer().getShips()) {
                int boundingX = ship.getX();
                int boundingY = ship.getY();

                if ((x >= boundingX && x <= (boundingX + ship.getIcon().getWidth()) &&
                        (y >= boundingY && y <= (boundingY + ship.getIcon().getHeight())))) {

                    shipMouseLocation = GameManager.getActivePlayer().getShips().indexOf(ship);
                    GameManager.getActivePlayer().setShipSelected(true);
                    GameManager.getActivePlayer().setSelectedShip(ship);
                } else {
                    continue;
                }
            }
        }

        return -1;
    }

    /**
     * gets the location of the planet that the mouse has clicked.
     * @return an int of the planet clicked by the mouse.
     */
    public static int getPlanetMouseLocation() {
        return planetMouseLocation;
    }

    /**
     * Gets the location of the button clicked by the mouse.
     * @return an int of where the button clicked by the mouse is.
     */
    public static int getButtonMouseLocation() {
        int output = buttonMouseLocation;
        buttonMouseLocation = -1;

        return output;
    }

    /**
     * Gets teh location of the ship that the mouse has clicked.
     * @return an int of the ship clicked by the mouse.
     */
    public static int getShipMouseLocation() {
        return shipMouseLocation;
    }

    /**
     * Adds buttons to the game.
     * @param gameButton the button to be added.
     */
    public static void addButtons(GameButton gameButton) {
        gameButtons.add(gameButton);
    }

    /**
     * resets the buttons of the start menu and adds only the start game button to the screen.
     * @return an arraylist of the buttons in the game.
     */
    public static ArrayList<GameButton> resetToStart() {
        gameButtons = new ArrayList<>();
        gameButtons.add(startGameButton);

        return gameButtons;
    }

    /**
     *  resets the buttons in the game and adds the next turn button only.
     * @return arraylist of the buttons in the game.
     */
    public static ArrayList<GameButton> resetToGame() {
        gameButtons = new ArrayList<>();
        gameButtons.add(nextTurnButton);

        return gameButtons;
    }

    /**
     * gets the buttons in the game.
     * @return an arralist of the buttons in the game.
     */
    public static ArrayList<GameButton> getButtons() {
        return gameButtons;
    }

    /**
     * sets the window to the width given by the parameter.
     * @param windowWidth the width to set the window to.
     */
    public static void setWindowWidth(int windowWidth) {
        WINDOW_WIDTH = windowWidth;
    }

    /**
     * sets the window to the height given by the parameter.
     * @param windowHeight the height to set the window to.
     */
    public static void setWindowHeight(int windowHeight) {
        WINDOW_HEIGHT = windowHeight;
    }

    /**
     * gets the window height.
     * @return the height of the window.
     */
    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    /**
     * gets the width of the window.
     * @return the width of the window.
     */
    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    /**
     * sets the screen to the state given by the parameter (game, starting menu, etc)
     * @param screenState the state to set the screen to.
     */
    public static void setScreenState(ScreenState screenState) {
        GameManager.screenState = screenState;
    }

    /**
     * gets the screen state of the game.
     * @return the current screenstate of the game.
     */
    public static ScreenState getScreenState() {
        return screenState;
    }

    /**
     * gets the state of the game.
     * @return the current state of the game.
     */
    public static ArrayList<GameState> getGameState() {
        return gameState;
    }

    /**
     * adds a state to the game such as buy stages and more.
     * @param gameState the state the game should be set to.
     */
    public static void addGameState(GameState gameState) {
        GameManager.gameState.add(gameState);
    }

    /**
     * removes a game state and returns whether it has been removed or not.
     * @param gameState the game state to be removed.
     * @return whether or not the game state has been removed.
     */
    public static boolean removeGameState(GameState gameState) {
        return GameManager.gameState.remove(gameState);
    }

    /**
     * gets the number of turns completed.
     * @return an int of the number of turns completed.
     */
    public static int getTurnCount() {
        return turnCount;
    }

    /**
     * gets the selection matrix for choices.
     * @return a 2D array of the selection matrix.
     */
    public static Rectangle2D[][] getSelectionMatrix() {
        return selectionMatrix;
    }

    /**
     * sets the selection matrix to the parameter given.
     * @param selectionMatrix the matrix the selection matrix should be set to.
     */
    public static void setSelectionMatrix(Rectangle2D[][] selectionMatrix) {
        GameManager.selectionMatrix = selectionMatrix;
    }

    /**
     * gets the column location of the mouse selection.
     * @return an int of the mouse's column location.
     */
    public static int getSelectionMouseLocationCol() {
        return selectionMouseLocationCol;
    }

    /**
     * gets the row lcoation of the mouse selection.
     * @return an int of the mouse's row location.
     */
    public static int getSelectionMouseLocationRow() {
        return selectionMouseLocationRow;
    }

    /**
     * sets the key state to the given state in the parameter.
     * @param state the state the key state should be set to.
     */
    public static void setKeyState(GameState state) { keyState = state; }

    /**
     * gets the key state of the game output.
     * @return the current game state.
     */
    public static GameState retrieveKeyState() {
        GameState output = keyState;
        keyState = null;

        return output;
    }

    /**
     * Gets a random number between a given maximum and minimum number.
     * @param min the minimum number for the range.
     * @param max the maximum number for the range.
     * @return one integer that was randomly chosen between the given range.
     */
    public static int getRandIntInRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * sets the buyWarshipButton to the given image.
     * @param buyWarshipButton the buffered image to set as the buy button.
     */
    public static void setBuyWarshipButton(BufferedImage buyWarshipButton) {
        GameManager.buyWarshipButton = buyWarshipButton;
    }

    /**
     * sets the buyTransportShipButton to the given image.
     * @param buyTransportShipButton the buffered image to set as the buy button.
     */
    public static void setBuyTransportShipButton(BufferedImage buyTransportShipButton) {
        GameManager.buyTransportShipButton = buyTransportShipButton;
    }

    /**
     * gets the actual in game button for buying transport ships.
     * @return a GameButton for the purchase of trasnport ships.
     */
    public static GameButton getBuyTransportShipButton() {
        return new GameButton(buyTransportShipButton, BUTTON_OFFSET, BUTTON_ONE,
                GameState.BUY_TRANSPORTSHIP);
    }

    /**
     * gets the in game button for buying warships.
     * @return a GameButton for the purchase of warships.
     */
    public static GameButton getBuyWarshipButton() {
        return new GameButton(buyWarshipButton, BUTTON_OFFSET, BUTTON_TWO, GameState.BUY_WARSHIP);
    }

    /**
     * gets an ArrayList of game buttons for the buy menu.
     * @return the arraylist of GameButtons.
     */
    public static ArrayList<GameButton> getBuyMenuButtonArray() {
        return buyMenuButtonArray;
    }

    /**
     * removes the given button from the array of buttons.
     * @param button the button to be removed from the arraylist.
     */
    public static void removeBuyMenuButton(GameButton button) {
        buyMenuButtonArray.remove(button);
    }

    /**
     * adds the given button to the array of buttons.
     * @param button the button to be added to the array of buttons.
     */
    public static void addBuyMenuButton(GameButton button) {
        buyMenuButtonArray.add(button);
    }

    /**
     * gets the global x position of the mouse.
     * @return an integer value indicating the x position of the mouse.
     */
    public static int getGlobalMouseX() {
        return globalMouseX;
    }

    /**
     * gets the global y position of the mouse.
     * @return an integer value indicating the y position of the mouse.
     */
    public static int getGlobalMouseY() {
        return globalMouseY;
    }

    /**
     * sets the global x position of the mouse to the given position.
     * @param globalMouseX the x position to set as the global mouse position.
     */
    public static void setGlobalMouseX(int globalMouseX) {
        GameManager.globalMouseX = globalMouseX;
    }

    /**
     * sets the global y position of the mouse to the given position.
     * @param globalMouseY
     */
    public static void setGlobalMouseY(int globalMouseY) {
        GameManager.globalMouseY = globalMouseY;
    }

    /**
     * gets the array of rectangles for the tech tree.
     * @return the ArrayList of techtree rectangles.
     */
    public static ArrayList<Rectangle> getTechTreeRectangles() {
        return techTreeRectangles;
    }

    /**
     * Makes all the rectangles in the techtree clickable so the user can actually select
     * upgrades. Adds all rectangles after the given one and puts it in an array to update.
     * @param unlockedNode the node to start adding from.
     */
    public static void techTreeClickBuilder(TechNode unlockedNode) {
        int[] nextNodes = unlockedNode.getNextNodes();

        if (nextNodes == null) return;

        for (int number = 0; number < nextNodes.length; number++) {
            activePlayer.addClickableRectangles(techTreeRectangles.get(nextNodes[number]));
        }
    }
}
