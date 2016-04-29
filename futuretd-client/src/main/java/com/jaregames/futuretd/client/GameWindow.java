package com.jaregames.futuretd.client;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
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
    private GameMap gameMap;
    static Camera camera;
    private KeyboardInput keyboardInput;
    
    private boolean running;
    private BufferStrategy bufferStrategy;
    
    private long lastUpdate;
    private long gameTime;
    
    GameWindow() {
        JFrame gameWindow = new JFrame("FutureTD");
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        gameWindow.setUndecorated(true);
        gameWindow.enableInputMethods(false);
        
        keyboardInput = new KeyboardInput();
        
        canvas = new Canvas();
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.addKeyListener(keyboardInput);
        gameWindow.add(canvas);
        
        GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        screen.setFullScreenWindow(gameWindow);
        
        canvas.requestFocusInWindow();
        
        gameLoop();
        
        gameWindow.dispose();
    }
    
    private void init() {
        gameMap = new GameMap();
        camera = new Camera();
        
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        
        gameTime = System.nanoTime();
        lastUpdate = System.nanoTime();
        running = true;
    }
    
    private void gameLoop() {
        init();
        
        while (running) {
            if (frame()) {
                update();
                render();
            }
            
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void update() {
        double delta = delta();
        System.out.println(1 / delta);
        
        keyboardInput.poll();
        gameMap.update(delta);
        
        if (KeyboardInput.keyDown(KeyEvent.VK_ESCAPE)) running = false;
    }
    
    private double delta() {
        long diff = System.nanoTime() - lastUpdate;
        lastUpdate += diff;
        return diff / 1_000_000_000.0;
    }
    
    private boolean frame() {
        long diff = System.nanoTime() - gameTime;
        boolean frame = diff > 8_000_000;
        if(frame) gameTime += 8_000_000;
        return frame;
    }
    
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.fillRect(-(int) camera.getX(), -(int) camera.getY(), 100, 100);
        gameMap.render(g);
        
        g.dispose();
        bufferStrategy.show();
    }
}
