package com.jaregames.futuretd.client;

import java.awt.Color;
import java.awt.Graphics2D;

import static com.jaregames.futuretd.client.GameWindow.camera;

/**
 * Created by René on 03.06.2016.
 */
public class Tile {
    final static int SIZE = 50;
    private double x;
    private double y;

    public Tile(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(double delta){

    }

    public void render(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        g2d.drawRect((int)(x - camera.getX()), (int)(y - camera.getY()), SIZE, SIZE);
    }
}
