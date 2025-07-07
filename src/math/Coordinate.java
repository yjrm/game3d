package javagame.game3d.src.math;

public class Coordinate {
    
    public float x, y, z;
    public float w = 1.0f;
    public float textureX, textureY;

    public Coordinate() {
        super();
    }

    public Coordinate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static float dotProduct(Coordinate a, Coordinate b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Coordinate normalizedLine(Coordinate one, Coordinate two, Coordinate three) {

        Coordinate line1 = new Coordinate();
        Coordinate line2 = new Coordinate();

        line1.x = two.x - one.x;
        line1.y = two.y - one.y;
        line1.z = two.z - one.z;

        line2.x = three.x - one.x;
        line2.y = three.y - one.y;
        line2.z = three.z - one.z;

        
        Coordinate normal = new Coordinate();
        
        normal.x = line1.y * line2.z - line1.z * line2.y;
        normal.y = line1.z * line2.x - line1.x * line2.z;
        normal.z = line1.x * line2.y - line1.y * line2.x;

        float divide = (float) Math.sqrt( Math.pow(normal.x, 2) 
                + Math.pow(normal.y, 2) 
                + Math.pow(normal.z, 2) 
            );

        if(divide != 0) {
            normal.x /= divide;
            normal.y /= divide;
            normal.z /= divide;
        }

        return normal;
    }

}
