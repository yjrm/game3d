package javagame.game3d.src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javagame.game3d.src.math.Camera;
import javagame.game3d.src.math.Coordinate;
import javagame.game3d.src.math.Triangle;

public class KeyBoardInput implements KeyListener {

    boolean forward, backward, left, right, up, down;

    public void keyTyped(KeyEvent e) {

        if(e.getKeyChar() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'w') {
            forward = true;
        }
        if(e.getKeyChar() == 's') {
            backward = true;
        }
        if(e.getKeyChar() == 'a') {
            left = true;
        }
        if(e.getKeyChar() == 'd') {
            right = true;
        }
        if(e.getKeyChar() == 'e') {
            up = true;
        }
        if(e.getKeyChar() == 'q') {
            down = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        
        if(e.getKeyChar() == 'w') {
            forward = false;
        }
        if(e.getKeyChar() == 's') {
            backward = false;
        }
        if(e.getKeyChar() == 'a') {
            left = false;
        }
        if(e.getKeyChar() == 'd') {
            right = false;
        }
        if(e.getKeyChar() == 'e') {
            up = false;
        }
        if(e.getKeyChar() == 'q') {
            down = false;
        }
        
    }

    public void update() {

        Coordinate vForward = Triangle.vectorMul(Draw.camera.lookDirection, Draw.camera.zSpeed);

        if(forward) {
            Coordinate cam = Triangle.vectorAdd((Coordinate)Draw.camera, vForward);
            Draw.camera.x = cam.x; Draw.camera.y = cam.y; Draw.camera.z = cam.z;
        }
        if(backward) {
            Coordinate cam = Triangle.vectorSub((Coordinate)Draw.camera, vForward);
            Draw.camera.x = cam.x; Draw.camera.y = cam.y; Draw.camera.z = cam.z;
        }
        if(right) {
            Draw.camera.x += Camera.xSpeed;
        }
        if(left) {
            Draw.camera.x -= Camera.xSpeed;
        }
        if(up) {
            Draw.camera.y += Camera.ySpeed;
        }
        if(down) {
            Draw.camera.y -= Camera.ySpeed;
        }

    }
    

    
}
