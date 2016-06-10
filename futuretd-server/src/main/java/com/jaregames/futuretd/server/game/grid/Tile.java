package com.jaregames.futuretd.server.game.grid;


import com.jaregames.futuretd.server.communication.BuildTower;
import com.jaregames.futuretd.server.game.GameMap;
import com.jaregames.futuretd.server.tower.Tower;
import com.jaregames.futuretd.server.tower.TowerType;

/**
 * Created by René on 03.06.2016.
 */
public class Tile {
    final static int SIZE = 25;
    
    private final int x;
    private final int y;
    
    private Tower tower;
    private TileGrid parentGrid;
    private boolean towerRoot;// if the tile ist the root tile for a tower
    
    public Tile(int x, int y, TileGrid parentGrid) {
        this.x = x;
        this.y = y;
        this.parentGrid = parentGrid;
        towerRoot = false;
    }
    
    public void update(double delta) {
        
    }
    
    
    public void addTower(TowerType type) {
        // check if near Tiles have a Tower TODO: Muss mit variabelen Towergrößen funktionieren!
    
        Tile tileRight = parentGrid.getTiles()[x + 1][y];
        Tile tileCorner = parentGrid.getTiles()[x + 1][y + 1];
        Tile tileBottom = parentGrid.getTiles()[x][y + 1];
        
        boolean spaceFree = tower == null && !tileRight.hasTower() && !tileCorner.hasTower() && !tileBottom.hasTower();
        
        if (spaceFree) {
            tower = new Tower(type, x * Tile.SIZE, y * Tile.SIZE);
            tileRight.addTower(tower);
            tileCorner.addTower(tower);
            tileBottom.addTower(tower);
            towerRoot = true;
            
            GameMap.server.send(new BuildTower(tower.type.towerTypeID, x, y));
        }
    }
    
    public void addTower(Tower tower) {
        this.tower = tower;
        towerRoot = false;
    }
    
    public void removeTower() {
        //TODO: Muss mit variabelen Towergrößen funktionieren!
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
    
    public boolean hasTower() {
        return tower != null;
    }
}
