package com.jaregames.futuretd.client;

import com.jaregames.futuretd.client.input.Mouse;
import com.jaregames.futuretd.client.tower.Tower;
import com.jaregames.futuretd.client.tower.TowerType;

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
    
    private Tower tower;
    
    public Tile(double x, double y) {
        this.x = x;
        this.y = y;
        this.tower = null;
    }
    
    public void update(double delta) {
        
    }
    
    public void render(Graphics2D g2d) {
        if (isMouseover()) {
            g2d.setColor(Color.BLUE);
            g2d.drawRect((int) x - (int) camera.getX(), (int) y - (int) camera.getY(), SIZE, SIZE);
        }
        
        if (tower != null) {
            tower.render(g2d);
        }
    }
    
    public void addTower(TowerType type) {
        this.tower = new Tower(type, (int) x, (int) y);
    }
    
    private boolean isMouseover() {
        return (Mouse.getX() + camera.getX() >= x && Mouse.getX() + camera.getX() < x + Tile.SIZE && Mouse.getY() + camera.getY() >= y && Mouse.getY() + camera.getY() < y + Tile.SIZE);
    }
}
