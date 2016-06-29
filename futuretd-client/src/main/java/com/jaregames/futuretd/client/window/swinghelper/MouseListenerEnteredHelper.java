package com.jaregames.futuretd.client.window.swinghelper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Project: futuretd
 * <p/>
 * Created on 03.06.2016 at 22:46
 *
 * @author Jannis
 */
public interface MouseListenerEnteredHelper extends MouseListener {
    
    @Override
    default void mouseClicked(MouseEvent e) {
    }
    
    @Override
    default void mousePressed(MouseEvent e) {
    }
    
    @Override
    default void mouseReleased(MouseEvent e) {
    }
    
    //    @Override
    //    default void mouseEntered(MouseEvent e) {
    //    }
    
    @Override
    default void mouseExited(MouseEvent e) {
    }
}
