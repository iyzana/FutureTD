package com.jaregames.futuretd.server.pathfinding;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 22:46
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
