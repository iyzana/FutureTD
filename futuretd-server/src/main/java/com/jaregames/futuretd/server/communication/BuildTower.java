package com.jaregames.futuretd.server.communication;

import java.io.Serializable;

/**
 * Project: futuretd
 * <p/>
 * Created on 29.04.2016 at 21:28
 *
 * @author René
 */
public class BuildTower implements Serializable{
    private static final long serialVersionUID = 1;
    
    public final int towerTypeID;
    public final int posX;
    public final int posY;
    
    public BuildTower(int towerTypeID, int posX, int posY){
        this.towerTypeID = towerTypeID;
        this.posX = posX;
        this.posY = posY;
    }
}
