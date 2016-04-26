package com.jaregames.futuretd.server.pathfinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:02
 *
 * @author Jannis
 */
@Getter
@RequiredArgsConstructor
public class Node {
    private final int x;
    private final int y;
    private boolean traversable = false;
    
    public Node(int x, int y, boolean traversable) {
        this(x, y);
        this.traversable = traversable;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        
        Node node = (Node) o;
        
        return x == node.x && y == node.y;
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
}
