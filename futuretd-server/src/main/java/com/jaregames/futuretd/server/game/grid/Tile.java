package com.jaregames.futuretd.server.game.grid;


import com.jaregames.futuretd.server.pathfinding.TraversableNode;
import com.jaregames.futuretd.server.tower.Tower;
import com.jaregames.futuretd.server.tower.TowerType;

import java.awt.Graphics2D;

/**
 * Created by René on 03.06.2016.
 */
public class Tile implements TraversableNode {
    protected final int x;
    protected final int y;
    
    protected Tower tower;
    protected final TileGrid parentGrid;
    protected boolean towerRoot; // if the tile is the root tile for a tower
    
    public Tile(int x, int y, TileGrid parentGrid) {
        this.x = x;
        this.y = y;
        this.parentGrid = parentGrid;
        towerRoot = false;
    }
    
    public void update(double delta) {
        
    }
    
    public void render(Graphics2D g) {
        throw new UnsupportedOperationException("Server mustn't render");
    }
    
    protected Tower createTower(TowerType type, int x, int y) {
        return new Tower(type, x, y);
    }
    
    public boolean addTower(TowerType type) {
        // check if near Tiles have a Tower TODO: Needs to work with variable tower sizes
        
        // FIXME: ArrayIndexOutOfBoundsException if placed in illegal position at bottom or right
        Tile tileRight = parentGrid.getTiles()[x + 1][y];
        Tile tileCorner = parentGrid.getTiles()[x + 1][y + 1];
        Tile tileBottom = parentGrid.getTiles()[x][y + 1];
        
        boolean spaceFree = tower == null && tileRight.isFree() && tileCorner.isFree() && tileBottom.isFree();
        
        if (spaceFree) {
            tower = createTower(type, x, y);
            tileRight.addTower(tower);
            tileCorner.addTower(tower);
            tileBottom.addTower(tower);
            towerRoot = true;
            return true;
        }
        
        return false;
    }
    
    public void addTower(Tower tower) {
        this.tower = tower;
        towerRoot = false;
    }
    
    public void removeTower() {
        // TODO: Needs to work with variable tower sizes
        if (tower != null) {
            if (towerRoot) {
                tower = null;
                parentGrid.getTiles()[x + 1][y].removeTower();
                parentGrid.getTiles()[x + 1][y + 1].removeTower();
                parentGrid.getTiles()[x][y + 1].removeTower();
            }
            towerRoot = false;
        }
    }
    
    public boolean isFree() {
        return tower == null;
    }
    
    @Override
    public boolean isTraversable() {
        return isFree();
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
}
