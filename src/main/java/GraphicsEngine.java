/** This class is responsible for displaying our game. It includes methods to update the visuals
 * of the game, display the techtree, the planets, the ships, and helper methods for each. It
 * also includes engine constants for the frame of the game.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java;

import main.java.game.GameState;
import main.java.game.Player;
import main.java.game.ScreenState;
import main.java.game.entities.Entity;
import main.java.game.entities.WarshipEntity;
import main.java.game.techtree.TechNode;
import main.java.game.techtree.TechTree;
import main.java.game.ui.GameButton;
import main.java.game.ui.UIKeyListener;
import main.java.game.ui.UIMouseListener;
import main.java.game.world.AstralBody;
import main.java.game.world.Capital;
import main.java.game.world.Planet;
import main.java.game.world.Production;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class GraphicsEngine extends JPanel implements Runnable {

    /* Engine Constants */
    private static final int SCREEN_HEIGHT = 720;
    private static final int SCREEN_WIDTH = 1280;
    private static final int FPS = 60;
    private static final int WARSHIP_COST = 100;

    /* Warship Constants */
    private static final int XY = 100;
    private static final int HEALTH = 50;
    private static final int MV = 3;
    private static final int DAMAGE = 5;
    private static final int SHIP_OFFSET = 5;

    /* Drawing Constants */
    private static final int DRAW_X = 80;
    private static final int DRAW_X_OFFSET20 = 100;
    private static final int DRAW_X_OFFSET40 = 120;
    private static final int DRAW_X_OFFSET60 = 140;
    private static final int DRAW_X_OFFSETX3 = 240;

    private static final int DRAW_HELP = 5;
    private static final int DRAW_HELP_OFFSETX3 = 15;
    private static final int DRAW_HELP_OFFSETX10 = 50;
    private static final int DRAW_HELP_OFFSETX13 = 65;

    /* Loop Variables */
    private static Thread thread;
    private static boolean isStart = false;

    /* Graphics Variables */
    private BufferedImage backgroundImage;
    private BufferedImage startScreenBackground;
    private BufferedImage muteIcon;
    private BufferedImage warshipOne;
    private BufferedImage warshipTwo;
    private BufferedImage transportOne;
    private BufferedImage transportTwo;

    private BufferedImage buyButtonIcon;
    private GameButton buyButton;

    private final String BACKGROUND_PATH = "/assets/background.png";
    private final String START_BACKGROUND_PATH = "/assets/startScreenBackground.png";

    /* TechTree Constants */
    

    /* Size Variables */

    //FONT SIZE
    private final int FONT_OFFSET = 15;
    private final int BUY_FONT = FONT_OFFSET + 5;
    private final int PLANET_FONT = FONT_OFFSET + 8;
    private final int HUD_CONTEXT_FONT = FONT_OFFSET + 10;
    private final int HIGH_SCORE_FONT = FONT_OFFSET + 45;
    private final int HIGH_SCORE_FONT2 = FONT_OFFSET + 35;

    // BUY WINDOW
    private final int WINDOW_OFFSET = 80;
    private final int WINDOW_WIDTH = SCREEN_WIDTH - 160;
    private final int WINDOW_HEIGHT = SCREEN_HEIGHT - 160;

    private final int NAME_X = WINDOW_OFFSET + 5;
    private final int NAME_Y = WINDOW_OFFSET + 25;

    private final int PROD_X = WINDOW_OFFSET + 5;
    private final int PROD_Y = WINDOW_OFFSET + 50;

    //TECH NODE HANDLING
    private final int NODE_WIDTH = 110;
    private final int NODE_HEIGHT = 70;

    private Clip clip;

    private Font buyFont = new Font("Tahoma", Font.TRUETYPE_FONT, BUY_FONT);
    private Font techFont = new Font("Tahoma", Font.TRUETYPE_FONT, FONT_OFFSET);
    private Font planetNameFont = new Font("NewHiScore", Font.TRUETYPE_FONT, PLANET_FONT);
    private Font HUDContextFont = new Font("NewHiScore", Font.BOLD, HUD_CONTEXT_FONT);

    /* Misc. Constants */
    private static final int INTERVAL = 1000000000;
    private static final int CAPITAL_OFFSET = 50;

    private static final int BUILD_WORLD_X = 30;
    private static final int BUILD_WORLD_Y = 40;
    private static final int WORLD_STRING_X = 20;
    private static final int WORLD_STRING_Y = 90;

    private static final int HUD_X = 1000;
    private static final int HUD_Y = 80;
    private static final int HUD_Y_OFFSET = 100;

    /**
     * Instantiates the graphics of the game, which includes the background,
     * the buttons for buying ships. Also includes other useful utilities
     * such as the mouse and key listeners to know when buttons are pressed.
     * This method also creates the window for the game, the audio clips
     * for the music, and the font for the text used throughout the game.
     * The method is also responsible for instantiating warships with set stats
     * for both players when needed.
     *
     */
    public GraphicsEngine() {
        // Creates window
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        // Adds mouse listener
        MouseListener UIMouseListener = new UIMouseListener();
        this.addMouseListener(UIMouseListener);

        KeyListener UIKeyListener = new UIKeyListener();
        this.addKeyListener(UIKeyListener);

        try {
            // Starts state management
            GameManager.initStateManagement();

            // Loads background image
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(BACKGROUND_PATH));

            InputStream startScreenStream = getClass().getResourceAsStream(START_BACKGROUND_PATH);
            startScreenBackground = ImageIO.read(startScreenStream);

            // Loads spaceship images
            warshipOne = ImageIO.read
                    (GameManager.class.getResourceAsStream("/assets/warship1.png"));
            warshipTwo = ImageIO.read
                    (GameManager.class.getResourceAsStream("/assets/warship2.png"));
            transportOne = ImageIO.read
                    (GameManager.class.getResourceAsStream("/assets/transport1.png"));
            transportTwo = ImageIO.read
                    (GameManager.class.getResourceAsStream("/assets/transport2.png"));


            muteIcon = ImageIO.read(getClass().getResourceAsStream("/assets/close.png"));

            BufferedImage buyWarshipButton = ImageIO.read(getClass().getResourceAsStream
                    ("/assets/warshipButton.png"));
            BufferedImage buyTransportShipButton = ImageIO.read(getClass().getResourceAsStream
                    ("/assets/transportButton.png"));

            GameManager.setBuyWarshipButton(buyWarshipButton);
            GameManager.setBuyTransportShipButton(buyTransportShipButton);

            GameManager.addBuyMenuButton(GameManager.getBuyTransportShipButton());
            GameManager.addBuyMenuButton(GameManager.getBuyWarshipButton());

            // Registers custom font
            InputStream in = getClass().getResourceAsStream
                    ("/main/resources/fonts/NewHiScore.ttf");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, in));

            URL audioURL = getClass().getResource("/main/resources/sfx/Level 1.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioURL);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            GameManager.addGameState(GameState.MUTE_SOUND);

            GameManager.setWindowHeight(HEIGHT);
            GameManager.setWindowWidth(WIDTH);

            WarshipEntity basicWarshipEntity =
                    new WarshipEntity(warshipOne, XY, XY, HEALTH, MV, DAMAGE);
            WarshipEntity basicWarshipEntityTwo = new WarshipEntity
                    (warshipTwo, XY, XY, HEALTH, MV, DAMAGE);
            GameManager.getPlayerOne().setWarshipEntity(basicWarshipEntity.clone());
            GameManager.getPlayerTwo().setWarshipEntity(basicWarshipEntityTwo.clone());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new thread.
     */
    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Runs the code and redraws everything 60 times a second for 60 FPS.
     */
    @Override
    public void run() {
        // Calculates the math to redraw every FPS times a second
        // Default is 60.
        double interval = INTERVAL / FPS;
        double deltaTime = 0;

        long startTime = System.nanoTime();
        long currentTime;

        // Game loop (repaints 60 times a second)
        while (thread != null) {
            currentTime = System.nanoTime();

            deltaTime += (currentTime - startTime) / interval;
            startTime = currentTime;

            if (deltaTime >= -1) {
                update();
                repaint();
                deltaTime--;
            }
        }
    }

    /**
     * The method used to paint everything.
     * @param g the graphics used.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        switch (GameManager.getScreenState()) {
            case START_SCREEN:
                g2.drawImage(startScreenBackground, 0, 0, this);
                drawButtons(g2);
                initializeGame(g2);

//                creditsGUI(g2);

                break;
            case GAME_SCREEN:
                gameScreen(g2);
//                helpGUI(g2);

                break;
            case BUY_SCREEN:
//                gameScreen(g2);
//                drawHUD(g2);
                buyGUI(g2);

                break;
            case TECH_SCREEN:
                techGUI(g2);

                break;
            case CREDITS_SCREEN:
                g2.drawImage(startScreenBackground, 0, 0, this);
                creditsGUI(g2);

                break;
        }

        g2.dispose();
    }

    /**
     * This method updates the game real time to keep it going from turn to turn.
     */
    public void update() {
        Player player = GameManager.getActivePlayer();

        for (int i = 0; i < GameManager.getGameState().size(); i++) {
            GameState state = GameManager.getGameState().get(i);

            if (state == GameState.MUTE_SOUND) {
                if (clip.isActive()) clip.stop();
            } else if (state == GameState.PLAY_MUSIC) {
                if (!clip.isActive()) clip.start();
            } else if (state == GameState.NEXT_TURN) {
                GameManager.switchPlayer();
                GameManager.removeGameState(GameState.NEXT_TURN);
            } else if (state == GameState.BUY_WARSHIP) {
                GameManager.removeGameState(GameState.BUY_WARSHIP);
                GameManager.setGlobalMouseX(0);
                GameManager.setGlobalMouseY(0);

                if (player.getMoney() - WARSHIP_COST >= 0) {
                    WarshipEntity newWarship = (WarshipEntity) player.getWarshipEntity();
                    newWarship.setX(player.getCapital().getX());
                    newWarship.setY(player.getCapital().getY() - CAPITAL_OFFSET);

                    player.getSelectedPlanet().addProduction
                            (new Production(player.getWarshipEntity()));
                    player.setMoney(player.getMoney() - WARSHIP_COST);
                }

            } else if (state == GameState.BUY_TRANSPORTSHIP) {
                GameManager.removeGameState(GameState.BUY_TRANSPORTSHIP);
            }
        }

        GameState keyState = GameManager.retrieveKeyState();

        Player activePlayer = GameManager.getActivePlayer();
        Entity ship = activePlayer.getSelectedShip();

        if (keyState == null || ship == null) return;
        if (GameManager.getActivePlayer().getSelectedShip().getMovementPoints() <= 0) return;

        if (keyState == GameState.KEY_UP) ship.setY(ship.getY() - ship.getIcon().getHeight());
        else if (keyState == GameState.KEY_DOWN)
            ship.setY(ship.getY() + ship.getIcon().getHeight());
        else if (keyState == GameState.KEY_LEFT) ship.setX
                (ship.getX() - ship.getIcon().getWidth());
        else if (keyState == GameState.KEY_RIGHT)
            ship.setX(ship.getX() + ship.getIcon().getWidth());

        GameManager.getActivePlayer().getSelectedShip().setMovementPoints
                (GameManager.getActivePlayer().getSelectedShip().getMovementPoints() - 1);
    }

    /**
     * The constructor that initializes the methods below.
     * @param g2 the graphics used.
     */
    private void gameScreen(Graphics2D g2) {
        g2.drawImage(backgroundImage, 0, 0, this);
        drawButtons(g2);
        buildWorld(g2);
        drawShips(g2);
        drawShipContext(g2);
        drawContext(g2);
        drawHUD(g2);
        processAnnex(g2);
    }

    /**
     * This method builds the world in the game and shows planets.
     * @param g2 the graphics.
     */
    private void buildWorld(Graphics2D g2) {
        ArrayList<AstralBody> world = GameManager.getWorld();
        int distance = SCREEN_WIDTH / world.size();

        g2.setColor(Color.white);
        g2.setFont(planetNameFont);

        for (int i = 0; i < world.size(); i++) {
            int x = BUILD_WORLD_X + (distance * i);
            int y = (this.getHeight() / 2) - BUILD_WORLD_Y;

            AstralBody body = world.get(i);
            body.setCoordinates(x, y);

            if (body instanceof Planet) {
                Planet planet = (Planet) body;

                if (GameManager.getPlayerOne().owns(planet)) {
                    g2.setColor(GameManager.getPlayerOne().getColor());
                } else if (GameManager.getPlayerTwo().owns(planet)) {
                    g2.setColor(GameManager.getPlayerTwo().getColor());
                } else {
                    g2.setColor(Color.white);
                }

                g2.drawString(planet.getName(), x - WORLD_STRING_X, y + WORLD_STRING_Y);
                Rectangle radiusBox = new Rectangle(planet.getRadiusX(), planet.getRadiusY(),
                        planet.getRadiusWidth(), planet.getRadiusHeight());

                planet.setRadiusRect(radiusBox);
                g2.draw(planet.getRadiusRect());
            }

            g2.drawImage(body.getPlanetIcon(), x, y, this);
        }
    }

    /**
     * This method displays the information during the game on whose turn it is.
     * @param g2 the graphics used.
     */
    public void drawContext(Graphics2D g2) {
        int possibleMouseIndex = GameManager.getPlanetMouseLocation();
        g2.setColor(GameManager.getActivePlayer().getColor());
        g2.setFont(HUDContextFont);

        if (possibleMouseIndex != -1) {
            Planet planet = (Planet) GameManager.getWorld().get(possibleMouseIndex);
            g2.drawString(planet.getName(), DRAW_X, DRAW_X);

            if (planet instanceof Capital) {
                if (planet.getName().equals(GameManager.getPlayerOne().getCapital().getName())) {
                    if (planet.equals(GameManager.getActivePlayer().getCapital())) {
                        g2.drawString("Your Capital", DRAW_X, DRAW_X_OFFSET20);
                    } else {
                        g2.drawString("Player 1's Capital", DRAW_X, DRAW_X_OFFSET20);
                    }
                } else {
                    if (planet.equals(GameManager.getActivePlayer().getCapital())) {
                        g2.drawString("Your Capital", DRAW_X, DRAW_X_OFFSET20);
                    } else {
                        g2.drawString("Player 2's Capital", DRAW_X, DRAW_X_OFFSET20);
                    }
                }

                g2.drawString(GameManager.getActivePlayer().getName() +
                        "'s Turn", DRAW_X, DRAW_X_OFFSET40);
                g2.drawString("Turn " + GameManager.getTurnCount(), DRAW_X, DRAW_X_OFFSET60);
            } else {
                g2.drawString(GameManager.getActivePlayer().getName()
                        + "'s Turn", DRAW_X, DRAW_X_OFFSET20);
                g2.drawString("Turn " + GameManager.getTurnCount(), DRAW_X, DRAW_X_OFFSET40);
            }
        } else {
            g2.drawString(GameManager.getActivePlayer().getName()
                    + "'s Turn", DRAW_X, DRAW_X_OFFSET20);

            if (GameManager.getScreenState() != ScreenState.BUY_SCREEN)
                g2.drawString("Empty Space", DRAW_X, DRAW_X);

            g2.drawString("Turn " + GameManager.getTurnCount(), DRAW_X, DRAW_X_OFFSET40);
        }
    }

    /**
     * This method initializes the game and shows the welcome screen.
     * @param g2 the graphics used.
     */
    private void initializeGame(Graphics2D g2) {
        g2.setColor(Color.white);
        Font font = new Font("NewHiScore", Font.TRUETYPE_FONT, HIGH_SCORE_FONT);
        g2.setFont(font);
        g2.drawString("Welcome to VinVeli", DRAW_X, DRAW_X_OFFSETX3);
    }

    /**
     * This method draws the buttons of the game.
     * @param g2 the graphics used.
     */
    public void drawButtons(Graphics2D g2) {
        g2.setColor(Color.white);

        ArrayList<GameButton> gameButtons = GameManager.getButtons();

        Font font = new Font("NewHiScore", Font.TRUETYPE_FONT, HIGH_SCORE_FONT2);
        g2.setFont(font);

        for (GameButton gameButton : gameButtons) {
            g2.draw(gameButton.getShape());

            g2.drawString(gameButton.getLabel(), gameButton.getTextX(), gameButton.getTextY());
        }

        for (GameButton gameButton : gameButtons) {
            if (GameManager.getButtonMouseLocation() == gameButtons.indexOf(gameButton)) {
                switch (gameButton.getButtonState()) {
                    case NEXT_TURN:
                        GameManager.switchPlayer();
                        GameManager.removeGameState(GameState.NEXT_TURN);
                        break;
                    case START_GAME:
                        GameManager.setScreenState(ScreenState.GAME_SCREEN);
                        GameManager.resetToGame();
                        break;
                }
            }
        }
    }

    /**
     * Displays the buy menu.
     * @param g2 the graphics used.
     */
    public void buyGUI(Graphics2D g2) {
        g2.setFont(buyFont);

        if (GameManager.getActivePlayer().getSelectedPlanet() == null) {
            GameManager.setScreenState(ScreenState.GAME_SCREEN);
        }

        g2.setColor(Color.white);

        Rectangle window = new Rectangle
                (WINDOW_OFFSET, WINDOW_OFFSET, WINDOW_WIDTH, WINDOW_HEIGHT);

        g2.draw(window);
        g2.setColor(Color.black);
        g2.fill(window);
        g2.setColor(Color.white);

        Planet selectedPlanet = GameManager.getActivePlayer().getSelectedPlanet();
        if (selectedPlanet == null) return;

        g2.drawString(selectedPlanet.getName(), NAME_X, NAME_Y);
        g2.drawString("Production Queue: " + GameManager.getActivePlayer().getSelectedPlanet()
                .productionQueueToString(), PROD_X, PROD_Y);

        ArrayList<GameButton> buttonArray = GameManager.getBuyMenuButtonArray();

        for (GameButton button: buttonArray) {
            g2.drawImage(button.getIcon(), button.getX(), button.getY(), this);
            g2.draw(button.getShape());
        }

        for (GameButton button: buttonArray) {
            double mouseX = GameManager.getGlobalMouseX();
            double mouseY = GameManager.getGlobalMouseY();

            if (button.getShape().contains(mouseX, mouseY)) {
                GameManager.addGameState(button.getButtonState());
            }
        }
    }

    /**
     * This method is used to draw ships.
     * @param g2 the graphics used.
     */
    public void drawShips(Graphics2D g2) {
        ArrayList<Entity> playerOneShips = GameManager.getPlayerOne().getShips();
        ArrayList<Entity> playerTwoShips = GameManager.getPlayerTwo().getShips();

        for (int index = 0; index < playerOneShips.size(); index++) {
            Entity ship = playerOneShips.get(index);

            if (ship.getHitPoints() <= 0) {
                playerOneShips.remove(ship);
                continue;
            }

            g2.drawImage(ship.getIcon(), ship.getX(), ship.getY(), this);
            g2.drawString(ship.getHitPoints() + "",
                    ship.getX() - SHIP_OFFSET, ship.getY() - SHIP_OFFSET);

            if (ship instanceof WarshipEntity) {
                g2.draw(((WarshipEntity) ship).getRangeBox());
            }
        }

        for (int ind = 0; ind < playerTwoShips.size(); ind++){
            Entity ship = playerTwoShips.get(ind);
            if (ship.getHitPoints() <= 0) {
                playerTwoShips.remove(ship);
                continue;
            }

            g2.drawImage(ship.getIcon(), ship.getX(), ship.getY(), this);
            g2.drawString(ship.getHitPoints() + "",
                    ship.getX() - SHIP_OFFSET, ship.getY() - SHIP_OFFSET);

            if (ship instanceof WarshipEntity) {
                g2.draw(((WarshipEntity) ship).getRangeBox());
            }
        }
    }

    /**
     * This method is a helper method to draw ships.
     * @param g2 the graphics used
     */
    public void drawShipContext(Graphics2D g2) {
        g2.setColor(Color.white);

        int possibleShipIndex = GameManager.getShipMouseLocation();

        if (possibleShipIndex <= -1) return;

        ArrayList<Entity> ships = GameManager.getActivePlayer().getShips();

        if (ships.size() == 0) return;

        if (ships.size() - 1 < possibleShipIndex) return;

        Entity ship = ships.get(possibleShipIndex);
        g2.setColor(Color.green);
        g2.drawRect(ship.getX(), ship.getY(), ship.getIcon().getWidth(),
                ship.getIcon().getHeight());
    }

    /**
     * Displays the techtree.
     * @param g2 the graphics used to display the tech tree.
     */
    private void techGUI(Graphics2D g2) {
        Player activePlayer = GameManager.getActivePlayer();
        TechTree activeTechTree = GameManager.getTechTree();
        int nodeNumber = 0;

        int thirdColX = (WINDOW_WIDTH / 2) + 340;
        int globalYOffset = WINDOW_OFFSET + ((WINDOW_HEIGHT / 2) / 2);

        g2.setColor(Color.white);
        g2.setFont(techFont);

        //Displays all the rectangles with the upgrades in them.

        Rectangle window = new Rectangle(WINDOW_OFFSET, WINDOW_OFFSET, WINDOW_WIDTH,
                WINDOW_HEIGHT);

        g2.draw(window);
        g2.setColor(Color.black);
        g2.fill(window);

        g2.setColor(Color.white);

        //Lines connecting the different upgrades
        //Lines from first upgrade to it's possibilities

        g2.drawLine((WINDOW_OFFSET + 5) + NODE_WIDTH, (WINDOW_OFFSET + (WINDOW_HEIGHT / 2) - 50)
                + (NODE_HEIGHT / 2), ((WINDOW_WIDTH / 2) - 50), (globalYOffset - 50) +
                (NODE_HEIGHT / 2));
        g2.drawLine((WINDOW_OFFSET + 5) + NODE_WIDTH, (WINDOW_OFFSET + (WINDOW_HEIGHT / 2) - 50)
                + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2) - 50, globalYOffset + 50 +
                (NODE_HEIGHT / 2));
        g2.drawLine((WINDOW_OFFSET + 5) + NODE_WIDTH, (WINDOW_OFFSET + (WINDOW_HEIGHT / 2) - 50)
                + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2) - 50, globalYOffset + 150 +
                (NODE_HEIGHT / 2));
        g2.drawLine((WINDOW_OFFSET + 5) + NODE_WIDTH, (WINDOW_OFFSET + (WINDOW_HEIGHT / 2) - 50)
                + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2) - 50, globalYOffset + 250 +
                (NODE_HEIGHT / 2));

        //Line from interplanetary economy to Stock market establishment

        g2.drawLine((WINDOW_WIDTH / 2) - 50  + NODE_WIDTH + 90, WINDOW_OFFSET +
                ((((WINDOW_HEIGHT / 2) / 2)) - 50) + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2) + 330
                , globalYOffset - 50 + (NODE_HEIGHT / 2));

        //Line from Trade to Automated Trading

        g2.drawLine((WINDOW_WIDTH / 2) - 50  + (NODE_WIDTH - 34), WINDOW_OFFSET +
                ((((WINDOW_HEIGHT / 2) / 2)) + 50) + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2) + 330,
                globalYOffset  + 25 + (NODE_HEIGHT / 2));

        //Line from weapons to increased defense and Advanced weapons

        g2.drawLine((WINDOW_WIDTH / 2) - 50  + (NODE_WIDTH - 14), WINDOW_OFFSET +
                ((((WINDOW_HEIGHT / 2) / 2)) + 150) + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2)
                + 330, globalYOffset  + 100 + (NODE_HEIGHT / 2));
        g2.drawLine((WINDOW_WIDTH / 2) - 50  + (NODE_WIDTH - 14), WINDOW_OFFSET +
                ((((WINDOW_HEIGHT / 2) / 2)) + 150) + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2) + 330,
                globalYOffset  + 175 + (NODE_HEIGHT / 2));

        //Line from Planetary Infrastructure to Worker Optimization

        g2.drawLine((WINDOW_WIDTH / 2) - 50  + NODE_WIDTH + 80, WINDOW_OFFSET +
                ((((WINDOW_HEIGHT / 2) / 2)) + 250) + (NODE_HEIGHT / 2), (WINDOW_WIDTH / 2)
                + 330, globalYOffset  + 250 + (NODE_HEIGHT / 2));

