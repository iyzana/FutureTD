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
        //check if near Tiles have a Tower TODO: Muss mit variabelen Towergrößen funktionieren!
        boolean spaceFree = true;
        spaceFree = !parentGrid.getTiles()[x+1][y].hasTower() && !parentGrid.getTiles()[x+1][y+1].hasTower() && !parentGrid.getTiles()[x][y+1].hasTower();

        if(this.tower==null && spaceFree){
            this.tower = new Tower(type, x * Tile.SIZE, y * Tile.SIZE);
            parentGrid.getTiles()[x+1][y].addTower(tower);
            parentGrid.getTiles()[x+1][y+1].addTower(tower);
            parentGrid.getTiles()[x][y+1].addTower(tower);
            towerRoot = true;
            GameMap.server.send(new BuildTower(tower.getType().towerTypeID, x, y));
        }

    }

    public void addTower(Tower tower) {
        this.tower = tower;
        towerRoot = false;
    }

    public void removeTower() {
        //TODO: Muss mit variabelen Towergrößen funktionieren!
        if(this.tower!=null){
            if(towerRoot){
                this.tower = null;
                parentGrid.getTiles()[x+1][y].removeTower();
                parentGrid.getTiles()[x+1][y+1].removeTower();
                parentGrid.getTiles()[x][y+1].removeTower();
            }
            towerRoot = false;
        }
    }


    public boolean hasTower(){
        return tower!=null;
    }
}
