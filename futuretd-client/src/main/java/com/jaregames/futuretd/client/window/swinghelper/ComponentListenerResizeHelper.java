package com.jaregames.futuretd.client.window.swinghelper;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Project: futuretd
 * <p/>
 * Created on 03.06.2016 at 22:17
 *
 * @author Jannis
 */
public interface ComponentListenerResizeHelper extends ComponentListener {
    @Override
    default void componentMoved(ComponentEvent e) {
    }
    
    @Override
    default void componentShown(ComponentEvent e) {
    }
    
    @Override
    default void componentHidden(ComponentEvent e) {
    }
}
