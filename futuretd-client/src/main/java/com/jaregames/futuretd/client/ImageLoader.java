package com.jaregames.futuretd.client;

import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 19:06
 *
 * @author René
 */
@Log4j2
public class ImageLoader {
    private static BufferedImage errorImage = loadImage("daMalRendern.png");
    
    public static BufferedImage loadImage(String name) {
        String path = "/images/" + name;
        try {
            URL resource = ImageLoader.class.getClass().getResource(path);
            if (resource == null) throw new FileNotFoundException("File \"" + path + "\" does not exist");
            
            return ImageIO.read(resource);
        } catch (Throwable e) {
            log.error("Error loading image " + name, e);
            return errorImage;
        }
    }
    
    /**
     * Splits the image into chunks determined by the rows and columns
     *
     * @param image   image to split
     * @param rows
     * @param columns
     * @return
     */
    public static BufferedImage[][] chunkify(BufferedImage image, int rows, int columns) {
        int chunks = rows * columns;
        
        int chunkWidth = image.getWidth() / columns; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;
        
        BufferedImage imgs[][] = new BufferedImage[rows][columns]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                imgs[x][y] = image.getSubimage(chunkWidth * x, chunkHeight * y, chunkWidth, chunkHeight);
            }
        }
        return imgs;
    }
}
