package com.jaregames.futuretd.client.game.grid;

import com.jaregames.futuretd.client.input.Mouse;

import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.window.GameWindow.camera;

/**
 * Created by René on 03.06.2016.
 */
public class TileGrid {
    private static final int maxTilesX = 1920 / Tile.SIZE + 2;
    private static final int maxTilesY = 1080 / Tile.SIZE + 2;
    
    private final int width;
    private final int height;
    
    public final Tile[][] tiles;
    
    public TileGrid(int width, int height) {
        this.width = width;
        this.height = height;
        
        tiles = new Tile[width][height];
        
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(i, j, this);
            }
        }
    }
    
    public void update(double delta) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y].update(delta);
            }
        }
    }
    
    /**
     * renders only the Tiles which are seen by the camera
     *
     * @param g
     */
    public void render(Graphics2D g) {
        int offsetX = (int) camera.getX() / Tile.SIZE;
        int offsetY = (int) camera.getY() / Tile.SIZE;
        
        g.drawString(offsetX + ", " + offsetY, 20, 20);
        g.drawString("Mouse: X: " + Mouse.getX() + " Y: " + Mouse.getY(), 20, 30);
        g.drawString("Camera: X: " + camera.getX() + " Y: " + camera.getY(), 20, 40);
        
        for (int x = Math.max(0, offsetX); x < Math.min(offsetX + maxTilesX, tiles.length); x++) {
            for (int y = Math.max(0, offsetY); y < Math.min(offsetY + maxTilesY, tiles[0].length); y++) {
                tiles[x][y].render(g);
            }
        }
    }

    public Tile getTileAt(int x, int y){
        return tiles[x][y];
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
