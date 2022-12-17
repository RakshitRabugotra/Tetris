package Tetris.entity;

import java.awt.Graphics2D;
import java.util.Random;

import Tetris.constants.Constants;
import Tetris.constants.PieceProperties;
import Tetris.main.GamePanel;
import Tetris.main.KeyHandler;
import Tetris.resource.Matrix;

public class Grid extends Entity {
    
    /*
    The constructor requires to specify the size and position of grid,
    also a reference to GamePanel
    */
    private GamePanel gp;
    private KeyHandler keyH;
    
    // Private RNG to generate Random positions
    private Random RNG;
    
    // To check whether we should spawn a new piece in the scene
    private boolean shouldSpawnNewPiece = false;

    // The color of wall in this grid is final
    private final int wallColorIndex = 8;
    private final int disabledColorIndex = 9;
    private final int spawnColor = 0;

    // Keep track of current Piece
    private Piece currentActivePiece = null;

    // Keep track of the current Piece speed
    private int gameSpeedThreshold = Constants.GAME_SPEED_INCREASE_STEP;

    // We will make a separate grid... which will be displayed on the screen
    // when the grid is cleared
    public int[][] permanentGridShape;

    // This grid also has some shape
    public int[][] currentGridShape;

    // The score as of right now
    public int score = 0;
    
    public Grid(GamePanel gp, KeyHandler keyH, int posX, int posY, int gridWidth, int gridHeight) {
        // Place the grid at given position
        // and also give it a size
        super(posX, posY, gridWidth, gridHeight);
        
        // Copy the GamePanel reference
        this.gp = gp;
        this.keyH = keyH;

        // Initialize an empty grid first and then fill it with borders
        currentGridShape = new int[gridHeight][gridWidth];
        
        // Initialize the permanent grid with borders
        permanentGridShape = new int[gridHeight][gridWidth];
        for(int row = 0; row < gridHeight; row++) {
            for(int col = 0; col < gridWidth; col++) {
                if(row == 0 || col == 0 || row == gridHeight-1 || col == gridWidth-1) permanentGridShape[row][col] = wallColorIndex;
                else permanentGridShape[row][col] = 0;
            }
        }
        // This will copy the permanent grid to the current grid
        copyPermanentGrid();

        // Initialize the RNG
        RNG = new Random(System.currentTimeMillis());

        // Instantiate and place a Piece
        currentActivePiece = instantiatePiece(spawnColor);
    }

    // The grid will update the pieces residing in it
    @Override
    public void update(double dt) {
        // Clear the grid
        copyPermanentGrid();

        // Let's check if the game is over
        if(isGameOver()) {
            return;
        }

        // Check if we should spawn a new piece
        shouldSpawnNewPiece = (currentActivePiece == null);

        // If we are meant to place a new piece... then instantiate a new piece
        if(this.shouldSpawnNewPiece) {
            currentActivePiece = instantiatePiece(spawnColor);
            this.shouldSpawnNewPiece = false;
            // If this happens then the piece has become permanent on grid
            // So, we would play this sound
            Constants.soundEffects.get("on-collide").play();
            // Also, reset the FPS, if the game was temporarily Sped up
            if(Constants.varCurrentFPS > Constants.constFPS) Constants.varCurrentFPS = Constants.constFPS;
        }

        /*
         * Let's set proper rotation for the piece
         */
        rotatePiece(currentActivePiece);
        
        // Update the current piece and check for collisions
        currentActivePiece.update(dt);

        // Place the piece on the current grid
        placePiece(currentActivePiece, currentGridShape);

        // Check for collisions on the X-Axis
        currentActivePiece.canMoveLeft = !isPieceCollidingLeft(currentActivePiece);
        currentActivePiece.canMoveRight = !isPieceCollidingRight(currentActivePiece);
        currentActivePiece.canMoveDown = !isPieceCollidingBottom(currentActivePiece);

        // Check for bottom collisions
        if(!currentActivePiece.canMoveDown) {
            // Change the color of piece
            currentActivePiece.shape = currentActivePiece.changeColor(disabledColorIndex);
            // Copy the piece to the permanent grid
            placePiece(currentActivePiece, permanentGridShape);
            // Clean this piece, so a new one would be instantiated
            currentActivePiece = null;
        }

        // Remove all the removable rows
        removeScoreRows();

        // Print the score
        System.out.println("SCORE: " + this.score);
        // Print is the game over
        System.out.println("GAME OVER: " + isGameOver());
        // Print the Game FPS
        System.out.println("FPS: " + Constants.constFPS);
        System.out.println("Current FPS: " + Constants.varCurrentFPS);
    }

