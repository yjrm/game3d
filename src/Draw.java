package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics2D;

import javagame.game3d.src.math.Camera;
import javagame.game3d.src.math.Coordinate;
import javagame.game3d.src.math.Cube;
import javagame.game3d.src.math.Matrices;
import javagame.game3d.src.math.Triangle;

public class Draw {

    Cube cube = new Cube();
    Camera camera = new Camera();

    float thetaZ, thetaX, thetaY;
    
    public void renderTriangles(Graphics2D g2) {

        for(Triangle tri : cube.getMesh()) {
            
            Triangle rotTri = Matrices.RotateMatrixTriangleTransformation(tri, thetaX, thetaY, thetaZ);
            Coordinate one = rotTri.one;
            Coordinate two = rotTri.two;
            Coordinate three = rotTri.three;
            thetaZ += 0.009; thetaY += 0.009; thetaX += 0.009;


            one.z += 3.0f;
            two.z += 3.0f;
            three.z += 3.0f;


            Coordinate normal = Coordinate.normalizedLine(one, two, three);
            if(normal.x * (one.x - camera.x) +
               normal.y * (one.y - camera.y) +
               normal.z * (one.z - camera.z) >= 0.0f) continue;

            one = Matrices.VectorMatrixMultiplication(one, Matrices.PROJECTION_MATRIX());
            two = Matrices.VectorMatrixMultiplication(two, Matrices.PROJECTION_MATRIX());
            three = Matrices.VectorMatrixMultiplication(three, Matrices.PROJECTION_MATRIX());

            
            one.x += 1.0f; one.y += 1.0f;
            two.x += 1.0f; two.y += 1.0f;
            three.x += 1.0f; three.y += 1.0f;
            
            one.x *= 0.5f * GameFrame.screenWidth;
            one.y *= 0.5f * GameFrame.screenHeight;
            two.x *= 0.5f * GameFrame.screenWidth;
            two.y *= 0.5f * GameFrame.screenHeight;
            three.x *= 0.5f * GameFrame.screenWidth;
            three.y *= 0.5f * GameFrame.screenHeight;

            g2.setColor(Color.WHITE);

            g2.drawLine((int)one.x, (int)one.y, (int)two.x, (int)two.y);
            g2.drawLine((int)two.x, (int)two.y, (int)three.x, (int)three.y);
            g2.drawLine((int)one.x, (int)one.y, (int)three.x, (int)three.y);
        }
        

    }

}
