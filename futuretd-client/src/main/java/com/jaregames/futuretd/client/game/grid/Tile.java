package com.jaregames.futuretd.client.game.grid;


import com.jaregames.futuretd.client.input.Mouse;
import com.jaregames.futuretd.client.tower.Tower;
import com.jaregames.futuretd.client.window.GameWindow;
import com.jaregames.futuretd.server.communication.BuildTower;
import com.jaregames.futuretd.server.tower.TowerType;

import java.awt.Color;
import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.window.GameWindow.camera;

/**
 * Created by René on 03.06.2016.
 */
public class Tile extends com.jaregames.futuretd.server.game.grid.Tile {
    public final static int SIZE = 25;
    
    public Tile(int x, int y, TileGrid parentGrid) {
        super(x, y, parentGrid);
    }
    
    public void update(double delta) {
        if (isMouseover() && Mouse.isDownOnce()) {
            requestTower(TowerType.DEFAULT);
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
    
    @Override
    protected Tower createTower(TowerType type, int x, int y) {
        return new Tower(type, x, y);
    }
    
    public void requestTower(TowerType type) {
        GameWindow.client.send(new BuildTower(type.towerTypeID, x, y));
    }
    
    public void requestRemoveTower(TowerType type) {
        // TODO: Implement
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
}
