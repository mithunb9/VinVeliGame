/**
 * The UIKeyListener class implements the KeyListener interface and is used to capture keystrokes
 * made in the game.
 * @author: Mithun Balasubramaniam
 * Collaborators: Sree Bommisetty, Nikhil Venkatachalam
 * Teacher name: Ms. Bailey
 * Period: 5
 * Due Date: 5/19/2022
 */
package main.java.game.ui;

import main.java.GameManager;
import main.java.game.GameState;
import main.java.game.ScreenState;
import main.java.game.world.Planet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UIKeyListener implements KeyListener {

    /**
     * Outlines what happens if a key is typed
     * @param e the typed key
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Outlines what happens if a key is pressed
     * @param e the pressed key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_M) {
            if (GameManager.getGameState().contains(GameState.MUTE_SOUND)) {
                GameManager.removeGameState(GameState.MUTE_SOUND);
                GameManager.addGameState(GameState.PLAY_MUSIC);
            }
            else {
                GameManager.removeGameState(GameState.MUTE_SOUND);
                GameManager.addGameState(GameState.MUTE_SOUND);
            }
        } else if (keyCode == KeyEvent.VK_B) {
            Planet selectedPlanet = GameManager.getActivePlayer().getSelectedPlanet();

            if (selectedPlanet == null) return;
            if (!GameManager.getActivePlayer().owns(selectedPlanet)) return;

            if (GameManager.getScreenState() == ScreenState.BUY_SCREEN) {
                GameManager.setScreenState(ScreenState.GAME_SCREEN);
            } else if (GameManager.getScreenState() == ScreenState.GAME_SCREEN) {
                GameManager.setScreenState(ScreenState.BUY_SCREEN);
            }
        } else if (keyCode == KeyEvent.VK_N) GameManager.addGameState(GameState.NEXT_TURN);
        else if (keyCode == KeyEvent.VK_T) {
            if (GameManager.getScreenState() == ScreenState.TECH_SCREEN) {
                GameManager.setScreenState(ScreenState.GAME_SCREEN);
            } else if (GameManager.getScreenState() == ScreenState.GAME_SCREEN) {
                GameManager.setScreenState(ScreenState.TECH_SCREEN);
            }
        }
        else if (keyCode == KeyEvent.VK_UP)  GameManager.setKeyState(GameState.KEY_UP);
        else if (keyCode == KeyEvent.VK_DOWN) GameManager.setKeyState(GameState.KEY_DOWN);
        else if (keyCode == KeyEvent.VK_LEFT) GameManager.setKeyState(GameState.KEY_LEFT);
        else if (keyCode == KeyEvent.VK_RIGHT) GameManager.setKeyState(GameState.KEY_RIGHT);
    }

    /**
     * Outlines what happens if a key is released
     * @param e the released key
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
