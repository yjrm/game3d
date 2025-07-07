package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Collections;
import java.util.Vector;

import javagame.game3d.src.math.Camera;
import javagame.game3d.src.math.Coordinate;
import javagame.game3d.src.math.Cube;
import javagame.game3d.src.math.Light;
import javagame.game3d.src.math.Matrices;
import javagame.game3d.src.math.Triangle;

public class Draw {

    public static Camera camera;
    public static Light lightSource;
    public Mesh mesh;

    public Draw() {
        mesh = new Mesh("javagame\\game3d\\meshes\\axis.obj");
        camera = new Camera();
        lightSource = new Light();
    }


    public void renderTriangles(Graphics2D g2) {
        
        Vector<Triangle> triangles = new Vector<>();


        /*
         * This is apart of javidx9's tutorial for camera
         * Note: I already implemented the camera, however, his way
         * is more optimized.
         */
        // float[][] matView = Camera.cameraMovement();
        /*
         * Tutorial ends here
         */


        for(Triangle tri : mesh.getMesh()) {

            // Copy the original triangle to avoid Coordinate corruption
            Triangle renderTri = tri.copyTriangle();           

            // Find the normal of the Triangle
            renderTri.normal = Coordinate.normalizedLine(renderTri.one, renderTri.two, renderTri.three);


            // Find the Camera's Dot Product
            Coordinate triCamDisplacement = new Coordinate(
                (renderTri.one.x - camera.x),
                (renderTri.one.y - camera.y),
                (renderTri.one.z - camera.z)
            );
            float DPCameraNormal = Coordinate.dotProduct(renderTri.normal, triCamDisplacement);
            // If the Triangle faces away from the camera don't render
            if(DPCameraNormal >= 0.0f) continue;

            
            // Apply the Projection Matrix to the Triangle
            renderTri = Matrices.projectMatrixTriangleTransformation(renderTri);


            /*
            * This is apart of javidx9's tutorial for camera
             * Note: I already implemented the camera, however, his way
            * is more optimized.
            */
            // renderTri.one = Matrices.VectorMatrixMultiplication(renderTri.one, matView);
            // renderTri.two = Matrices.VectorMatrixMultiplication(renderTri.two, matView);
            // renderTri.three = Matrices.VectorMatrixMultiplication(renderTri.three, matView);
            /*
             * Tutorial ends here
            */

            triangles.add(renderTri);
            // Add renderable triangles to vector
            // triangles.addAll(Triangle.clipping(
            //     new Coordinate(0.0f, 0.0f, 0.1f),
            //     new Coordinate(0.0f, 0.0f, 0.1f),
            //     renderTri
            // ));

        } 

        // Painter's algorithm
        Collections.sort(triangles, (o1, o2) -> {
            Triangle firstTri = (Triangle) o1;
            Triangle secondTri = (Triangle) o2;

            float aveOneZ = (firstTri.one.z + firstTri.two.z + firstTri.three.z) / 3.0f;
            float aveTwoZ = (secondTri.one.z + secondTri.two.z + secondTri.three.z) / 3.0f;

            return Float.compare(aveOneZ, aveTwoZ);
        });

        for(Triangle renderTri : triangles) {

            // Calculate the albedo value of the face
            renderTri.albedoValue = Light.lightCalculator(renderTri.normal, (Coordinate) lightSource);

            // Turn the normalized Triangle Coordinates into screen Coordinates
            renderTri = Triangle.normalizedToScreenTriangle(renderTri);


            // Calculate the Triangle's RGB values
            float red   = (float) ( 255 * renderTri.albedoValue ) / 255;
            float green = (float) ( 255 * renderTri.albedoValue ) / 255;
            float blue  = (float) ( 255 * renderTri.albedoValue ) / 255;
            // Render the Triangle
            Color color = new Color(red, green, blue);
            drawSolid(g2, renderTri, color);
            drawWireFrame(g2, renderTri, Color.RED);

        }

        g2.dispose();
    }


    private void drawWireFrame(Graphics2D g2, Triangle tri, Color color) {
        g2.setColor(color);
        g2.drawLine((int)tri.one.x, (int)tri.one.y, (int)tri.two.x, (int)tri.two.y);
        g2.drawLine((int)tri.two.x, (int)tri.two.y, (int)tri.three.x, (int)tri.three.y);
        g2.drawLine((int)tri.one.x, (int)tri.one.y, (int)tri.three.x, (int)tri.three.y);
        g2.dispose();
    }

    private void drawSolid(Graphics2D g2, Triangle tri, Color color) {
        g2.setColor(color);
        Polygon triangle = new Polygon();
        triangle.addPoint((int)tri.two.x, (int)tri.two.y);
        triangle.addPoint((int)tri.three.x, (int)tri.three.y);
        triangle.addPoint((int)tri.one.x, (int)tri.one.y);
        g2.fillPolygon(triangle);
    }

}
