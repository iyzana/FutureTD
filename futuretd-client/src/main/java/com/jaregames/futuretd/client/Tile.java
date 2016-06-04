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
    
    // TODO: Think about saving grid pos instead of render pos
    private double x;
    private double y;
    
    // TODO: Tower takes 4 tiles of space
    private Tower tower;
    
    public Tile(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void update(double delta) {
        
    }
    
    public void render(Graphics2D g) {
        if (isMouseover()) {
            g.setColor(Color.BLUE);
            g.drawRect(renderX(), renderY(), SIZE, SIZE);
        }
        
        if (tower != null) {
            tower.render(g);
        }
    }
    
    public void addTower(TowerType type) {
        this.tower = new Tower(type, (int) x, (int) y);
    }
    
    private int renderX() {
        return (int) x - (int) camera.getX();
    }
    
    private int renderY() {
        return (int) y - (int) camera.getY();
    }
    
    private boolean isMouseover() {
        double x = renderX();
        double y = renderY();
        
        return Mouse.getX() >= x && Mouse.getX() < x + Tile.SIZE && Mouse.getY() >= y && Mouse.getY() < y + Tile.SIZE;
    }
}
