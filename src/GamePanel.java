package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    public Thread gameThread;
    public boolean isGameRunning;

    int FPS = 60;

    public GamePanel() {
        
        setBackground(Color.BLACK);

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
            System.out.println(System.nanoTime());
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

    public void paintComponent(Graphics2D g2) {
        
        super.paintComponent(g2);





        g2.dispose();

    }


    public void update() {

    }



}
