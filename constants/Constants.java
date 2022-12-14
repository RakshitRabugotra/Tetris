package Tetris.constants;

import java.awt.Color;

public class Constants {
    // Window Configuration    
    public static final String TITLE = "Tetris";
    public static final boolean RESIZABLE = false;
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    // To initialize Debug mode
    public static final boolean IS_DEBUG_MODE = true;

    // The desired FPS to run the game at
    public static final int FPS = 5;

    // Properties of some GameObjects/Entities
    public static final int PIECE_GRAVITY = 1;
    
    // Information on the max rows and columns
    public static final int MAX_COLUMNS = 12;
    public static final int MAX_ROWS = 22;

    // Information on tilesize
    public static final int DEFAULT_TILESIZE = 32;
    public static final int GLOBAL_TILE_SCALE = 1;

    public static final int SCALED_TILESIZE = GLOBAL_TILE_SCALE * DEFAULT_TILESIZE;

}
