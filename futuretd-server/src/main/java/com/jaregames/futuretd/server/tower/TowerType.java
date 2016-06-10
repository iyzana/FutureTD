package com.jaregames.futuretd.server.tower;

/**
 * Created by René on 03.06.2016.
 */
public enum TowerType {
    DEFAULT("Default", 2);

    String name;
    public int sizeInTiles;
    public int towerTypeID;

    TowerType(String name, int sizeInTiles) {
        this.sizeInTiles = sizeInTiles;
        this.towerTypeID = ordinal();
    }

    public static TowerType getTypeFromID(int id){
        return values()[id];
    }
}
