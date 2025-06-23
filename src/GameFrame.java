package javagame.game3d.src;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameFrame {

    JFrame gameFrame;
    GamePanel gamePanel;

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    public int screenWidth = (int) dimension.getWidth();
    public int screenHeight = (int) dimension.getHeight();

    public GameFrame() {
    
        gameFrame = new JFrame("3D Game");
        gameFrame.setSize(dimension);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        gamePanel = new GamePanel();
        gameFrame.add(gamePanel);


        gameFrame.setVisible(true);
    }

}