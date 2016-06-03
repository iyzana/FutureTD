package com.jaregames.futuretd.client.tower;


import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.GameWindow.camera;

public class Tower {

    TowerType type;

    int posX;
    int posY;

    public Tower(TowerType type, int posX, int posY){
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }

    public void update(double delta){

    }

    public void render(Graphics2D g2d){
        g2d.drawImage(type.baseImg, posX-(int)camera.getX(), posY-(int)camera.getY(), null);
    }
}
