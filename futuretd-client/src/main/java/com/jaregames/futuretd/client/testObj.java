package com.jaregames.futuretd.client;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Created by René on 26.04.2016.
 */
public class TestObj {
    private BufferedImage img;
    
    public TestObj() {
        img = ImageLoader.loadImage("daMalRendern.png");
    }
    
    public void render(Graphics2D g) {
        g.drawImage(img, 200, 300, null);
    }
}
