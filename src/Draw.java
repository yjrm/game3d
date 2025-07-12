package javagame.game3d.src;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Collections;
import java.util.List;
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
        mesh = new Mesh("javagame\\game3d\\meshes\\mountains.obj");
        camera = new Camera();
        lightSource = new Light();
    }


    public void renderTriangles(Graphics2D g2) {
        
        Vector<Triangle> triangles = new Vector<>();

        Coordinate vUp = new Coordinate(0, 1, 0);
        Coordinate vTarget = new Coordinate(0, 0, 1);
        float[][] matCameraRot = Matrices.Y_ROTATION_MATRIX(camera.fYaw);
        //matCameraRot = Camera.Matrix_MultiplyMatrix(matCameraRot, Matrices.X_ROTATION_MATRIX(camera.fXaw));
        camera.vLookDir = Matrices.VectorMatrixMultiplication(vTarget, matCameraRot);
        vTarget = Camera.Vector_Add(camera, camera.vLookDir);

        float[][] matCamera = Camera.Matrix_PointAt(camera, vTarget, vUp);
        float[][] matView = Camera.Matrix_QuickInverse(matCamera);

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

            
            renderTri.one = Matrices.VectorMatrixMultiplication(renderTri.one, matView);
            renderTri.two = Matrices.VectorMatrixMultiplication(renderTri.two, matView);
            renderTri.three = Matrices.VectorMatrixMultiplication(renderTri.three, matView);

            // Add renderable triangles to vector
            List<Triangle> clipTri = Triangle.clipping(
                new Coordinate(0.0f, 0.0f, 0.1f),
                new Coordinate(0.0f, 0.0f, 1.0f),
                renderTri
            );
            for(int i = 0; i < clipTri.size(); i++) {
                triangles.add(Matrices.projectMatrixTriangleTransformation(clipTri.get(i)));
                triangles.getLast().albedoValue = renderTri.albedoValue;
                triangles.getLast().normal = renderTri.normal;
            }

        } 

        // Painter's algorithm
        Collections.sort(triangles, (o1, o2) -> {
            Triangle firstTri = (Triangle) o1;
            Triangle secondTri = (Triangle) o2;

            float aveOne = (firstTri.one.z + firstTri.two.z + firstTri.three.z) / 3.0f;
            float aveTwo = (secondTri.one.z + secondTri.two.z + secondTri.three.z) / 3.0f;

            if(aveOne == aveTwo) {
                aveOne = (firstTri.one.y + firstTri.two.y + firstTri.three.y) / 3.0f;
                aveTwo = (secondTri.one.y + secondTri.two.y + secondTri.three.y) / 3.0f;
            }

            return Float.compare(aveOne, aveTwo);
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
            drawWireFrame(g2, renderTri, Color.BLACK);
            drawSolid(g2, renderTri, color);

        }

        g2.dispose();
    }


    private void drawWireFrame(Graphics2D g2, Triangle tri, Color color) {
        g2.setColor(color);
        g2.drawLine((int)tri.one.x, (int)tri.one.y, (int)tri.two.x, (int)tri.two.y);
        g2.drawLine((int)tri.two.x, (int)tri.two.y, (int)tri.three.x, (int)tri.three.y);
        g2.drawLine((int)tri.one.x, (int)tri.one.y, (int)tri.three.x, (int)tri.three.y);
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
