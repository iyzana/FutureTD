package com.jaregames.futuretd.server.pathfinding;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:54
 *
 * @author Jannis
 */
class PathNode extends Node {
    private PathNode parent;
    private float steps = -1;
    
    private PathNode(int x, int y, PathNode parent) {
        super(x, y);
        this.parent = parent;
    }
    
    float getSteps() {
        if (steps != -1) return steps;
        if (parent == null) return steps = 0;
        float weight = getX() - parent.getX() == 0 || getY() - parent.getY() == 0 ? 1 : 1.1f;
        return steps = parent.getSteps() + weight;
    }
    
    PathNode getParent() {
        return parent;
    }
    
    public void setParent(PathNode parent) {
        this.parent = parent;
    }
    
    static PathNode of(Node node) {
        return new PathNode(node.getX(), node.getY(), null);
    }
    
    static PathNode of(Node node, PathNode parent) {
        return new PathNode(node.getX(), node.getY(), parent);
    }
}
