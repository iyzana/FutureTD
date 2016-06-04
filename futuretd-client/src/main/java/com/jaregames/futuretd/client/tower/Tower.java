package com.jaregames.futuretd.client.tower;


import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.GameWindow.camera;

public class Tower {
    private TowerType type;
    
    private int posX;
    private int posY;
    
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
}
