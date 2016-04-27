package com.jaregames.futuretd.client;

import java.awt.event.KeyEvent;

/**
 * Created by René on 26.04.2016.
 */
public class Camera {
    double x;
    double y;


    int width;
    int height;

    public Camera() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public void update() {
        if(KeyboardInput.keyDown(KeyEvent.VK_D)){
            x += 6;
        }
        if(KeyboardInput.keyDown(KeyEvent.VK_S)){
            y += 6;
        }
        if(KeyboardInput.keyDown(KeyEvent.VK_A)){
            x-=6;
        }
        if(KeyboardInput.keyDown(KeyEvent.VK_W)){
            y-=6;
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
