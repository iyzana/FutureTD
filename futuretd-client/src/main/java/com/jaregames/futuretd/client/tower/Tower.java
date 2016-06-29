package com.jaregames.futuretd.client.tower;


import com.jaregames.futuretd.client.assets.ImageLoader;
import com.jaregames.futuretd.client.game.grid.Tile;
import com.jaregames.futuretd.server.tower.TowerType;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static com.jaregames.futuretd.client.window.GameWindow.camera;

public class Tower extends com.jaregames.futuretd.server.tower.Tower {
    private BufferedImage image;
    
    public Tower(TowerType type, int posX, int posY) {
        super(type, posX,posY);
    
        image = ImageLoader.loadImage(type.name + "TowerBase.png");
    }
    
    public void update(double delta) {
        
    }
    
    public void render(Graphics2D g2d) {
        g2d.drawImage(image, renderX(), renderY(), null);
    }
    
    private int renderX() {
        return posX * Tile.SIZE - (int) camera.getX();
    }
    
    private int renderY() {
        return posY * Tile.SIZE - (int) camera.getY();
    }
}
