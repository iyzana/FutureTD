package com.jaregames.futuretd.server.game.grid;


import com.jaregames.futuretd.server.pathfinding.TiledMap;
import com.jaregames.futuretd.server.pathfinding.TraversableNode;

/**
 * Created by René on 03.06.2016.
 */
public class TileGrid implements TiledMap {
    protected final int width;
    protected final int height;
    
    public Tile[][] tiles;
    
    public TileGrid(int width, int height) {
        this.width = width;
        this.height = height;
        
        initTiles();
    }
    
    protected void initTiles() {
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
    
    @Override
    public TraversableNode getNodeAt(int x, int y) {
        return getTileAt(x, y);
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
}
