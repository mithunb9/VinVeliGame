/**
 * Main class for main.java.Game.
 * @author Mithun Balasubramanian
 * Collaborators: None
 * Teacher Name: Ms. Bailey
 * Due Date:
 * Period: 5
 */

package main.java;
import javax.swing.*;

public class Game extends JPanel {
    public static final String NAME = "VinVeli";

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();

        Game.initEngine(NAME);

        System.out.println("Initialized Game and Loaded Data in : "
                + (System.currentTimeMillis() - startTime) + "ms");
    }

    /**
     * initEngine initalizes the main.java.game engine.
     * @param name name of the window
     */
    public static void initEngine(String name) {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        GraphicsEngine graphicsEngine = new GraphicsEngine();
        graphicsEngine.setFocusable(true);

        frame.add(graphicsEngine);
        frame.pack();

        frame.setVisible(true);

        graphicsEngine.startThread();
    }
}

