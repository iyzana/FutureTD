package com.jaregames.futuretd.client.game.grid;


import com.jaregames.futuretd.client.input.Mouse;
import com.jaregames.futuretd.client.tower.Tower;
import com.jaregames.futuretd.client.tower.TowerType;
import com.jaregames.futuretd.client.window.GameWindow;
import com.jaregames.futuretd.server.communication.BuildTower;

import java.awt.Color;
import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.window.GameWindow.camera;

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
        if (isMouseover() && Mouse.isDownOnce()) {
            addTower(TowerType.DEFAULT);
        }

        if (isMouseover() && Mouse.isRightDownOnce()) {
            removeTower();
        }
    }
    
    public void render(Graphics2D g) {
        if (isMouseover()) {
            g.setColor(Color.BLUE);

            g.drawRect(renderX(), renderY(), SIZE * 2, SIZE * 2);
        }
        
        if (tower != null) {
            tower.render(g);
        }
    }
    
    public void addTower(TowerType type) {
        //check if near Tiles have a Tower TODO: Muss mit variabelen Towergrößen funktionieren!
//        boolean spaceFree = true;
//        spaceFree = !parentGrid.getTiles()[x+1][y].hasTower() && !parentGrid.getTiles()[x+1][y+1].hasTower() && !parentGrid.getTiles()[x][y+1].hasTower();
//
//        if(this.tower==null && spaceFree){
//            this.tower = new Tower(type, x * Tile.SIZE, y * Tile.SIZE);
//            parentGrid.getTiles()[x+1][y].addTower(tower);
//            parentGrid.getTiles()[x+1][y+1].addTower(tower);
//            parentGrid.getTiles()[x][y+1].addTower(tower);
//            towerRoot = true;
//        }

        GameWindow.client.send(new BuildTower(TowerType.DEFAULT.towerTypeID, this.x, this.y));

    }

    public void serverAddTower(TowerType type){
        tower = new Tower(type, x* Tile.SIZE, y* Tile.SIZE);
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
    
    private int renderX() {
        return x * Tile.SIZE - (int) camera.getX();
    }
    
    private int renderY() {
        return y * Tile.SIZE - (int) camera.getY();
    }
    
    public boolean isMouseover() {
        double x = renderX();
        double y = renderY();
        
        return Mouse.getX() >= x && Mouse.getX() < x + Tile.SIZE && Mouse.getY() >= y && Mouse.getY() < y + Tile.SIZE;
    }

    public boolean hasTower() {
        return tower != null;
    }
}
