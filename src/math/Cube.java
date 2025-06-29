package javagame.game3d.src.math;

import java.util.ArrayList;
import java.util.List;

public class Cube {
    
    List<Triangle> triangles = new ArrayList<>();
    public float thetaZ, thetaX, thetaY;
    
    public Cube() {

        triangles.add(new Triangle(0, 0, 0, 0, 1, 0, 1, 1, 0));
        triangles.add(new Triangle(0, 0, 0, 1, 1, 0, 1, 0, 0));
        triangles.add(new Triangle(1, 0, 0, 1, 1, 0, 1, 1, 1));
        triangles.add(new Triangle(1, 0, 0, 1, 1, 1, 1, 0, 1));
        triangles.add(new Triangle(1, 0, 1, 1, 1, 1, 0, 1, 1));
        triangles.add(new Triangle(1, 0, 1, 0, 1, 1, 0, 0, 1));
        triangles.add(new Triangle(0, 0, 1, 0, 1, 1, 0, 1, 0));
        triangles.add(new Triangle(0, 0, 1, 0, 1, 0, 0, 0, 0));
        triangles.add(new Triangle(0, 1, 0, 0, 1, 1, 1, 1, 1));
        triangles.add(new Triangle(0, 1, 0, 1, 1, 1, 1, 1, 0));
        triangles.add(new Triangle(1, 0, 1, 0, 0, 1, 0, 0, 0));
        triangles.add(new Triangle(1, 0, 1, 0, 0, 0, 1, 0, 0));

    }


    public List<Triangle> getMesh() {
        return triangles;
    }
}
