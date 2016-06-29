package com.jaregames.futuretd.server.pathfinding;

import lombok.Getter;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:54
 * <p/>
 * Implementation of Node additionally providing a parent and an abstract distance
 *
 * @author Jannis
 */
@Getter
class PathNode extends SimpleNode {
    private final PathNode parent;
    private float steps = -1;
    
    /**
     * A node representing data necessary for a* pathfinding
     *
     * @param x      The nodes x coordinate
     * @param y      The nodes y coordinate
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
        if (steps >= 0) return steps;
        if (parent == null) return steps = 0;
        float weight = getX() - parent.getX() == 0 || getY() - parent.getY() == 0 ? 1 : 1.1f;
        return steps = parent.getSteps() + weight;
    }
    
    static PathNode of(int x, int y) {
        return of(x, y, null);
    }
    
    static PathNode of(int x, int y, PathNode parent) {
        return new PathNode(x, y, parent);
    }
    
    static PathNode of(Node node) {
        return of(node, null);
    }
    
    static PathNode of(Node node, PathNode parent) {
        return of(node.getX(), node.getY(), parent);
    }
}
