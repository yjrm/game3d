package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Vector;

import javagame.game3d.src.math.Camera;
import javagame.game3d.src.math.Coordinate;
import javagame.game3d.src.math.Cube;
import javagame.game3d.src.math.Light;
import javagame.game3d.src.math.Matrices;
import javagame.game3d.src.math.Triangle;

public class Draw {

    Cube cube;
    public static Camera camera;
    public static Light lightSource;

    public Draw() {
        cube = new Cube();
        camera = new Camera();
        lightSource = new Light();
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
            float DPCameraNormal = normal.x * (one.x - camera.x) +
               normal.y * (one.y - camera.y) +
               normal.z * (one.z - camera.z);

            if(DPCameraNormal >= 0.0f) continue;

            Triangle toRender = new Triangle(one, two, three);
            toRender.albedoValue = Light.lightCalculator(normal, (Coordinate) lightSource);
            toRender.one = Matrices.VectorMatrixMultiplication(one, Matrices.PROJECTION_MATRIX());
            toRender.two = Matrices.VectorMatrixMultiplication(two, Matrices.PROJECTION_MATRIX());
            toRender.three = Matrices.VectorMatrixMultiplication(three, Matrices.PROJECTION_MATRIX());



            mesh.add(toRender);
            
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
            

            float red = (float) (255 * tri.albedoValue) / 255;
            float green = (float) (255 * tri.albedoValue) / 255;
            float blue = (float) (255 * tri.albedoValue) / 255;
            g2.setColor( new Color( red, green, blue ) );

            Polygon triangle = new Polygon();
            triangle.addPoint((int)one.x, (int)one.y);
            triangle.addPoint((int)two.x, (int)two.y);
            triangle.addPoint((int)three.x, (int)three.y);
            g2.fillPolygon(triangle);

            g2.setColor(Color.GRAY);
            g2.drawLine((int)one.x, (int)one.y, (int)two.x, (int)two.y);
            g2.drawLine((int)two.x, (int)two.y, (int)three.x, (int)three.y);
            g2.drawLine((int)one.x, (int)one.y, (int)three.x, (int)three.y);
        }
        

    }

}