    @Override
    // We will also render the whole grid here
    public void draw(Graphics2D g2) {
        // Draw the grid
        for(int row = 0; row < this.height; row++) {
            for(int col = 0; col < this.width; col++) {
                // Fetch the color index at this position
                int colorIndex = this.currentGridShape[row][col];
                // Simply render the tile at this position offset by (tileSize)
                this.renderGridTile(g2, col*Constants.SCALED_TILESIZE, row*Constants.SCALED_TILESIZE, colorIndex);
            }
        }
    }

    // To rotate the piece
    private void rotatePiece(Piece p) {
        // We can also rotate the block if the block is worth rotating
        if(p.colorIndex == 4) return;
        // Check and rotate the piece accordingly

        // To avoid repeated actions, we will wrap the logic a little bit by doing this
        // If we're not rotating the piece then don't do ahead of this point
        if(!keyH.rotateClockwisePressed && !keyH.rotateCounterClockwisePressed) return;

        // For the clockwise rotation
        if(keyH.rotateClockwisePressed) {
            p.shape = Matrix.rotateClockWise(new Matrix(p.shape)).getShape();
        }
        // For the counter-clockwise rotation
        if(keyH.rotateCounterClockwisePressed) {
            p.shape = Matrix.rotateCounterClockWise(new Matrix(p.shape)).getShape();
        }

        // Now check if this piece is colliding left or right
        while(isPieceCollidingLeft(p)) {
            // Then we will move the piece to the right
            // till it's not colliding anymore
            System.out.println("Moving the piece to right");
            p.x++;
        }
        while(isPieceCollidingRight(p)) {
            // Then we will move the piece to the left
            // till it's not colliding anymore
            System.out.println("Moving the piece to left");
            p.x--;
        }
        p.canMoveDown = true; 
    }

    // To generate Piece
    private Piece instantiatePiece(int pieceColorIndex) {
        int posX = RNG.nextInt(1, this.width-1-3);
        int posY = 0;

        // If the piece color index is less than 1 or greater than 8 then.
        // we will randomize the color
        int colorIndex = (pieceColorIndex < 1 || pieceColorIndex > 8) ? RNG.nextInt(1, 8) : pieceColorIndex;

        Piece p = new Piece(keyH, posX, posY, colorIndex);
        p.upperBound = 1;
        // Instantiate a new piece and set its bound
        return p;
    }

    // Function to draw a Piece on the grid
    private void placePiece(Piece p, int[][] grid) {
        // Copy the Piece's shape to the grid

        // If the shape is null then don't do anything
        if(p.shape == null) return;
        
        // Start from piece's X to piece's X + Width
        for(int row = p.y; row < p.y + p.height; row++) {
            for(int col = p.x; col < p.x + p.width; col++) {
                // Let's copy these values over to the grid
                // Only if the value isn't 0
                if(p.shape[row-p.y][col-p.x] != 0) {
                    grid[row][col] = p.shape[row-p.y][col-p.x];
                }
            }
        }
    }

    /*
     * Collision Checks below!!
     */

    // Function to check if the piece collides from the bottom
    private boolean isPieceCollidingBottom(Piece p) {
        /*
         * Bottom collision check
         */

        /*
         * Positive condition:
         * [ 0 0 0 ]
         * [ 4 4 0 ]
         * [ 4 4 0 ]
         * [ 8 8 8 ]
         */
        int top = -1, bottom = -1;

        for(int col = p.x; col < p.x + p.width; col++) {
            // Move in the pairs of two
            for(int row = p.y + p.height; row > p.y; row--) {

                try {
                    // Fetch the top and bottom pair
                    top = currentGridShape[row-1][col];
                    bottom = currentGridShape[row][col];
                } catch(ArrayIndexOutOfBoundsException outOfBoundsException) {
                    // If the array gets out of bounds... then there's a definite collision
                    return true;
                }

                // If the pair is like this then there's a collision
                if(top != bottom && top != 0 && bottom != 0) return true;
            }
        }

        return false;
    }

