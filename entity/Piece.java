package Tetris.entity;

import java.awt.Graphics2D;

import Tetris.constants.Constants;
import Tetris.constants.PieceProperties;
import Tetris.main.KeyHandler;
import Tetris.resource.Matrix;

import java.awt.Color;

public class Piece extends Entity {
    // The attributes related to the Piece
    public int colorIndex = 0;
    public Color color;
    public int[][] shape;

    // Keeping track of FPS to speed up the tile falling down
    private static final int originalFPS = Constants.FPS;

    // Is the piece active?
    public boolean isActive = false;

    // Can the piece move in these direction... to avoid overlapping
    public boolean canMoveRight = true, canMoveLeft = true, canMoveDown = true;

    // Set the upper bound of the Piece... the piece cannot be instantiated above this Y
    public int upperBound = 0;

    // The instance of KeyHandler to handle events
    private KeyHandler keyH; 

    public Piece(KeyHandler keyH, int posX, int posY, int colorIndex) {
        // Call the superclass constructor to set the position and dimensions
        super(posX, posY, 3, 3);
        // Set the color of the piece
        this.colorIndex = colorIndex;
        this.color = PieceProperties.COLORS[colorIndex];
        this.shape = PieceProperties.SHAPES[colorIndex];

        // Copy the reference to the object
        this.keyH = keyH;

        // The piece is active now
        this.isActive = true;

        // Set the FPS to original FPS
        Constants.FPS = originalFPS;
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

            // For the clockwise rotation
            if(keyH.rotateClockwisePressed) {
                this.shape = Matrix.rotateClockWise(new Matrix(shape)).getShape();
                return;
            }
            // For the counter-clockwise rotation
            if(keyH.rotateCounterClockwisePressed) {
                this.shape = Matrix.rotateCounterClockWise(new Matrix(shape)).getShape();
                return;
            }
        }

        // We can move the piece on X-axis
        if(keyH.leftPressed && canMoveLeft) this.x--;
        if(keyH.rightPressed && canMoveRight) this.x++;

        if(!Constants.IS_DEBUG_MODE) {
            // Make the Piece fall down
            if(canMoveDown) this.y++;

            // If the user pressed down key... then speed up the game
            if(keyH.downPressed && Constants.FPS == originalFPS) Constants.FPS *= Constants.SPEED_UP_FACTOR;

        } else {
            // In debug mode we're free to move the piece
            if(keyH.downPressed && canMoveDown) this.y++;
            if(keyH.upPressed) this.y--;
        }

        // Also we need to bound the Y position of this piece
        this.y = Math.max(this.upperBound, this.y);
    }

    @Override
    public void draw(Graphics2D g2) {
        /*
         * Piece doesn't need to have draw method
         */
        return;
    }

    /*
     * To return a copy of same shape but with new color
     */
    public int[][] changeColor(int colorIndex) {

        // If the color is not valid, then return
        if (!(0 <= colorIndex && colorIndex <= 9)) {
            return new int[this.height][this.width];
        }

        // Make a copy of this shape
        int[][] newColorShape = new int[this.height][this.width];

        // Iterate in the shape
        for(int row = 0; row < this.height; row++) {
            for(int col = 0; col < this.width; col++) {
                // If the cell is non-empty, then replace it with the new color
                if(this.shape[row][col] != 0) newColorShape[row][col] = colorIndex;
                // Else, we will copy the zeros
                else newColorShape[row][col] = this.shape[row][col];
            }
        }

        return newColorShape;
    }

    
}