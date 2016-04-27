package com.jaregames.futuretd.client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static com.jaregames.futuretd.client.GameWindow.camera;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 19:06
 *
 * @author René
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
    
    public void update(double delta) {
        camera.update(delta);
    }
    
    public void render(Graphics2D g) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                g.drawImage(mapInChunks[i][j], (1920 / 6) * i -(int)camera.getX(), (1080 / 6) * j  -(int)camera.getY(), null);
                g.drawRect((1920 / 6) * i -(int)camera.getX(), (1080 / 6) * j  -(int)camera.getY(), 1920/6, 1080/6);
            }
        }
    }
}
