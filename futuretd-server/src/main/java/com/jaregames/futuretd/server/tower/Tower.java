package com.jaregames.futuretd.server.tower;


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

    public TowerType getType() {
        return type;
    }
}
