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
    public int towertypeID;
    public int posX;
    public int posY;
    public BuildTower(int towertypeID, int posX, int posY){
        this.towertypeID = towertypeID;
        this.posX = posX;
        this.posY = posY;
    }
}
