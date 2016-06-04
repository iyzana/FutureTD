package com.jaregames.futuretd.client;

import com.jaregames.futuretd.client.helper.ComponentListenerResizeHelper;
import com.jaregames.futuretd.client.helper.MouseListenerEnteredHelper;
import com.jaregames.futuretd.client.helper.WindowClosingListener;
import lombok.extern.log4j.Log4j2;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

/**
 * Project: futuretd
 * <p/>
 * Created on 03.06.2016 at 22:18
 *
 * @author Jannis
 */
@Log4j2
abstract class Window {
    Canvas canvas; // Drawing pane
    BufferStrategy bufferStrategy; // Drawing strategy
    
    private final JFrame gameWindow;
    
    private Dimension screenSize;
    private final GraphicsDevice screen;
    
    private boolean resizeFlag;
    private Dimension previousDimension = new Dimension(-1, -1);
    
    Window() {
        log.info("Creating window");
        
        // Create window with title 'FutureTD'
        gameWindow = new JFrame("FutureTD");
        gameWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    
        gameWindow.addWindowListener((WindowClosingListener) e -> onClose()); // Inform about window close request
        
        canvas = new Canvas(); // Create a drawing pane
        canvas.setFocusTraversalKeysEnabled(false); // Pass 'tab' keystrokes through to our keyboard input handler
        gameWindow.add(canvas); // Put drawing pane in window
        
        canvas.addComponentListener((ComponentListenerResizeHelper) e -> resizeFlag = true);
        canvas.addMouseListener((MouseListenerEnteredHelper) e -> {
            if (resizeFlag) onResize(true);
            resizeFlag = false;
        });
        
        // Get main screen
        screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        GraphicsConfiguration graphicsConfig = screen.getDefaultConfiguration();
        screenSize = graphicsConfig.getBounds().getSize();
        log.info("Screen size: " + screenSize.width + "x" + screenSize.height + "px");
    }
    
    private void onResize(boolean forceFormat) {
        if (canvas.getSize().equals(previousDimension)) return;
        
        if (forceFormat) {
            log.debug("resize");
            
            int width;
            int height;
            
            int dw = (int) (canvas.getWidth() - previousDimension.getWidth());
            int dh = (int) (canvas.getHeight() - previousDimension.getHeight());
            
            if (Math.abs(dw) > Math.abs(dh)) {
                width = canvas.getWidth();
                height = width * 9 / 16;
            } else {
                height = canvas.getHeight();
                width = height * 16 / 9;
            }
            
            canvas.setSize(width, height);
        }
        
        onSizeChanged(canvas.getWidth(), canvas.getHeight());
        
        previousDimension = canvas.getSize();
        
        gameWindow.pack();
    }
    
    abstract void onSizeChanged(int width, int height);
    
    void toggleFullscreen() {
        if (gameWindow.isUndecorated()) disableFullscreen();
        else enableFullscreen();
    }
    
    /**
     * Put this window into fullscreen mode
     */
    void enableFullscreen() {
        log.debug("fullscreen mode");
        
        gameWindow.dispose(); // Temporarily close window for changing its state
        gameWindow.setUndecorated(true); // Remove window title bar
        screen.setFullScreenWindow(gameWindow); // Set application to fullscreen
        
        onResize(false);
        
        canvas.requestFocusInWindow(); // Request keyboard focus
    }
    
    /**
     * Put this window into windowed mode
     */
    void disableFullscreen() {
        log.debug("windowed mode");
        
        gameWindow.dispose(); // Temporarily close window for changing its state
        screen.setFullScreenWindow(null); // Set application to windowed
        gameWindow.setUndecorated(false); // Add window title bar
        
        int width = screenSize.width / 2;
        int height = width * 9 / 16;
        canvas.setSize(width, height); // Set size to half screen
        
        onResize(false);
        
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        
        canvas.requestFocusInWindow(); // Request keyboard focus
    }
    
    void closeWindow() {
        gameWindow.dispose();
    }
    
    abstract void onClose();
}
