package com.jaregames.futuretd.client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Created by René on 26.04.2016.
 */
public class GameMap {
    BufferedImage[][] mapInChunks;
    
    
    public GameMap() {
        BufferedImage[] temp = ImageLoader.chunkify(ImageLoader.loadImage("chunkTest.png"), 6, 6);
        int counter = 0;
        mapInChunks = new BufferedImage[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                mapInChunks[j][i] = temp[counter];
                counter++;
            }
        }
    }
    
    public void update() {
        
    }
    
    public void render(Graphics2D g) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                g.drawImage(mapInChunks[i][j], (1920 / 6) * i, (1080 / 6) * j, null);
            }
        }
    }
}
