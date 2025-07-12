package javagame.game3d.src.math;


public class Camera extends Coordinate {

    public float thetaX, thetaY, thetaZ;
    public Coordinate vLookDir = new Coordinate(0, 0, 1);
    public Coordinate vXDir = new Coordinate(1, 0, 0);
    public Coordinate vYDir = new Coordinate(0, 1, 0);
    public float fYaw, fXaw;

    // Mouse Movement Speed
    public static float yAxisRotationFactor = 10.0f;
    public static float xAxisRotationFactor = 9.0f;

    
    // Keyboard Movement Speed
    public static float zSpeed = 0.05f;
    public static float xSpeed = 0.05f;
    public static float ySpeed = 0.05f;

    /**
    * This is where javid9x code transferring begins
    */

    public static Coordinate Vector_Sub(Coordinate a, Coordinate b) {
        return new Coordinate(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    public static Coordinate Vector_Add(Coordinate camera, Coordinate b) {
        return new Coordinate(camera.x+b.x, camera.y+b.y, camera.z+b.z);
    }

    public static Coordinate Vector_Normalise(Coordinate v) {
        float l =  (float) Math.sqrt(Coordinate.dotProduct(v, v));
        return new Coordinate( v.x / l, v.y / l, v.z / l);
    }

    public static Coordinate Vector_Mul(Coordinate v, float k) {
        return new Coordinate(
            v.x * k,
            v.y * k,
            v.z * k
        );
    }

    public static Coordinate Vector_CrossProduct(Coordinate v1, Coordinate v2) {
        Coordinate v = new Coordinate();
        v.x = v1.y * v2.z - v1.z * v2.y;
        v.y = v1.z * v2.x - v1.x * v2.z;
        v.z = v1.x * v2.y - v1.y * v2.x;
        return v;
    }

    public static float[][] Matrix_MultiplyMatrix(float[][] m1, float[][] m2)
	{
		float[][] matrix = new float[4][4];
		for (int c = 0; c < 4; c++)
			for (int r = 0; r < 4; r++)
				matrix[r][c] = m1[r][0] * m2[0][c] + m1[r][1] * m2[1][c] + m1[r][2] * m2[2][c] + m1[r][3] * m2[3][c];
		return matrix;
	}

    public static float[][] Matrix_PointAt(Coordinate pos, Coordinate target, Coordinate up) {
        Coordinate newForward = Vector_Sub(target, pos);
        newForward = Vector_Normalise(newForward);

        Coordinate a = Vector_Mul(newForward, Coordinate.dotProduct(up, newForward));
        Coordinate newUp = Vector_Sub(up, a);
        newUp = Vector_Normalise(newUp);

        Coordinate newRight = Vector_CrossProduct(newUp, newForward);

        float[][] matrix = new float[4][4];
        matrix[0][0] = newRight.x;	matrix[0][1] = newRight.y;	matrix[0][2] = newRight.z;	matrix[0][3] = 0.0f;
        matrix[1][0] = newUp.x;		matrix[1][1] = newUp.y;		matrix[1][2] = newUp.z;		matrix[1][3] = 0.0f;
        matrix[2][0] = newForward.x;	matrix[2][1] = newForward.y;	matrix[2][2] = newForward.z;	matrix[2][3] = 0.0f;
        matrix[3][0] = pos.x;			matrix[3][1] = pos.y;			matrix[3][2] = pos.z;			matrix[3][3] = 1.0f;
        return matrix;
    }

    public static float[][] Matrix_QuickInverse(float[][] m) {
        float[][] matrix = new float[4][4];
        matrix[0][0] = m[0][0]; matrix[0][1] = m[1][0]; matrix[0][2] = m[2][0]; matrix[0][3] = 0.0f;
        matrix[1][0] = m[0][1]; matrix[1][1] = m[1][1]; matrix[1][2] = m[2][1]; matrix[1][3] = 0.0f;
        matrix[2][0] = m[0][2]; matrix[2][1] = m[1][2]; matrix[2][2] = m[2][2]; matrix[2][3] = 0.0f;
        matrix[3][0] = -(m[3][0] * matrix[0][0] + m[3][1] * matrix[1][0] + m[3][2] * matrix[2][0]);
        matrix[3][1] = -(m[3][0] * matrix[0][1] + m[3][1] * matrix[1][1] + m[3][2] * matrix[2][1]);
        matrix[3][2] = -(m[3][0] * matrix[0][2] + m[3][1] * matrix[1][2] + m[3][2] * matrix[2][2]);
        matrix[3][3] = 1.0f;
        return matrix;
    }


    /**
    * This is where it ends
    */
}
