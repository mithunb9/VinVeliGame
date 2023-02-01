/**
 * The UIMouseListener class implements the MouseListener interface and is used to capture mouse
 * actions made in the game.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.ui;

import main.java.GameManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UIMouseListener implements MouseListener {
    /**
     * checks if the mouse is clicked during the event e.
     * @param e the MouseEvent checked.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
//        GameManager.getIndexAtMouseLocation(e.getX(), e.getY());
    }

    /**
     * Checks if the mouse is pressed during the event e.
     * @param e the MouseEvent checked.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        GameManager.getIndexAtMouseLocation(e.getX(), e.getY());
    }

    /**
     * Checks for the release of the mouse during the event.
     * @param e the MouseEvent checked.
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Checks for the entering of the mouse during the event.
     * @param e the MouseEvent checked.
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * checks for the exiting of the mouse during the event.
     * @param e the MouseEvent checked.
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}

