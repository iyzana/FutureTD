package com.jaregames.futuretd.client.game;

import com.jaregames.futuretd.client.input.Keyboard;
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
    
    void update(double delta) {
        if (Keyboard.keyDown(KeyEvent.VK_D)) {
            x += 600 * delta;
        }
        if (Keyboard.keyDown(KeyEvent.VK_S)) {
            y += 600 * delta;
        }
        if (Keyboard.keyDown(KeyEvent.VK_A)) {
            x -= 600 * delta;
        }
        if (Keyboard.keyDown(KeyEvent.VK_W)) {
            y -= 600 * delta;
        }
    }
}
