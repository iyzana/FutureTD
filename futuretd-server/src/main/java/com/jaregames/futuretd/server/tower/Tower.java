package com.jaregames.futuretd.server.tower;


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
}
