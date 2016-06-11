package com.jaregames.futuretd.server;

import com.jaregames.futuretd.server.communication.BuildTower;
import com.jaregames.futuretd.server.game.GameMap;
import com.jaregames.futuretd.server.game.grid.Tile;
import com.jaregames.futuretd.server.tower.TowerType;
import lombok.extern.log4j.Log4j2;

import java.util.Queue;

/**
 * Created by René on 10.06.2016.
 */
@Log4j2
public class FutureTDServerManager {
    private GameMap map;
    private Queue inputQueue;
    private FutureTdServer server;
    
    private static final int prefFps = 64;
    private static final int minFrameTime = 1000000000 / prefFps;
    private long gameTime; // time the game thinks currently is
    
    private long lastUpdate; // time when the game was last updated
    private boolean running;
    
    private FutureTDServerManager() {
        log.info("Waiting for client...");
        establishConnection();
        gameLoop();
    }
    
    public static void main(String[] args) {
        new FutureTDServerManager();
    }
    
    private void update() {
        double delta = delta(); // Get the time since the last frame in seconds
        
        map.update(delta);
        
        if (!server.isSessionEnded()) {
            checkInputQueue();
        }
    }
    
    private double delta() {
        long diff = System.nanoTime() - lastUpdate;
        lastUpdate += diff;
        return diff / 1_000_000_000.0;
    }
    
    private void gameLoop() {
        init();
        while (running) {
            if (frame()) {
                update();
            }
            
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void init() {
        log.info("Initializing game");
        
        GameMap.server = server;
        map = new GameMap();
        
        // Set initial values for constant fps
        gameTime = System.nanoTime();
        lastUpdate = System.nanoTime();
        running = true;
        
        log.info("Starting game");
    }
    
    private void establishConnection() {
        server = new FutureTdServer();
        
        while (!server.isClientConnected()) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        inputQueue = server.getInputQueue();
    }
    
    private boolean frame() {
        long diff = System.nanoTime() - gameTime;
        if (diff > 4 * minFrameTime) gameTime += diff - 4 * minFrameTime;
        if (diff > 16 * minFrameTime) log.warn(diff / minFrameTime + " frames behind");
        boolean frame = diff > minFrameTime;
        if (frame) gameTime += minFrameTime;
        return frame;
    }
    
    private void checkInputQueue() {
        Object o = inputQueue.poll();
        
        if (o == null) {
            return;
        }
        
        if (o instanceof BuildTower) {
            BuildTower buildTower = (BuildTower) o;
            handleBuildTower(buildTower);
        }
    }
    
    private void handleBuildTower(BuildTower tower) {
        log.info("Tower must be build!");
        TowerType type = TowerType.getTypeFromID(tower.towerTypeID);
        Tile tile = map.grid.getTileAt(tower.posX, tower.posY);
        tile.addTower(type);
    }
}
