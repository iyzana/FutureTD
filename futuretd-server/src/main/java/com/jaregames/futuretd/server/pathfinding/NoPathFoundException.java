package com.jaregames.futuretd.server.pathfinding;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:46
 * 
 * For a given map a path from a to b cannot be found
 * or cannot be found in a reasonable amount of time
 *
 * @author Jannis
 */
public class NoPathFoundException extends RuntimeException {
    public NoPathFoundException() {
    }
    
    public NoPathFoundException(String message) {
        super(message);
    }
    
    public NoPathFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
