package com.jaregames.futuretd.server.game.grid;



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

    public Tile getTileAt(int x, int y){
        return tiles[x][y];
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
