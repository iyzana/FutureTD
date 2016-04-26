package com.jaregames.futuretd.server.pathfinding;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:59
 *
 * @author Jannis
 */
public interface TiledMap {
    Node getNodeAt(int x, int y);
    
    int getWidth();
    
    int getHeight();
}
