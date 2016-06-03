package com.jaregames.futuretd.client.input;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Project: futuretd
 * <p/>
 * Created on 10.05.2016 at 22:33
 * <p/>
 * Accessing the current mouse position and data
 *
 * @author Jannis
 */
public class Mouse extends MouseAdapter {
    private static final int BUTTON_COUNT = 32;
    
    private enum ButtonState {
        // Not down, but not for the first time
        UP,
        // Not down for the first time
        RELEASED,
        // Down, but not the first time
        DOWN,
        // Down for the first time
        PRESSED
    }
    
    // Current state of the mouse
    private boolean[] currentButtons;
    private Point currentPosition;
    private int currentRotation;
    private boolean currentAvailable;
    
    // Polled mouse state
    private static ButtonState[] buttons = new ButtonState[BUTTON_COUNT];
    private static Point position = new Point();
    private static int rotation;
    private static boolean available;
    private static double scale = 1;
    
    public Mouse() {
        currentButtons = new boolean[BUTTON_COUNT];
        currentPosition = new Point();
        currentRotation = 0;
        
        for (int i = 0; i < BUTTON_COUNT; ++i) {
            buttons[i] = ButtonState.UP;
        }
    }
    
    /**
     * Write the current state of the mouse to the polled mouse data
     * <p>
     * This method synchronizes the input with our game ticks
     */
    public synchronized void poll() {
        for (int i = 0; i < BUTTON_COUNT; ++i) {
            // Set the mouse state
            if (currentButtons[i]) {
                buttons[i] = isUp(i) ? ButtonState.PRESSED : ButtonState.DOWN;
            } else {
                buttons[i] = isDown(i) ? ButtonState.RELEASED : ButtonState.UP;
            }
        }
        
        position.x = (int) (currentPosition.x / scale);
        position.y = (int) (currentPosition.y / scale);
        
        rotation = currentRotation;
        available = currentAvailable;
    }
    
    public void setScale(double scale) {
        Mouse.scale = scale;
    }
    
    // @formatter:off
    public static boolean isDown() {
        return isDown(MouseEvent.BUTTON1);
    }
    public static boolean isRightDown() {
        return isDown(MouseEvent.BUTTON3);
    }
    public static boolean isDown(int button) {
        return buttons[button] == ButtonState.DOWN || buttons[1] == ButtonState.PRESSED;
    }
    
    public static boolean isDownOnce() {
        return isDownOnce(MouseEvent.BUTTON1);
    }
    public static boolean isRightDownOnce() {
        return isDownOnce(MouseEvent.BUTTON3);
    }
    public static boolean isDownOnce(int button) {
        return buttons[button] == ButtonState.PRESSED;
    }
    
    public static boolean isUp() {
        return isUp(MouseEvent.BUTTON1);
    }
    public static boolean isRightUp() {
        return isUp(MouseEvent.BUTTON3);
    }
    public static boolean isUp(int button) {
        return buttons[button] == ButtonState.UP || buttons[1] == ButtonState.RELEASED;
    }
    
    public static boolean isUpOnce() {
        return isUpOnce(MouseEvent.BUTTON1);
    }
    public static boolean isRightUpOnce() {
        return isUpOnce(MouseEvent.BUTTON3);
    }
    public static boolean isUpOnce(int button) {
        return buttons[button] == ButtonState.RELEASED;
    }
    
    public static Point getPos() {
        return position;
    }
    public static int getX() {
        return position.x;
    }
    public static int getY() {
        return position.y;
    }
    
    public static int getRotation() {
        return rotation;
    }
    
    public static boolean isAvailable() {
         return available;
    }
    // @formatter:on
    
    // === Listener methods ===
    
    @Override
    public synchronized void mousePressed(MouseEvent e) {
        currentButtons[e.getButton()] = true;
    }
    
    @Override
    public synchronized void mouseReleased(MouseEvent e) {
        currentButtons[e.getButton()] = false;
    }
    
    @Override
    public synchronized void mouseEntered(MouseEvent e) {
        currentAvailable = true;
    }
    
    @Override
    public synchronized void mouseExited(MouseEvent e) {
        currentAvailable = false;
    }
    
    @Override
    public synchronized void mouseDragged(MouseEvent e) {
        currentPosition = e.getPoint();
    }
    
    @Override
    public synchronized void mouseMoved(MouseEvent e) {
        currentPosition = e.getPoint();
    }
    
    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent e) {
        currentRotation += e.getWheelRotation();
    }
}
