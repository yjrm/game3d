package javagame.game3d.src.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javagame.game3d.src.Draw;
import javagame.game3d.src.GameFrame;

public class Triangle {
    
    public Coordinate one, two, three;
    public Coordinate normal;
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








    /*
     * Unfortunately I was unable to create a clipping algorithm that was sufficient
     * so I did some research and found a Youtuber called "javidx9" and used his java
     * equivalent. 
     * The video name is Code-It-Yourself! 3D Graphics Engine Part #3 - Cameras & Clipping
     */

    public static Coordinate normalizeVector(Coordinate v) {
        float l = (float) Math.sqrt(Coordinate.dotProduct(v, v));
        return new Coordinate(v.x/l, v.y/l, v.z/l);
    }

    public static Coordinate vectorSub(Coordinate one, Coordinate two) {
        return new Coordinate(one.x - two.x, one.y - two.y, one.z - two.z);
    }

    public static Coordinate vectorAdd(Coordinate one, Coordinate two) {
        return new Coordinate(one.x + two.x, one.y + two.y, one.z + two.z);
    }

    public static Coordinate vectorMul(Coordinate one, float k) {
        return new Coordinate(one.x*k, one.y*k, one.z*k);
    }

    public static Coordinate VectorIntersectPlane(Coordinate plane_p, Coordinate plane_n, Coordinate lineStart, Coordinate lineEnd) {
        plane_n = normalizeVector(plane_n);
        float plane_d = -Coordinate.dotProduct(plane_n, plane_p);
        float ad = Coordinate.dotProduct(lineStart, plane_p);
        float bd = Coordinate.dotProduct(lineEnd, plane_n);
        float t = (-plane_d - ad) / (bd - ad);
        Coordinate lineStartToEnd = vectorSub(lineEnd, lineStart);
        Coordinate lineToIntersect = vectorMul(lineStartToEnd, t);
        return vectorAdd(lineStart, lineToIntersect);
    }

    public static float dist(Coordinate c, Coordinate plane_n, Coordinate plane_p) {
        Coordinate n = normalizeVector(c);
        return (plane_n.x * c.x + plane_n.y * c.y + plane_n.z * c.z - Coordinate.dotProduct(plane_n, plane_p));
    }

    public static List<Triangle> clipping(Coordinate plane_p, Coordinate plane_n, Triangle tri) {
        
        List<Triangle> triangles = new ArrayList<>();

        plane_n = normalizeVector(plane_n);

        Coordinate[] inside_points = new Coordinate[3]; int nInsidePointCount = 0;
        Coordinate[] outside_points = new Coordinate[3]; int nOutsidePointCount = 0;

        float d0 = dist(tri.one, plane_n, plane_p);
        float d1 = dist(tri.two, plane_n, plane_p);
        float d2 = dist(tri.three, plane_n, plane_p);

        if (d0 >= 0) { 
            inside_points[nInsidePointCount++] = tri.one;
        }
		else { 
            outside_points[nOutsidePointCount++] = tri.one; 
        }
		if (d1 >= 0) { 
            inside_points[nInsidePointCount++] = tri.two; 
        }
		else { 
            outside_points[nOutsidePointCount++] = tri.two; 
        }
		if (d2 >= 0) { 
            inside_points[nInsidePointCount++] = tri.three; 
        }
		else { 
            outside_points[nOutsidePointCount++] = tri.three; 
        }

        if(nInsidePointCount == 0) return Arrays.asList();

        if(nInsidePointCount == 3) return Arrays.asList(tri);

        if(nInsidePointCount == 1 && nOutsidePointCount == 2) {
            
            triangles.add(
                new Triangle(
                    inside_points[0],
                    VectorIntersectPlane(plane_p, plane_n, inside_points[0], outside_points[0]),
                    VectorIntersectPlane(plane_p, plane_n, inside_points[0], outside_points[1])
                )
            );

            triangles.getFirst().normal = tri.normal;

            return triangles;

        }

        if(nInsidePointCount == 2 && nOutsidePointCount == 1) {
            
            triangles.add(
                new Triangle(
                    inside_points[0],
                    inside_points[1],
                    VectorIntersectPlane(plane_p, plane_n, inside_points[0], outside_points[0])
                )
            );
            triangles.getFirst().normal = tri.normal;

            triangles.add( new Triangle(
                inside_points[1],
                VectorIntersectPlane(plane_p, plane_n, inside_points[0], outside_points[0]),
                VectorIntersectPlane(plane_p, plane_n, inside_points[1], outside_points[0])
            ));
            triangles.getLast().normal = tri.normal;

            return triangles;

        }


        return triangles;
    }








    /*
     * This is the end of where the code equivalent was copied. 
     */


    private static boolean isPointInNormalizedPlain(Coordinate cord) {
        return (cord.x >= -1.5f && cord.x <= 1.5f) && (cord.y >= -1.5f && cord.y <= 1.5f);
    }

    public static Triangle normalizedToScreenTriangle(Triangle tri) {

        /*
            * Whereas before the screen was normalized to + or -
            * one in both width and height it is now + or - two;
            */
        tri.one.x += 1.0f;   tri.one.y += 1.0f;
        tri.two.x += 1.0f;   tri.two.y += 1.0f;
        tri.three.x += 1.0f; tri.three.y += 1.0f;

        /*
            * The normalized Coordinates are now multiplied by the 
            * screen's dimensions in order to obtain the screen Coordinates
            * of the Triangle to be rendered.
            */

        tri.one.x   *= 0.5f * GameFrame.screenWidth;
        tri.one.y   *= 0.5f * GameFrame.screenHeight;
        tri.two.x   *= 0.5f * GameFrame.screenWidth;
        tri.two.y   *= 0.5f * GameFrame.screenHeight;
        tri.three.x *= 0.5f * GameFrame.screenWidth;
        tri.three.y *= 0.5f * GameFrame.screenHeight;

        return tri;
    }

}
