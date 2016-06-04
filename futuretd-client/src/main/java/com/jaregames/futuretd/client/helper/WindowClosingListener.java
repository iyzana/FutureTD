package com.jaregames.futuretd.client.helper;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Project: futuretd
 * <p/>
 * Created on 04.06.2016 at 20:52
 *
 * @author Jannis
 */
public interface WindowClosingListener extends WindowListener {
    @Override
    default void windowOpened(WindowEvent e) {
    }
    
    @Override
    default void windowClosed(WindowEvent e) {
    }
    
    @Override
    default void windowIconified(WindowEvent e) {
    }
    
    @Override
    default void windowDeiconified(WindowEvent e) {
    }
    
    @Override
    default void windowActivated(WindowEvent e) {
    }
    
    @Override
    default void windowDeactivated(WindowEvent e) {
    }
}
