package com.jaregames.futuretd.client.game;

import com.jaregames.futuretd.client.assets.ImageLoader;
import com.jaregames.futuretd.client.game.grid.TileGrid;
import com.jaregames.futuretd.server.tower.TowerType;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static com.jaregames.futuretd.client.window.GameWindow.camera;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 19:06
 *
 * @author René
 */
public class GameMap {
    private final BufferedImage[][] mapInChunks;
    public final TileGrid grid;
    
    public GameMap() {
        mapInChunks = ImageLoader.chunkify(ImageLoader.loadImage("chunkTest.png"), 6, 6);
        
        grid = new TileGrid(500, 300);
        grid.tiles[1][1].addTower(TowerType.DEFAULT);
        grid.tiles[8][12].addTower(TowerType.DEFAULT);
        grid.tiles[9][23].addTower(TowerType.DEFAULT);
    }
    
    public void update(double delta) {
        camera.update(delta);
        
        grid.update(delta);
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
        grid.render(g);
    }
}