    // Function to check if the piece collides from left
    private boolean isPieceCollidingLeft(Piece p) {
        /*
         * Checking collision on the left
        */

        // If the position has become negative, then definitely return true
        if(p.x < 0) return true;

        // Let's check the first and second column side-by-side.
        // If there's any pair of wall-color-index and non-zero number
        // then it's a collision

        int left = -1, right = -1;

        for(int row = p.y ; row < p.y + p.height; row++) {
            try {
                // The left side column-pair
                left = currentGridShape[row][p.x-1];
                right = currentGridShape[row][p.x];
            } catch(ArrayIndexOutOfBoundsException outOfBoundsException) {
                // If we're indexing outside the grid.. then the collision is definite
                return true;
            }

            // If the pair is like this then there's a collision
            if(left != right && left != 0 && right != 0) return true;
        }


        return false;
    }

    // Function to check for collisions on the right
    private boolean isPieceCollidingRight(Piece p) {
        /*
         * Check for collisions on the right
         */

        // Let's check the last two columns in extended 5x5 matrix form
        // example:
        /*         l r
         * [ 0 0 0 0 8 ]
         * [ 0 2 0 0 8 ]
         * [ 0 2 2 2 8 ]
         * [ 0 0 0 0 8 ]
         * [ 0 0 0 0 8 ]
         * 
         * Should return true... as
         *  l != r and l != 0 and r != 0
         */

        int left = -1, right = -1;

        for(int row = p.y; row < p.y + p.height; row++) {

            for(int col = p.x + p.width; col > p.x; col--) {
                try {
                    // The left side column-pair
                    left = currentGridShape[row][col-1];
                    right = currentGridShape[row][col];
                } catch(ArrayIndexOutOfBoundsException outOfBoundsException) {
                    // If we're indexing outside the grid.. then the collision is definite
                    return true;
                }

                // If the pair is like this then there's a collision
                if((left != right) && left != 0 && right != 0) return true;
            }

        }

        return false;
    }

    // Function to initialize the grid shape with borders
    private void copyPermanentGrid() {
        // Copy the permanent grid to the current grid
        for(int row = 0; row < this.height; row++) {
            for(int col = 0; col < this.width; col++) {
                this.currentGridShape[row][col] = this.permanentGridShape[row][col];
            }
        }
    }

    // Function to clear the grid when a score happens
    private void removeScoreRows() {
        // We will clear the row which is filled with tiles
        int bonus = this.height;
        int rowsRemoved = 0;

        for(int row = this.height-1; row >= 0; row--) {
            // Check If all the elements are disabled tiles
            boolean isRowRemovable = true; 

            for(int col = 1; col < this.width-1; col++) {
                if(permanentGridShape[row][col] != disabledColorIndex) {
                    isRowRemovable = false;
                    break;
                }
            }

            if(!isRowRemovable) continue;

            // If the row is removable then... remove the row
            for(int k = row; k > 1; k--) {
                permanentGridShape[k] = permanentGridShape[k-1];
            }
            rowsRemoved++;
            // Play the sound on removing a row
            Constants.soundEffects.get("on-score").play();
        }

        this.score += rowsRemoved * bonus;
        // Also check if the score has passed certain threshold,
        // Then we should increase the game speed
        if(this.score >= gameSpeedThreshold) {
            gameSpeedThreshold += Constants.GAME_SPEED_INCREASE_STEP;
            Constants.constFPS += 2;
        }
    }

    // Function to check whether the game is over or not?
    public boolean isGameOver() {
        /*
         * If the spawn region has any tile in it... then the game is over
         */
        /*
         * Spawn region is first 3 rows after the top wall
         */
        for(int row = 1; row <= 3; row++) {
            // Check if the row is filled with zeros or not
            for(int col = 1; col < this.width-1; col++) {
                // this.width-1 because we don't want to iterate on the wall
                if(permanentGridShape[row][col] != 0) return true;
            }
        }

        return false;
    }

    // Function to render a single tile
    private void renderGridTile(Graphics2D g2, int posX, int posY, int colorIndex) {
        // First we will create a filled rectangle
        g2.setColor(PieceProperties.COLORS[colorIndex]);
        g2.fillRect(posX, posY, Constants.SCALED_TILESIZE, Constants.SCALED_TILESIZE);
        // Then we will draw an outline rectangle over it,
        // with the color same as background color in GamePanel
        g2.setColor(gp.getBackground());
        g2.drawRect(posX, posY, Constants.SCALED_TILESIZE, Constants.SCALED_TILESIZE);
    }

}
