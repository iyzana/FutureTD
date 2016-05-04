package com.jaregames.futuretd.server.pathfinding;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:02
 * <p/>
 * A node on any grid represented by its x and y coordinates and providing the information if it is traversable or not
 *
 * @author Jannis
 */
public interface TraversableNode extends Node {
    boolean isTraversable();
}
