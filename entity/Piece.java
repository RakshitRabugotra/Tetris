package Tetris.entity;

import java.awt.Graphics2D;

import Tetris.constants.PieceProperties;
import Tetris.main.KeyHandler;

import java.awt.Color;

public class Piece extends Entity {
    // The attributes related to the Piece
    public int colorIndex = 0;
    public Color color;
    public int[][] shape;

    // Is the piece active?
    public boolean isActive = false;

    // Can the piece move in these direction... to avoid overlapping
    public boolean canMoveRight = true, canMoveLeft = true, canMoveDown = true;

    // The instance of KeyHandler to handle events
    private KeyHandler keyH; 

    public Piece(KeyHandler keyH, int posX, int posY, int entityWidth, int entityHeight, int colorIndex) {
        // Call the superclass constructor to set the position and dimensions
        super(posX, posY, entityWidth, entityHeight);
        // Set the color of the piece
        this.colorIndex = colorIndex;
        this.color = PieceProperties.COLORS[colorIndex];
        this.shape = PieceProperties.SHAPES[colorIndex];

        // Copy the reference to the object
        this.keyH = keyH;

        // The piece is active now
        this.isActive = true;
    }

    @Override
    public void update(double dt) {
        /*
         * We will update the positions of the object here
         */
        // If the piece isn't active... then why update it
        if(!isActive) return;

        // We can also rotate the block if the block is worth rotating
        if(this.colorIndex != 4) {
            // Check and rotate the piece accordingly
        }

        // We can move the piece on X-axis
        if(keyH.leftPressed && canMoveLeft) this.x--;
        if(keyH.rightPressed && canMoveRight) this.x++;

        // Make the Piece fall down

    }

    @Override
    public void draw(Graphics2D g2) {
        /*
         * Piece doesn't need to have draw method
         */
        return;
    }
    
}