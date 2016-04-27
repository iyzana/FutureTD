package com.jaregames.futuretd.server.pathfinding;

import lombok.Getter;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:54
 *
 * @author Jannis
 */
@Getter
class SimpleNode implements Node {
    private final int x;
    private final int y;
    
    /**
     * A node representing data neccesarry for a* pathfinding
     *
     * @param x      The nodes x coordinate
     * @param y      The nodes y coordinat
     */
    protected SimpleNode(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        
        Node node = (Node) o;
        
        return x == node.getX() && y == node.getY();
    }
    
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
    
    @Override
    public String toString() {
        return "Node(" + x + ", " + y + ")";
    }
    
    static SimpleNode of(int x, int y) {
        return new SimpleNode(x, y);
    }
    
    static SimpleNode of(Node node) {
        return of(node.getX(), node.getY());
    }
}
