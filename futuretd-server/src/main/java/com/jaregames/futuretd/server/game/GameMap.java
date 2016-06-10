package com.jaregames.futuretd.server.game;


import com.jaregames.futuretd.server.FutureTdServer;
import com.jaregames.futuretd.server.game.grid.TileGrid;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 19:06
 *
 * @author René
 */
public class GameMap {
    public final TileGrid grid;
    public static FutureTdServer server;
    
    public GameMap() {
        grid = new TileGrid(500, 300);
    }
    
    public void update(double delta) {
        grid.update(delta);
    }
}