//        Rectangle flightBox = new Rectangle(WINDOW_OFFSET + 5, WINDOW_OFFSET +
//                (WINDOW_HEIGHT / 2) - 50, NODE_WIDTH, NODE_HEIGHT);
//        g2.draw(flightBox);

        g2.drawString(activeTechTree.get(nodeNumber).getName(), WINDOW_OFFSET + 16,
                WINDOW_OFFSET + (WINDOW_HEIGHT / 2) - 20);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), WINDOW_OFFSET + 16, WINDOW_OFFSET + (WINDOW_HEIGHT / 2));
        nodeNumber++;

        /*Rectangle interEcon = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset - 50,
                (NODE_WIDTH + 90), NODE_HEIGHT);
        g2.draw(interEcon);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), (WINDOW_WIDTH / 2) - 35,
                globalYOffset - 20);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), (WINDOW_WIDTH / 2) - 35, globalYOffset);
        nodeNumber++;

/*        Rectangle trade = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset + 50,
                (NODE_WIDTH - 34), NODE_HEIGHT);
        g2.draw(trade);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), (WINDOW_WIDTH / 2) - 35,
                globalYOffset + 80);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), (WINDOW_WIDTH / 2) - 35, globalYOffset + 100);
        nodeNumber++;

/*        Rectangle weapons = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset + 150,
                (NODE_WIDTH - 14), NODE_HEIGHT);
        g2.draw(weapons);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), (WINDOW_WIDTH / 2) - 35,
                globalYOffset + 180);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), (WINDOW_WIDTH / 2) - 35, globalYOffset + 200);
        nodeNumber++;

 /*       Rectangle planetInfra = new Rectangle((WINDOW_WIDTH / 2) - 50, globalYOffset + 250,
                (NODE_WIDTH + 80), NODE_HEIGHT);
        g2.draw(planetInfra);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), (WINDOW_WIDTH / 2) - 35,
                globalYOffset + 280);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), (WINDOW_WIDTH / 2) - 35, globalYOffset + 300);
        nodeNumber++;

/*        Rectangle stock = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset - 50,
                (NODE_WIDTH + 100), NODE_HEIGHT);
        g2.draw(stock);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), thirdColX, globalYOffset - 20);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), thirdColX, globalYOffset);
        nodeNumber++;

/*        Rectangle autoTrade = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 25,
                (NODE_WIDTH + 40), NODE_HEIGHT);
        g2.draw(autoTrade);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), thirdColX, globalYOffset + 55);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), thirdColX, globalYOffset + 75);
        nodeNumber++;

/*        Rectangle increasedDef = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 100,
                (NODE_WIDTH + 40), NODE_HEIGHT);
        g2.draw(increasedDef);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), thirdColX, globalYOffset + 130);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), thirdColX, globalYOffset + 150);
        nodeNumber++;

/*        Rectangle advWeap = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 175,
                (NODE_WIDTH + 50), NODE_HEIGHT);
        g2.draw(advWeap);*/
        g2.drawString(activeTechTree.get(nodeNumber).getName(), thirdColX, globalYOffset + 205);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), thirdColX, globalYOffset + 225);
        nodeNumber++;

 /*       Rectangle workOp = new Rectangle((WINDOW_WIDTH / 2) + 330, globalYOffset  + 250,
                (NODE_WIDTH + 50), NODE_HEIGHT);
        g2.draw(workOp);*/

        g2.drawString(activeTechTree.get(nodeNumber).getName(), thirdColX, globalYOffset + 280);
        g2.drawString(String.valueOf(activeTechTree.get(nodeNumber).getCost()), thirdColX, globalYOffset + 300);
        nodeNumber++;

        ArrayList<Rectangle> techTreeRectangles = GameManager.getTechTreeRectangles();
        HashSet<TechNode> techNodes = GameManager.getActivePlayer().getTechTreeList();
        System.out.println(techNodes.size());
        
        for (Rectangle techTreeBox: techTreeRectangles) {
            int index = techTreeRectangles.indexOf(techTreeBox);
            TechNode unlockedNode = null;

            for (TechNode node: techNodes) {
                if (index == node.getNumber()) unlockedNode = node;
            }

            if (unlockedNode != null) {
                g2.setColor(Color.green);
            }

            g2.draw(techTreeBox);

            g2.setColor(Color.white);
        }

        int mouseX = GameManager.getGlobalMouseX();
        int mouseY = GameManager.getGlobalMouseY();

        for (int techIndex = 0; techIndex < techTreeRectangles.size(); techIndex++) {
            Rectangle techTreeRect = techTreeRectangles.get(techIndex);
            boolean isClickable = GameManager.getActivePlayer().getClickableRectangles().contains(techTreeRect);

            if (techTreeRect.contains(mouseX, mouseY) && isClickable) {
                TechNode node = activeTechTree.get(techIndex);

                if (!(activePlayer.getMoney() - node.getCost() < 0)) {
                    activePlayer.removeMoney(node.getCost());
                    activePlayer.addTechTreeList(node);
                    GameManager.techTreeClickBuilder(node);
                }

                GameManager.setGlobalMouseX(0);
                GameManager.setGlobalMouseY(0);
            }
        }
    }

    /**
     * This method shows the number of movement points the selected ship has.
     * @param g2 the graphics used in the method.
     */
    private void drawHUD(Graphics2D g2) {
        Player activePlayer = GameManager.getActivePlayer();
        g2.drawString("Money: " + activePlayer.getMoney(), HUD_X, HUD_Y);

        Entity ship = activePlayer.getSelectedShip();

        if (ship != null) {
            int movementPoints = ship.getMovementPoints();

            g2.drawString("Movement points: " + movementPoints, HUD_X, HUD_Y_OFFSET);
        }
    }

    /**
     * This method is used to display a starting message to the player detailing how to play.
     * @param g2 the graphics used.
     */
    private void helpGUI(Graphics2D g2) {
        g2.setColor(Color.white);

        Rectangle window = new Rectangle(WINDOW_OFFSET, WINDOW_OFFSET, WINDOW_WIDTH,
                WINDOW_HEIGHT);

        g2.draw(window);
        g2.setColor(Color.black);
        g2.fill(window);
        g2.setColor(Color.white);

        g2.drawString("Welcome to VinVeli!", WINDOW_OFFSET + DRAW_HELP,
                WINDOW_OFFSET + DRAW_HELP_OFFSETX3);
        g2.drawString("Turn-based, space-based, civilization building and territory conquest" +
                " game. Player v Player in which players attempt to capture as many " +
                "capitals and planets as possible to win",
                WINDOW_OFFSET + DRAW_HELP, WINDOW_OFFSET + DRAW_HELP_OFFSETX10);
        g2.drawString("the game. They can do this through the construction of ships," +
                " upgrades, and colonization to fight until only one player remains.",
                WINDOW_OFFSET + DRAW_HELP, WINDOW_OFFSET + DRAW_HELP_OFFSETX13);
    }

    private void creditsGUI(Graphics2D g2) {
        g2.setColor(Color.white);

        Rectangle window = new Rectangle(WINDOW_OFFSET, WINDOW_OFFSET, WINDOW_WIDTH,
                WINDOW_HEIGHT);

        g2.draw(window);
        g2.setColor(Color.black);
        g2.fill(window);
        g2.setColor(Color.white);

        g2.drawString("Thank you for playing VinVeli :D", WINDOW_OFFSET + 5, WINDOW_OFFSET + 15);

        g2.drawString("Credits: ", WINDOW_OFFSET + 5, WINDOW_OFFSET + 45);

        g2.drawString("Lead Developer: Mithun Balasubramanian", WINDOW_OFFSET + 5, WINDOW_OFFSET + 60);
        g2.drawString("Developers: Sree Bommisetty & Nikhil Venkatachalam", WINDOW_OFFSET + 5, WINDOW_OFFSET + 75);

        g2.drawString("All art asset attributions can be found at: ", WINDOW_OFFSET + 5, WINDOW_OFFSET + 105);

    }

    private void processAnnex(Graphics2D g2) {
        int mouseX = GameManager.getGlobalMouseX();
        int mouseY = GameManager.getGlobalMouseY();

        ArrayList<AstralBody> world = GameManager.getWorld();
        ArrayList<Entity> ships = GameManager.getActivePlayer().getShips();
        ArrayList<Entity> otherShips = GameManager.getOtherPlayer().getShips();

        for (AstralBody body: world) {
            if (!(body instanceof Planet)) continue;

            Planet planet = (Planet) body;
            if (planet.getRadiusRect().contains(mouseX, mouseY)) {
                for (Entity ship: ships) {
                    if (ship instanceof WarshipEntity) {
                        if (planet.getRadiusRect().contains(ship.getX(), ship.getY())) {
                            boolean containsEnemy = false;

                            for (int otherIndex = 0; otherIndex < otherShips.size(); otherIndex++){
                                Entity otherShip = otherShips.get(otherIndex);
                                int x = otherShip.getX();
                                int y = otherShip.getY();

                                if (planet.getRadiusRect().contains(x, y)) {
                                    containsEnemy = true;
                                }
                            }


                            if (!containsEnemy) {
                                GameManager.getActivePlayer().addPlanet(planet);
                            }
                        }
                    }
                }
            }
        }
    }
}
