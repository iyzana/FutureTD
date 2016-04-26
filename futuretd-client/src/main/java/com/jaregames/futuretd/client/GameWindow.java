package com.jaregames.futuretd.client;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:44
 *
 * @author René
 */
class GameWindow {
    private JFrame gameWindow;
    private Canvas canvas;
    private testObj test;
    private GameMap gameMap;

    private boolean running;
    private BufferStrategy bufferStrategy;

    GraphicsDevice myDevice;

    GameWindow() {
        test = new testObj();

        gameMap = new GameMap();

        running = true;

        myDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        canvas = new Canvas();
        canvas.setVisible(true);

        gameWindow = new JFrame("FutureTD");
        gameWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        gameWindow.setUndecorated(true);
        gameWindow.add(canvas);
        canvas.setSize(gameWindow.getSize());

        Window w = gameWindow;



        try {
            myDevice.setFullScreenWindow(w);
        } finally {
            //myDevice.setFullScreenWindow(null);
        }

        gameLoop();
    }



    private void gameLoop(){
        while(running){
            update();
            render();
        }
    }

    private void update(){

    }

    private void render(){
        if (bufferStrategy == null) {
            canvas.createBufferStrategy(2);
            bufferStrategy = canvas.getBufferStrategy();
        }
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();

        g2d.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        g2d.fillRect((int)1, 10, 100, 100);
        test.render(g2d);
        gameMap.render(g2d);

        bufferStrategy.show();
    }
}
