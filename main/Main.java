package Tetris.main;

import Tetris.constants.Constants;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        
        // Create a new window
        JFrame window = new JFrame();

        // Setup the window
        window.setTitle(Constants.TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(Constants.RESIZABLE);

        // Add the main Game window
        GamePanel gp = new GamePanel();

        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Run the game by doing this
        gp.startGameThread();

    }
}