package com.jaregames.futuretd.client.tower;

import com.jaregames.futuretd.client.assets.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * Created by René on 03.06.2016.
 */
public enum TowerType {
    DEFAULT("Default", 2);
    
    public final String name;
    public final BufferedImage baseImg;
    public final int sizeInTiles;
    
    //    BufferedImage turnerImg;
    
    TowerType(String name, int sizeInTiles) {
        this.name = name;
        baseImg = ImageLoader.loadImage(name + "TowerBase.png");
        this.sizeInTiles = sizeInTiles;
    }
}
