package javagame.game3d.src.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javagame.game3d.src.Draw;

public class Triangle {
    
    public Coordinate one;
    public Coordinate two;
    public Coordinate three;
    public float albedoValue;

    public Triangle(float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3) {

        one = new Coordinate(x1, y1, z1);
        two = new Coordinate(x2, y2, z2);
        three = new Coordinate(x3, y3, z3);
    }

    public Triangle(Coordinate one, Coordinate two, Coordinate three) {
        this.one = one;
        this.two =  two;
        this.three = three;
    }


    public void printTriangle() {
        System.out.println("\n\nCoordinate 1: x:" + one.x + " y:" + one.y + " z:" + one.z);
        System.out.println("Coordinate 2: x:" + two.x + " y:" + two.y + " z:" + two.z);
        System.out.println("Coordinate 3: x:" + three.x + " y:" + three.y + " z:" + three.z + "\n\n");

    }

    public Triangle copyTriangle() {

        Coordinate one = new Coordinate(this.one.x, this.one.y, this.one.z);
        Coordinate two = new Coordinate(this.two.x, this.two.y, this.two.z);
        Coordinate three = new Coordinate(this.three.x, this.three.y, this.three.z);

        Triangle tri = new Triangle(one, two, three);
        tri.albedoValue = this.albedoValue;

        return tri;
    }

    public Coordinate[] getCoordinates() {
        return new Coordinate[] {
           this.one, this.two, this.three
        };
    }





    // Triangle must have been projected (x,y) coordinates in order for this method



    // to function properly.


    public static List<Triangle> triangleClipping(Triangle givenTriangle) {

        Triangle tri = givenTriangle.copyTriangle();

        //Lets make sure the triangles aren't in the camera:
        if(tri.one.z <= Draw.camera.z || tri.two.z <= Draw.camera.z || tri.three.z <= Draw.camera.z) return Arrays.asList();
        // No Need To Render Triangle:
        if( tri.one.x < -1 && tri.two.x < -1 && tri.three.x < -1 ) return Arrays.asList();
        if( tri.one.x > 1  && tri.two.x > 1  && tri.three.x > 1  ) return Arrays.asList();
        if( tri.one.y < -1 && tri.two.y < -1 && tri.three.y < -1 ) return Arrays.asList();
        if( tri.one.y > 1  && tri.two.y > 1  && tri.three.y > 1  ) return Arrays.asList();
        // Triangle should be rendered as is:
        if( isPointInNormalizedPlain(tri.one) 
            && isPointInNormalizedPlain(tri.two) 
            && isPointInNormalizedPlain(tri.three) ) {
            return Arrays.asList(tri);
        }

        List<Coordinate> cords = new ArrayList<>();
        // Find all the clipped coordinates for Coordinate one:
        cords.addAll(findCoordinatesOfASide(tri.one, tri.two));
        cords.addAll(findCoordinatesOfASide(tri.two, tri.three));
        cords.addAll(findCoordinatesOfASide(tri.three, tri.one));
        Collections.sort(cords, new Triangle.CoordinateComparing());
        int size = cords.size();
        if(size == 0)  {
            return Arrays.asList(tri);
        } else if(size == 3) {
            Triangle clippedTri = new Triangle(cords.get(0), cords.get(1), cords.get(2));
            return Arrays.asList(clippedTri);
        }
        float averageX = 0.0f;
        float averageY = 0.0f;
        for(int i = 0; i < size; i++) {
            Coordinate c = cords.get(i);
            averageX += c.x;
            averageY += c.y;
        }
        averageX /= size;
        averageY /= size;
        Coordinate centroid = new Coordinate(averageX, averageY, 0);
        List<Triangle> triangles = new ArrayList<>();
        for(int j = 1; j < size; j++) {
            List<Coordinate> c = new ArrayList<>();
            c.add(cords.get(j-1));
            c.add(centroid);
            c.add(cords.get(j));
            Collections.sort(c, (p1, p2) -> {
                    Coordinate c1 = (Coordinate) p1;
                    Coordinate c2 = (Coordinate) p2;
                    if(c2.x < c1.x) return 1;
                    if(c2.y < c1.y) return 1;
                    return -1;
            });
            triangles.add( new Triangle(c.getFirst(), c.getFirst(), c.getFirst()) );
        }
        return triangles;
    }


    private static List<Coordinate> findCoordinatesOfASide(Coordinate one, Coordinate two) {

        List<Coordinate> cords = new ArrayList<>();

        float yIntersectN1 = (two.y - one.y)/(two.x - one.x) * (-1 - one.x) + one.y;
        float yIntersectP1 = (two.y - one.y)/(two.x - one.x) * (1 - one.x) + one.y;
        float xIntersectN1 = (-1 - one.y) * (two.x - one.x) / (two.y - one.y) + one.x;
        float xIntersectP1 = (1 - one.y) * (two.x - one.x) / (two.y - one.y) + one.x;

        Coordinate yN1 = new Coordinate(-1, yIntersectN1, 0);
        Coordinate yP1 = new Coordinate(1, yIntersectP1, 0);
        Coordinate xN1 = new Coordinate(xIntersectN1, -1, 0);
        Coordinate xP1 = new Coordinate(xIntersectP1, 1, 0);

        if(isPointInNormalizedPlain(yN1)) cords.add(yN1);
        if(isPointInNormalizedPlain(yP1)) cords.add(yP1);
        if(isPointInNormalizedPlain(xN1)) cords.add(xN1);
        if(isPointInNormalizedPlain(xP1)) cords.add(xP1);

        return cords;
    }

    private static boolean isPointInNormalizedPlain(Coordinate cord) {
        return (cord.x >= -1 && cord.x <= 1) && (cord.y >= -1 && cord.y <= 1);
    }

    // nested class:
    public static class CoordinateComparing implements Comparator<Coordinate> {

        @Override
        public int compare(Coordinate o1, Coordinate o2) {


            if(o1.x < o2.x) return -1;
            if(o1.x > o2.x) return  1;
            if(o1.y < o2.y) return -1;
            if(o1.y > o2.y) return  1;
            return 0;
        }
    }

}
