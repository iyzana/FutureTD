package com.jaregames.futuretd.client;

import com.jaregames.futuretd.client.helper.ComponentListenerResizeHelper;
import com.jaregames.futuretd.client.helper.MouseListenerEnteredHelper;
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
class Window {
    Canvas canvas; // Drawing pane
    BufferStrategy bufferStrategy; // Drawing strategy
    
    private final JFrame gameWindow;
    
    private Dimension screenSize;
    private final GraphicsDevice screen;
    
    private boolean resizeFlag;
    private Dimension previousDimension;
    
    Window() {
        log.info("Application start");
        
        // Create window with title 'FutureTD'
        gameWindow = new JFrame("FutureTD");
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Exit application on window close
        
        canvas = new Canvas(); // Create a drawing pane
        canvas.setFocusTraversalKeysEnabled(false); // Pass 'tab' keystrokes through to our keyboard input handler
        gameWindow.add(canvas); // Put drawing pane in window
        
        canvas.addComponentListener((ComponentListenerResizeHelper) e -> resizeFlag = true);
        canvas.addMouseListener((MouseListenerEnteredHelper) e -> {
            if (resizeFlag) onResize();
            resizeFlag = false;
        });
        
        // Get main screen
        screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        GraphicsConfiguration graphicsConfig = screen.getDefaultConfiguration();
        screenSize = graphicsConfig.getBounds().getSize();
        log.info("Screen size: " + screenSize.width + "x" + screenSize.height + "px");
    }
    
    private void onResize() {
        if (canvas.getSize().equals(previousDimension)) return;
        
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
        onSizeChanged(width, height);
    
        previousDimension = canvas.getSize();
        
        gameWindow.pack();
    }
    
    void onSizeChanged(int width, int height) {
        
    }
    
    void toggleFullscreen() {
        if (gameWindow.isUndecorated()) disableFullscreen();
        else enableFullscreen();
    }
    
    void enableFullscreen() {
        gameWindow.dispose();
        gameWindow.setUndecorated(true); // Remove window title bar
        screen.setFullScreenWindow(gameWindow); // Set application to fullscreen
        
        previousDimension = canvas.getSize();
        onSizeChanged(canvas.getWidth(), canvas.getHeight());
        gameWindow.setVisible(true);
    
        canvas.requestFocusInWindow(); // Request keyboard focus
    }
    
    void disableFullscreen() {
        gameWindow.dispose();
        screen.setFullScreenWindow(null);
        gameWindow.setUndecorated(false);
        
        int width = screenSize.width;
        int height = screenSize.height / 2;
        canvas.setSize(width, height);
        
        onResize();
        
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    
        canvas.requestFocusInWindow(); // Request keyboard focus
    }
    
    void close() {
        gameWindow.dispose();
    }
}
