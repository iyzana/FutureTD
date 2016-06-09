package com.jaregames.futuretd.client.tower;

import com.jaregames.futuretd.client.assets.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * Created by René on 03.06.2016.
 */
public enum TowerType {
    DEFAULT("Default", 2);
    
    String name;
    public int sizeInTiles;
    
    final BufferedImage baseImg;
    BufferedImage turnerImg;
    
    TowerType(String name, int sizeInTiles) {
        baseImg = ImageLoader.loadImage(name + "TowerBase.png");
        this.sizeInTiles = sizeInTiles;
    }
}
