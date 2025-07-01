package javagame.game3d.src.math;

public class Camera extends Coordinate {

    public float thetaX, thetaY, thetaZ;


    // Mouse Movement Speed
    public static float yAxisRotationFactor = 9.0f;
    public static float xAxisRotationFactor = 7.0f;

    
    // Keyboard Movement Speed
    // public static float zForwardSpeed = 0.04f;
    // public static float zBackwardSpeed = 0.03f;
    // public static float xSpeed = 0.01f;
    // public static float ySpeed = 0.03f;
    public static float zForwardSpeed = 0.02f;
    public static float zBackwardSpeed = 0.01f;
    public static float xSpeed = 0.005f;
    public static float ySpeed = 0.01f;

    public void cameraTriangleTransformation(Triangle tri) {

            tri.one.z -= z;
            tri.two.z -= z;
            tri.three.z -= z;

            tri.one.x -= x;
            tri.two.x -= x;
            tri.three.x -= x;

            tri.one.y -= y;
            tri.two.y -= y;
            tri.three.y -= y;
            
    }
}
