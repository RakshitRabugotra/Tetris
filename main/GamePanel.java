package Tetris.main;

import Tetris.constants.Constants;
import Tetris.entity.Grid;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Setup our screen
    public final int screenWidth = Constants.MAX_COLUMNS * Constants.SCALED_TILESIZE;
    public final int screenHeight = Constants.MAX_ROWS * Constants.SCALED_TILESIZE;

    // The game thread on which the game will run
    private Thread gameThread;

    // To handle the key press events
    public KeyHandler keyH = new KeyHandler();

    /*
     * Instantiate new Objects here!!
     */
    Grid grid = new Grid(this, keyH, 0, 0, Constants.MAX_COLUMNS, Constants.MAX_ROWS);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Constants.BACKGROUND_COLOR);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // To start the game
    public void startGameThread() {
        // Initialize the game thread
        // Passing in 'this' because this class contains the run method
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(double dt) {
        /*
         * Update all the GameObjects here!
         */
        // Update the grid...
        grid.update(dt);
    }

    public void paintComponent(Graphics g) {
        // Call the parent's implementation of this function
        super.paintComponent(g);

        // Cast the graphics to graphics2D for ease of use
        Graphics2D g2 = (Graphics2D) g;

        /*
         * Draw GameObjects here!
         */
        grid.draw(g2);

        // Dispose the graphics at end of function to release some memory
        g2.dispose();
    }

    // The GameLoop resides in here!
    @Override
    public void run() {

        /*
         * Logic for the GameLoop in here!!
         */
        // Capture the time at start of the frame with this
        long frameStartTime = 0;

        int frameCounter = 1;

        // Start the GameLoop
        while(gameThread != null) {
            // First of all let's calculate the frame delay, repeatedly because we can vary the FPS
            long frameDelay = 1000/Constants.FPS;

            // Fetch the start time of this frame
            frameStartTime = System.currentTimeMillis();

            // Handle all the events, update and draw
            // We will update only every UPF frames per time
            if(frameCounter % (Constants.UPF+1) == 0) {
                update(1);
                frameCounter = 1;
            }
            repaint();

            frameStartTime = System.currentTimeMillis() - frameStartTime;

            // If we still have some time to delay then do this
            if(frameDelay > frameStartTime) {
                // The above statement is equivalent to: frameDelay - frameStartTime > 0
                try {
                    Thread.sleep(frameDelay - frameStartTime);
                } catch (InterruptedException e) {
                    // To handle the error
                    e.printStackTrace();
                }
            }

            frameCounter++;
        }
    }

}
