package com.jaregames.futuretd.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:44
 *
 * @author René
 */
class GameWindow {
    private JFrame gameWindow;
    private JPanel gamePanel;
    GameWindow() {
        gamePanel = new JPanel();
        gamePanel.setSize(1000, 800);
        gamePanel.setVisible(true);

        gameWindow = new JFrame("FutureTD");
        gameWindow.setSize(1000, 800);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.add(gamePanel);
        gameWindow.setVisible(true);
    }
}
