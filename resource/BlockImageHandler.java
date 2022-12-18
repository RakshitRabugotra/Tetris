package Tetris.resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import Tetris.constants.PieceProperties;

public class BlockImageHandler {

    // Store the image files in this array
    private static BufferedImage[] wallImages = new BufferedImage[PieceProperties.COLORS.length];
    
    public static BufferedImage createImage(String filepath) throws IOException {
        // Create a file from this path
        File imageFile = new File(filepath);
        // Convert this to input stream
        try {
            InputStream targetImageStream = new FileInputStream(imageFile);
            // Return a buffered image object
            return ImageIO.read(targetImageStream);

        } catch(FileNotFoundException fnfe) {

            System.out.println("Filepath '" + filepath + "' couldn't be found");
            return null;
        }
    }

    public static void setWallImages(String directoryPath) {
        // Check if the path of the folder is valid
        // directoryPath = System.getProperty("user.dir") + directoryPath;
        // Fetch the contents of this file
        String contentFilenames[] = new File(directoryPath).list();
        // The filenames would be of name 0.png, 1.png, 2.png... 9.png
        // All of them would match the exact same color in the COLOR array

        for(String filename: contentFilenames) {
            // if the filename is like '1' (number) + '.png', then it a tile image
            if(!filename.endsWith(".png")) continue; 
            
            // Fetch the color index
            int colorIndex = filename.charAt(0) - '0';

            // if the filename doesn't start with a number then skip
            if(0 <= colorIndex && colorIndex <= 9) {
                // Then we will fetch this image add to the array
                try {
                    wallImages[colorIndex] = createImage(directoryPath + "/" + filename);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public static BufferedImage getColorIndexImage(int colorIndex) {
        // Fetch the wall of same color at this index
        return wallImages[colorIndex];
    }
}
