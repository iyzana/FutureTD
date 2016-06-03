package com.jaregames.futuretd.client;

import com.jaregames.futuretd.client.input.Keyboard;
import com.jaregames.futuretd.client.input.Mouse;
import lombok.extern.log4j.Log4j2;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:44
 *
 * @author René
 */
@Log4j2
public class GameWindow extends Window {
    private boolean running; // If the game is running
    
    private Keyboard keyboard; // Keyboard handler
    private Mouse mouse; // Keyboard handler
    
    private long lastUpdate; // time when the game was last updated
    private long gameTime; // time the game thinks currently is
    
    private double scale;
    
    private GameMap gameMap; // The map
    public static Camera camera; // The camera for maintaining the scrolling position
    
    /**
     * Create a new window, display it and start the gameLoop
     */
    GameWindow() {
        super();
        
        keyboard = new Keyboard(); // Create keyboard input handler
        mouse = new Mouse(); // Create mouse input handler
        
        canvas.addKeyListener(keyboard); // Add keyboard handler to our pane
        canvas.addMouseListener(mouse); // Add mouse click handler
        canvas.addMouseMotionListener(mouse); // Add mouse position handler
        canvas.addMouseWheelListener(mouse); // Add mouse wheel handler
    
        enableFullscreen();
    
        log.info("Window created");
        
        new Thread(() -> {
            gameLoop(); // Run the game
            
            close(); // Close window when game finished
        }, "gameLoop").start();
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
        
        log.info("Initialization complete");
    }
    
    /**
     * Update the state of all active game objects
     */
    private void update() {
        double delta = delta(); // Get the time since the last frame in seconds
        log.trace(1 / delta + " fps"); // Print the current fps
        
        keyboard.poll(); // Read the newest keyboard data
        mouse.poll(); // Read the newest mouse data
        
        if(Keyboard.keyDownOnce(KeyEvent.VK_F11)) {
            toggleFullscreen();
            keyboard.keyReleased(new KeyEvent(canvas, 0, 0, 0, KeyEvent.VK_F11));
        }
        
        gameMap.update(delta);
        
        if (Keyboard.keyDown(KeyEvent.VK_ESCAPE)) running = false; // Quit game on press escape
    }
    
    /**
     * Render all visible game objects
     */
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics(); // Get the graphics to draw with for this cycle
        
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the screen
        g.scale(scale, scale);
        
        // Render scene
        //g.fillRect(-(int) camera.getX(), -(int) camera.getY(), 100, 100);
        gameMap.render(g);
        
        g.dispose(); // Invalidate the graphics for this frame
        bufferStrategy.show(); // Show the rendered frame on the screen
    }
    
    /**
     * Calculates the difference in time since this method was last called.
     *
     * @return difference in seconds
     */
    private double delta() {
        long diff = System.nanoTime() - lastUpdate;
        lastUpdate += diff;
        return diff / 1_000_000_000.0;
    }
    
    /**
     * Determines if a new frame shall be rendered.
     * If the previous frame lasted longer the next frame may come earlier.
     *
     * @return If a new frame shall be rendered
     */
    private boolean frame() {
        long diff = System.nanoTime() - gameTime;
        boolean frame = diff > 7_000_000;
        if (frame) gameTime += 7_000_000;
        return frame;
    }
    
    @Override
    protected void onSizeChanged(int width, int height) {
        scale = Math.min(canvas.getWidth() / 1920.0, canvas.getHeight() / 1080.0);
        mouse.setScale(scale);
        
        log.info("Scale factor " + scale);
    }
}
