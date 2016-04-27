package com.jaregames.futuretd.client;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:44
 *
 * @author René
 */
class GameWindow {
    private Canvas canvas;
    private TestObj test;
    private GameMap gameMap;
    
    private boolean running;
    private BufferStrategy bufferStrategy;
    
    GameWindow() {
        JFrame gameWindow = new JFrame("FutureTD");
        
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        canvas = new Canvas();
        canvas.setFocusTraversalKeysEnabled(false);
        
        gameWindow.add(canvas);
        gameWindow.requestFocus();
        
        gameWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        gameWindow.setUndecorated(true);
        gameWindow.enableInputMethods(false);
        
        GraphicsDevice myDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        myDevice.setFullScreenWindow(gameWindow);
        
        gameLoop();
    }
    
    private void init() {
        test = new TestObj();
        gameMap = new GameMap();
        
        running = true;
    }
    
    private void gameLoop() {
        init();
        
        while (running) {
            update();
            render();
        }
    }
    
    private void update() {
        
    }
    
    private void render() {
        if (bufferStrategy == null) {
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
        }
        
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
    
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.fillRect(1, 10, 100, 100);
        test.render(g);
        gameMap.render(g);
    
        g.dispose();
        bufferStrategy.show();
    }
}
