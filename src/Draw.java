package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

import javagame.game3d.src.math.Camera;
import javagame.game3d.src.math.Coordinate;
import javagame.game3d.src.math.Cube;
import javagame.game3d.src.math.Matrices;
import javagame.game3d.src.math.Triangle;

public class Draw {

    Cube cube;
    public static Camera camera;

    public Draw() {
        cube = new Cube();
        camera = new Camera();
    }


    public void renderTriangles(Graphics2D g2) {

        Vector<Triangle> mesh = new Vector<>();

        for(Triangle tri : cube.getMesh()) {
            
            Triangle rotTri = Matrices.RotateMatrixTriangleTransformation(tri, cube.thetaX + camera.thetaX,
                                                                        cube.thetaY + camera.thetaY,
                                                                        cube.thetaZ + camera.thetaZ);
            Coordinate one = rotTri.one;
            Coordinate two = rotTri.two;
            Coordinate three = rotTri.three;
           // cube.thetaZ += 0.009; cube.thetaY += 0.009; cube.thetaX += 0.009;

            camera.cameraTriangleTransformation(rotTri);            


            Coordinate normal = Coordinate.normalizedLine(one, two, three);
            if(normal.x * (one.x - camera.x) +
               normal.y * (one.y - camera.y) +
               normal.z * (one.z - camera.z) >= 0.0f) continue;
            one = Matrices.VectorMatrixMultiplication(one, Matrices.PROJECTION_MATRIX());
            two = Matrices.VectorMatrixMultiplication(two, Matrices.PROJECTION_MATRIX());
            three = Matrices.VectorMatrixMultiplication(three, Matrices.PROJECTION_MATRIX());

            mesh.add( new Triangle(one, two, three));
            
        }

        for(Triangle tri : mesh) {

            Coordinate one = tri.one;
            Coordinate two = tri.two;
            Coordinate three = tri.three;

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
