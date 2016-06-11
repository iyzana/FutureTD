package com.jaregames.futuretd.client.tower;


import com.jaregames.futuretd.client.game.grid.Tile;

import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.window.GameWindow.camera;

public class Tower {
    public final TowerType type;
    
    public final int posX;
    public final int posY;
    
    public Tower(TowerType type, int posX, int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }
    
    public void update(double delta) {
        
    }
    
    public void render(Graphics2D g2d) {
        g2d.drawImage(type.baseImg, renderX(), renderY(), null);
    }
    
    private int renderX() {
        return posX * Tile.SIZE - (int) camera.getX();
    }
    
    private int renderY() {
        return posY * Tile.SIZE - (int) camera.getY();
    }
}
