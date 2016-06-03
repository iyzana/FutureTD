package com.jaregames.futuretd.client;

import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.GameWindow.camera;

/**
 * Created by René on 03.06.2016.
 */
public class TileGrid {
    
    int width;
    int height;
    
    int maxTilesX;
    int maxTilesY;
    
    public final Tile[][] tiles;
    
    public TileGrid(int width, int height) {
        this.width = width;
        this.height = height;
        
        //TODO: get the right dimensions for the game window
        maxTilesX = 1920 / Tile.SIZE + 2;
        maxTilesY = 1080 / Tile.SIZE + 2;
    
        tiles = new Tile[width][height];
        
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(i * Tile.SIZE, j * Tile.SIZE);
            }
        }
    }
    
    public void update(double delta) {
        
    }
    
    /**
     * renders only the Tiles wich are seen by the camera
     *
     * @param g2d
     */
    public void render(Graphics2D g2d) {// TODO: Investigate the wierd offset of the tiles in the left and the top of the window
        
        int offsetTilesX = (int) (camera.getX() / Tile.SIZE);
        int offsetTilesY = (int) (camera.getY() / Tile.SIZE);
        
        g2d.drawString(offsetTilesX + ", " + offsetTilesY, 20, 20);
        
        for (int i = Math.max(0, offsetTilesX); i < Math.min(maxTilesX + offsetTilesX, tiles.length); i++) {
            for (int j = Math.max(0, offsetTilesY); j < Math.min(maxTilesY + offsetTilesY, tiles[0].length); j++) {
                tiles[i][j].render(g2d);
            }
        }
    }
}
