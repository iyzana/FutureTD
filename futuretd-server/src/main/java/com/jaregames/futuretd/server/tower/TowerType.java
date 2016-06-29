package com.jaregames.futuretd.server.tower;


/**
 * Created by René on 03.06.2016.
 */
public enum TowerType {
    DEFAULT("Default", 2);
    
    public final String name;
    public final int sizeInTiles;
    public final int towerTypeID;
    
    TowerType(String name, int sizeInTiles) {
        this.name = name;
        this.sizeInTiles = sizeInTiles;
        towerTypeID = ordinal();
    }
    
    public static TowerType getTypeFromID(int id) {
        return values()[id];
    }
}

