package com.jaregames.futuretd.server.tower;


import java.awt.Graphics2D;

public class Tower {
    protected final TowerType type;
    
    public final int posX;
    public final int posY;
    
    public Tower(TowerType type, int posX, int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }
    
    public void update(double delta) {
        
    }
    
    public void render(Graphics2D g) {
        throw new UnsupportedOperationException("Server mustn't render");
    }
}
