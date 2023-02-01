/**
 * This class deals with the buttons used throughout the game. It includes methods to make
 * various renditions of a button, find out the location of the button or it's text, retrieve
 * the icon of the button, the label of the button, and the state of the button,
 * game and screen state to be specific. It also includes a method to see if a mouse
 * has clicked on the button.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.ui;

import main.java.game.GameState;
import main.java.game.ScreenState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class GameButton {

    private static final int BUTTONX = 10;
    private static final int BUTTONY = 35;

    Shape shape;
    String label;
    int textX;
    int textY;
    int x;
    int y;
    GameState buttonState;
    ScreenState screenState;
    BufferedImage icon;

    /**
     * One of four constructors for the class that makes the button based on shape, label, and
     * button state.
     * @param shape the shape to be used for the button.
     * @param label the label to give the button.
     * @param buttonState the button state to be set at start.
     */
    public GameButton(Shape shape, String label, GameState buttonState) {
        this.shape = shape;
        this.label = label;
        this.textX = (int) (shape.getBounds().getX() + BUTTONX);
        this.textY = (int) (shape.getBounds().getY() + BUTTONY);
        this.buttonState = buttonState;
    }

    /**
     * Two of four constructors for the class that makes the buttons with an icon, x and y
     * position, and state of the game.
     * @param icon the icon to be used for the game.
     * @param x x position of the button.
     * @param y y position of the button.
     * @param state Gamestate of the button.
     */
    public GameButton(BufferedImage icon, int x, int y, GameState state) {
        this.shape = new Rectangle(x, y, icon.getWidth(), icon.getHeight());
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.buttonState = state;
    }

    /**
     * Third of four constructors for the class. This one is based on just the shape and
     * buttonState.
     * @param shape shape for the button.
     * @param buttonState buttonState for the button to be used.
     */
    public GameButton(Shape shape, GameState buttonState) {
        this.shape = shape;
        this.buttonState = buttonState;
    }

    /**
     * Final constructor for the class that bases the button on
     * the shape, label, and x and y position of the button text.
     * @param shape the shape of the button.
     * @param label the label for the button.
     * @param textX the x location of the text for the button.
     * @param textY the y location of the text for the button.
     * @param screenState the screenstate to be given to the button.
     */
    public GameButton(Rectangle shape, String label, int textX, int textY,
                      ScreenState screenState) {
        this.shape = shape;
        this.label = label;
        this.textX = textX;
        this.textY = textY;
        this.screenState = screenState;
    }

    /**
     * gets the shape of the button.
     * @return the shape object of the button.
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * gets the label of the button.
     * @return string of the label of the button.
     */
    public String getLabel() {
        return label;
    }

    /**
     * gets the Y position of the text for the button.
     * @return integer value of the y position of the button text.
     */
    public int getTextY() {
        return textY;
    }

    /**
     * gets the X position of the text for the button.
     * @return integer value of the x position of the button text.
     */
    public int getTextX() {
        return textX;
    }

    /**
     * a boolean that indicates whether or not the mouse has clicked a certain position
     * that can be found in the parameters.
     * @param x the X position to check if the mouse has been clicked.
     * @param y the Y position to check if the mouse has been clicked.
     * @return true if the mouse has clicked in the given location.
     */
    public boolean didMouseClick(int x, int y) {
        return this.shape.contains(x, y);
    }

    /**
     * gets the state of the button.
     * @return GameState indicating the state of the button.
     */
    public GameState getButtonState() {
        return buttonState;
    }

    /**
     * gets the screen state of the button.
     * @return Screenstate indicating the screen state of the button.
     */
    public ScreenState getScreenState() {
        return screenState;
    }

    /**
     * gets the icon of the button in buffered image form.
     * @return a buffered image of the icon of the button.
     */
    public BufferedImage getIcon() {
        return icon;
    }

    /**
     * gets the x position of the button.
     * @return integer value of the x position of the button.
     */
    public int getX() {
        return x;
    }

    /**
     * gets the y position of the button.
     * @return integer value of the y position of the button.
     */
    public int getY() {
        return y;
    }
}
