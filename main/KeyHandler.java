package Tetris.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    /*
     * Booleans representing which key is pressed right now
     */
    public boolean leftPressed, rightPressed;
    public boolean upPressed, downPressed;
    public boolean rotateClockwisePressed, rotateCounterClockwisePressed;

    /*
     * Change the key-bindings here
     */
    private static int leftKey = KeyEvent.VK_LEFT;
    private static int rightKey = KeyEvent.VK_RIGHT;

    private static int upKey = KeyEvent.VK_UP;
    private static int downKey = KeyEvent.VK_DOWN;

    private static int rotateClockwise = KeyEvent.VK_E;
    private static int rotateCounterClockwise = KeyEvent.VK_Q;

    @Override
    public void keyTyped(KeyEvent e) {
        // It'll barely be used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // The key is pressed by the user
        int code = e.getKeyCode();

        if(code == leftKey) leftPressed = true;
        if(code == rightKey) rightPressed = true;
        if(code == upKey) upPressed = true;
        if(code == downKey) downPressed = true;
        if(code == rotateClockwise) rotateClockwisePressed = true;
        if(code == rotateCounterClockwise) rotateCounterClockwisePressed = true;   
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // User released the key
        int code = e.getKeyCode();

        if(code == leftKey) leftPressed = false;
        if(code == rightKey) rightPressed = false;
        if(code == upKey) upPressed = false;
        if(code == downKey) downPressed = false;
        if(code == rotateClockwise) rotateClockwisePressed = false;
        if(code == rotateCounterClockwise) rotateCounterClockwisePressed = false;
    }
    
}
