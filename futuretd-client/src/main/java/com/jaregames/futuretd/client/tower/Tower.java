package com.jaregames.futuretd.client.tower;


import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.window.GameWindow.camera;

public class Tower {
    private final TowerType type;
    
    private final int posX;
    private final int posY;
    
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
        return posX - (int) camera.getX();
    }
    
    private int renderY() {
        return posY - (int) camera.getY();
    }

    public TowerType getType() {
        return type;
    }
}
