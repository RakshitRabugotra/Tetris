package Tetris.constants;

import java.awt.Color;

public class Constants {
    // Window Configuration    
    public static final String TITLE = "Tetris";
    public static final boolean RESIZABLE = false;
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    // To initialize Debug mode
    public static final boolean IS_DEBUG_MODE = false;

    // The desired FPS to run the game at
    public static int FPS = 5;
    // This is the desired Updates per frame
    public static int UPF = 10;

    // Speed the game by this factor on pressing down-key
    public static final int SPEED_UP_FACTOR = 3;

    // Information on the max rows and columns
    public static final int MAX_COLUMNS = 12;
    public static final int MAX_ROWS = 22;

    // Information on tilesize
    public static final int DEFAULT_TILESIZE = 32;
    public static final int GLOBAL_TILE_SCALE = 1;

    public static final int SCALED_TILESIZE = GLOBAL_TILE_SCALE * DEFAULT_TILESIZE;

}
