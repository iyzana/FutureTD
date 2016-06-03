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
    private BufferedImage[][] mapInChunks;
    private TileGrid tileGrid;
    
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
        tileGrid = new TileGrid(50, 30);
    }
    
    public void update(double delta) {
        camera.update(delta);
    }
    
    public void render(Graphics2D g) {
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 6; j++) {
//                int x = (1920 / 6) * i - (int) camera.getX();
//                int y = (1080 / 6) * j - (int) camera.getY();
//
//                g.drawImage(mapInChunks[i][j], x, y, null);
//                g.drawRect(x, y, 1920 / 6, 1080 / 6);
//            }
//        }
        tileGrid.render(g);

    }
}
