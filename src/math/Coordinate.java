package javagame.game3d.src.math;

public class Coordinate {
    
    public float x, y, z, w;

    public Coordinate() {
        super();
    }

    public Coordinate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static float dotProduct(Coordinate a, Coordinate b) {
        return a.x * b.x + a.y * b.y;
    }

}
