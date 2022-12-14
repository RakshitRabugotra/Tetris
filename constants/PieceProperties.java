package Tetris.constants;

import java.awt.Color;

/*
 * This files contains the shapes for our Pieces
 */
public class PieceProperties {

    // Shapes will be contained in a 3D array
    public static final int[][][] SHAPES = new int[][][] {
        {
            {0, 0, 0},
            {1, 1, 1},
            {0, 0, 0}
        },
        {
            {2, 0, 0},
            {2, 2, 2},
            {0, 0, 0}
        },
        {
            {0, 0, 3},
            {3, 3, 3},
            {0, 0, 0}
        },
        {
            {0, 0, 0},
            {4, 4, 0},
            {4, 4, 0}
        },
        {
            {0, 0, 0},
            {0, 5, 5},
            {5, 5, 0} 
        },
        {
            {0, 6, 0},
            {6, 6, 6},
            {0, 0, 0}
        },
        {
            {0, 0, 0},
            {7, 7, 0},
            {0, 7, 7}
        }
    };

    // The color associated with each shape is like this:
    public static final Color[] COLORS = new Color[] {
        new Color(0, 0, 0),         // For the color-index 0 (Empty space)
        new Color(0, 240, 240),     // For the color-index 1
        new Color(0, 0, 240),       // For the color-index 2
        new Color(240, 160, 0),     // For the color-index 3
        new Color(240, 240, 0),     // For the color-index 4
        new Color(0, 240, 0),       // For the color-index 5
        new Color(160, 0, 240),     // For the color-index 6
        new Color(240, 0, 0),       // For the color-index 7
        new Color(128, 128, 128),   // For the color-index 8 (assigned default to walls)
        new Color(188, 169, 225)        // For the color-index 9 (disabled pieces)
    };

}