package com.jaregames.futuretd.client;

import lombok.Getter;

import java.awt.event.KeyEvent;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 19:06
 *
 * @author René
 */
@Getter
public class Camera {
    private double x;
    private double y;
    
    private int width;
    private int height;
    
    public Camera() {
        x = 0;
        y = 0;
    }
    
    public void update() {
        if (KeyboardInput.keyDown(KeyEvent.VK_D)) {
            x += 6;
        }
        if (KeyboardInput.keyDown(KeyEvent.VK_S)) {
            y += 6;
        }
        if (KeyboardInput.keyDown(KeyEvent.VK_A)) {
            x -= 6;
        }
        if (KeyboardInput.keyDown(KeyEvent.VK_W)) {
            y -= 6;
        }
    }
}
