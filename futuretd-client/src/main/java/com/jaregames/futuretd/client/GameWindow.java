package com.jaregames.futuretd.client;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
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
    private Canvas canvas; // Drawing pane
    private BufferStrategy bufferStrategy; // Drawing strategy
    private boolean running; // If the game is running
    
    private KeyboardInput keyboardInput; // Keyboard handler
    
    private long lastUpdate; // time when the game was last updated
    private long gameTime; // time the game thinks currently is
    
    private GameMap gameMap; // The map
    static Camera camera; // The camera for maintaining the scrolling position
    
    /**
     * Creates a new window, displays it and starts the gameLoop
     */
    GameWindow() {
        JFrame gameWindow = new JFrame("FutureTD"); // Create window with title 'FutureTD'
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Exit application on window close
        gameWindow.setUndecorated(true); // Remove window title bar
        
        keyboardInput = new KeyboardInput(); // Create keyboard input handler
        
        canvas = new Canvas(); // Create a drawing pane
        canvas.setFocusTraversalKeysEnabled(false); // Pass 'tab' keystrokes through to our keyboard
        canvas.addKeyListener(keyboardInput); // Add keyboard handler to our pane
        gameWindow.add(canvas); // Put drawing pane in window
        
        GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); // Get main screen
        screen.setFullScreenWindow(gameWindow); // Set application to fullscreen
        
        canvas.requestFocusInWindow(); // Request keyboard focus
        
        gameLoop(); // Run the game
        
        gameWindow.dispose(); // Dispose window if game finished
    }
    
    /**
     * Initialize the game and update and render it while running
     */
    private void gameLoop() {
        init();
        
        while (running) {
            if (frame()) {
                update();
                render();
            }
            
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
     /**
     * Initialize objects important for the game here
     */
    private void init() {
        gameMap = new GameMap();
        camera = new Camera();
    
        // Set rendering technique to double buffered
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    
        // Set initial values for constant fps
        gameTime = System.nanoTime();
        lastUpdate = System.nanoTime();
        running = true;
    }
    
    /**
     * Update the state of all active game objects
     */
    private void update() {
        double delta = delta(); // Get the time since the last frame in seconds
        System.out.println(1 / delta); // Print the current fps
        
        keyboardInput.poll(); // Read the newest keyboard data
        gameMap.update(delta);
        
        if (KeyboardInput.keyDown(KeyEvent.VK_ESCAPE)) running = false; // Quit game on press escape
    }
    
    /**
     * Render all visible game objects
     */
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics(); // Get the graphics to draw with this cycle
        
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the screen
        
        // Render scene
        g.fillRect(-(int) camera.getX(), -(int) camera.getY(), 100, 100);
        gameMap.render(g);
        
        g.dispose(); // Invalidate the graphics for this frame
        bufferStrategy.show(); // Show the rendered frame on the screen
    }
    
    /**
     * Calculates the difference in time since this method was last called
     * @return difference in time since last call in seconds
     */
    private double delta() {
        long diff = System.nanoTime() - lastUpdate;
        lastUpdate += diff;
        return diff / 1_000_000_000.0;
    }
    
    /**
     * Determines if a new frame shall be rendered
     * If the previous frame lasted longer the next frame may come earlier
     * 
     * @return If a new frame shall be rendered
     */
    private boolean frame() {
        long diff = System.nanoTime() - gameTime;
        boolean frame = diff > 7_000_000;
        if(frame) gameTime += 7_000_000;
        return frame;
    }
}
