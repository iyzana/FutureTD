package com.jaregames.futuretd.client;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by René on 26.04.2016.
 */
public class ImageLoader {
    public static BufferedImage loadImage(String name){
        BufferedImage img = null;
        try {
            img = ImageIO.read(ImageLoader.class.getClass().getResourceAsStream("/images/"+name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Splits the image into chunks determined by the rows and columns
     *
     * @param image image to split
     * @param rows
     * @param columns
     * @return
     */
    public static BufferedImage[] chunkify(BufferedImage image, int rows, int columns) {
        int chunks = rows * columns;

        int chunkWidth = image.getWidth() / columns; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
}
