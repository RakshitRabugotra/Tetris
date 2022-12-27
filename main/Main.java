package Tetris.main;

import Tetris.constants.Constants;
import javax.swing.JFrame;

public class Main {
    
    /*
     * Some private constants
     */
    private static  final int gameScreenWidth = Constants.MAX_COLUMNS * Constants.SCALED_TILESIZE;
    private static final int gameScreenHeight = (Constants.MAX_ROWS+1) * Constants.SCALED_TILESIZE;

    public static void main(String[] args) {
        
        // Create a new window
        JFrame window = new JFrame();

        // Setup the window
        window.setTitle(Constants.TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(Constants.RESIZABLE);

        // Set the window's actual size
        window.setSize(gameScreenWidth * 2, gameScreenHeight);

        // Add the information Window
        InfoPanel ip = new InfoPanel();
        // Set bounds for this InfoPanel
        ip.setBounds(gameScreenWidth, 0, gameScreenWidth, gameScreenHeight);

        // Add the main Game window
        // We can pass in the information panel object reference to update the score
        // and the next piece on the screen
        GamePanel gp = new GamePanel(ip);
        // Set bounds for this GamePanel
        gp.setBounds(0, 0, gameScreenWidth, gameScreenHeight);
    
        
        // window.pack();
        window.add(gp);
        window.add(ip);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Run the game by doing this
        gp.startGameThread();

    }
}