package com.jaregames.futuretd.client.tower;

import com.jaregames.futuretd.client.assets.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * Created by René on 03.06.2016.
 */
public enum TowerType {
    DEFAULT("Default");
    
    String name;
    
    final BufferedImage baseImg;
    BufferedImage turnerImg;
    
    TowerType(String name) {
        baseImg = ImageLoader.loadImage(name + "TowerBase.png");
    }
}
