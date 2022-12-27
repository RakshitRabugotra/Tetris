package Tetris.main;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import SpaceScroller.constants.Constants;
import Tetris.constants.PieceProperties;
import java.awt.Graphics2D;

public class InfoPanel extends JPanel {
    
    /*
     * The info we're gonna display
     */
    private int gameScore = 0;
    private int linesCompleted = 0;
    private int[][] nextPieceShape = new int[3][3];

    // Private variable to hold the color-index of next piece
    private int nextPieceColorIndex = 0;

    // Customize this JPanel
    public InfoPanel() {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(false);
    }

    // Change the variables
    public void setState(int gameScore, int linesCompleted, int[][] nextPieceShape) {
        this.gameScore = gameScore;
        this.linesCompleted = linesCompleted;
        this.nextPieceShape = nextPieceShape;

        // Also fetch the color-index for this piece
        for(int row = 0; row < nextPieceShape.length; row++) {
            for(int col = 0; col < nextPieceShape[row].length; col++) {
                if(nextPieceShape[row][col] == 0) continue;
                // Else, copy the number and break
                nextPieceColorIndex = nextPieceShape[row][col];
                break;
            }
        }
    }

    // To change the information on the panel
    public void updatePanel() {
        /*
         * Let's setup our layout here
         */
        // Removes all the previous children
        this.removeAll();

        // Start adding new things from here!
        this.add(new JLabel("Score: " + gameScore));

        // Print the state of the information
        System.out.println("Score: " + gameScore);
        System.out.println("Lines: " + linesCompleted);
        System.out.println("Color-Index: " + nextPieceColorIndex);
        // System.out.println("Score: " + gameScore);
        
    }
    
    // To render the piece to the screen
    public void renderPieceOn(Graphics2D g2, int x, int y) {
        /*
         * If the shape of piece is null, then return
         */
        if(nextPieceShape == null) return;

        // Else, we will render the piece
        for(int row = 0; row < nextPieceShape.length; row++) {
            for(int col = 0; col < nextPieceShape[row].length; col++) {
                // Render the piece
                g2.setColor(PieceProperties.COLORS[nextPieceColorIndex]);
                // Render the rectangle
                g2.drawRect(x + col * Constants.SCALED_TILESIZE, y + row * Constants.SCALED_TILESIZE, Constants.SCALED_TILESIZE, Constants.SCALED_TILESIZE);
            }
        }

    }
}
