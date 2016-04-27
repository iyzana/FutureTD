package com.jaregames.futuretd.server.pathfinding;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:59
 *
 * @author Jannis
 */
public interface TiledMap {
    /**
     * Returns the node at the given location.
     * This method should not do calculations as it is heavily used
     * during pathfinding and its performance is therefore critical.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The Node at the given location
     */
    TraversableNode getNodeAt(int x, int y);
    
    /**
     * @return The rightmost node x coordinate available on this map
     */
    int getWidth();
    
    /**
     * @return The bottommost node y coordinate available on this map
     */
    int getHeight();
}
