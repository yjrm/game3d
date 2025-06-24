package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    public Thread gameThread;
    public boolean isGameRunning;
    public Draw draw;

    public int FPS = 60;

    public GamePanel() {
        
        setBackground(Color.BLACK);

        draw = new Draw();

        isGameRunning = true;
        gameThread = new Thread(this);
        gameThread.start();

    }


    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime;

        while(isGameRunning) {

            nextDrawTime = System.nanoTime() + drawInterval;

            update();
            
            repaint();

            double remainingTime = nextDrawTime - System.nanoTime();
            remainingTime *= 0.0000001;

            if(remainingTime <= 0) continue;


            try {
                Thread.sleep( (long) remainingTime);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        draw.renderTriangles(g2);

        g2.dispose();

    }


    public void update() {

    }



}
