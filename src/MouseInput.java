package javagame.game3d.src;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javagame.game3d.src.math.Camera;

public class MouseInput implements MouseMotionListener, MouseListener {

    public static boolean isGameFocused = true;
    Cursor noCursor;
    Robot robot;

    private float centerX = GameFrame.screenWidth / 2;
    private float centerY = GameFrame.screenHeight / 2;

    public MouseInput() {
        Image cursor = GameFrame.toolkit.createImage(new byte[0]);
        noCursor = GameFrame.toolkit.createCustomCursor(cursor,
                new Point(0, 0), "blank cursor");
        GameFrame.gameFrame.getContentPane().setCursor(noCursor);
        
        try {
            robot = new Robot();
            robot.mouseMove((int)GameFrame.screenWidth, (int)GameFrame.screenHeight);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void mouseDragged(MouseEvent e) {}

    
    
    public void mouseMoved(MouseEvent e) {

        if(isGameFocused) {
            float mouseLocation = 0.0f;
            if(mouseLocation <= GameFrame.screenWidth/2) {
                Draw.camera.fYaw -= (e.getXOnScreen() - centerX) / Camera.yAxisRotationFactor;
            } else if(mouseLocation > GameFrame.screenWidth/2) {
                Draw.camera.fYaw += (e.getXOnScreen() - centerX) / Camera.yAxisRotationFactor;
            }
        }

    }

    // For later use
    public void update() {
        if(isGameFocused) {
            robot.mouseMove( (int) (GameFrame.screenWidth * 0.5), (int) (GameFrame.screenHeight * 0.5) );
        }


    }

    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() >= 3) {
            isGameFocused = (isGameFocused) ? false : true;
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
    
}
