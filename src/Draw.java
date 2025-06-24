package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics2D;

import javagame.game3d.src.math.Coordinate;
import javagame.game3d.src.math.Cube;
import javagame.game3d.src.math.Matrices;
import javagame.game3d.src.math.Triangle;

public class Draw {

    Cube cube = new Cube();
    
    public void renderTriangles(Graphics2D g2) {

        for(Triangle tri : cube.getMesh()) {

            Coordinate one = new Coordinate(tri.one.x, tri.one.y, tri.one.z);
            Coordinate two = new Coordinate(tri.two.x, tri.two.y, tri.two.z);
            Coordinate three = new Coordinate(tri.three.x, tri.three.y, tri.three.z);

            one = Matrices.VectorMatrixMultiplication(tri.one, Matrices.PROJECTION_MATRIX());
            two = Matrices.VectorMatrixMultiplication(tri.two, Matrices.PROJECTION_MATRIX());
            three = Matrices.VectorMatrixMultiplication(tri.three, Matrices.PROJECTION_MATRIX());

            
            one.x += 1.0f; one.y += 1.0f;
            two.x += 1.0f; two.y += 1.0f;
            three.x += 1.0f; three.y += 1.0f;
            
            one.x *= 0.5f * GameFrame.screenWidth;
            one.y *= 0.5f * GameFrame.screenHeight;
            two.x *= 0.5f * GameFrame.screenWidth;
            two.y *= 0.5f * GameFrame.screenHeight;
            three.x *= 0.5f * GameFrame.screenWidth;
            three.y *= 0.5f * GameFrame.screenHeight;
            
            System.out.println(one.x + " " + one.y + " " + one.z);
            System.out.println(two.x + " " + two.y + " " + one.z);
            System.out.println(three.x + " " + three.y + " " + one.z);


            g2.setColor(Color.WHITE);

            g2.drawLine((int)one.x, (int)one.y, (int)two.x, (int)two.y);
            g2.drawLine((int)two.x, (int)two.y, (int)three.x, (int)three.y);
            g2.drawLine((int)one.x, (int)one.y, (int)three.x, (int)three.y);
        }
        

    }

}
