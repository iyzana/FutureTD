package com.jaregames.futuretd.server.pathfinding;

import lombok.Getter;
import lombok.Setter;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:54
 *
 * @author Jannis
 */
class PathNode extends Node {
    @Getter
    @Setter
    private PathNode parent;
    private float steps = -1;
    
    /**
     * A node representing data neccesarry for a* pathfinding
     * 
     * @param x The nodes x coordinate
     * @param y The nodes y coordinat
     * @param parent The nodes parent or predecessor
     */
    private PathNode(int x, int y, PathNode parent) {
        super(x, y);
        this.parent = parent;
    }
    
    /**
     * @return The steps needed to get to this node from the starting point
     */
    float getSteps() {
        if (steps < 0) return steps;
        if (parent == null) return steps = 0;
        float weight = getX() - parent.getX() == 0 || getY() - parent.getY() == 0 ? 1 : 1.1f;
        return steps = parent.getSteps() + weight;
    }
    
    static PathNode of(Node node) {
        return new PathNode(node.getX(), node.getY(), null);
    }
    
    static PathNode of(Node node, PathNode parent) {
        return new PathNode(node.getX(), node.getY(), parent);
    }
}
