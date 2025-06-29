package javagame.game3d.src;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameFrame {

    public static JFrame gameFrame;
    public static GamePanel gamePanel;
    public static KeyBoardInput keyBoard;
    public static MouseInput mouse;
    

    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static Dimension dimension = toolkit.getScreenSize();
    public static float screenWidth = (float) dimension.getWidth();
    public static float screenHeight = (float) dimension.getHeight();

    public GameFrame() {
    
        gameFrame = new JFrame("3D Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        keyBoard = new KeyBoardInput();
        gameFrame.addKeyListener(keyBoard);
        mouse = new MouseInput();
        gameFrame.addMouseMotionListener(mouse);
        gameFrame.addMouseListener(mouse);

        gamePanel = new GamePanel();
        gameFrame.add(gamePanel);


        gameFrame.setVisible(true);
    }

}