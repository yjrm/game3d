package javagame.game3d.src.math;

import javagame.game3d.src.Draw;

public class Camera extends Coordinate {

    // Mouse Movement Speed
    public static float yAxisRotationFactor = 100.0f;

    public Coordinate lookDirection = new Coordinate(0, 0, 1);
    public float fYaw;
    
    // Keyboard Movement Speed
    // public static float zForwardSpeed = 0.04f;
    // public static float zBackwardSpeed = 0.03f;
    // public static float xSpeed = 0.01f;
    // public static float ySpeed = 0.03f;
    public static float zSpeed = 0.01f;
    public static float xSpeed = 0.01f;
    public static float ySpeed = 0.01f;

    /*
    * I implemented a camera in which the camera was stationary at (0,0,0) and it worked
    * but was not as efficient as the camera moving.
    * 
    * Hence, here I am implementing the Camera matrix as shown by YouTuber javidx9.
    * The video name is Code-It-Yourself! 3D Graphics Engine Part #3 - Cameras & Clipping
    */

        public static Coordinate crossProduct(Coordinate a, Coordinate b) {
            return new Coordinate(
                a.y * b.z - a.z * b.y,
                a.z * b.x - a.x * b.z,
                a.x * b.y - a.y * b.x
            );
        }

        private static float[][] matrix_quickInverse(float[][] m) {
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

        public static float[][] matrix_PointAt(Coordinate pos, Coordinate target, Coordinate up) {

            Coordinate newForward = Triangle.vectorSub(target, pos);
            newForward = Triangle.normalizeVector(newForward);

            Coordinate a = Triangle.vectorMul(newForward, Coordinate.dotProduct(up, newForward));
            Coordinate newUp = Triangle.vectorSub(up, a);
            newUp = Triangle.normalizeVector(newUp);

            Coordinate newRight = crossProduct(newUp, newForward);

            float[][] matrix = new float[][] {
                {newRight.x, newRight.y,  newRight.z, 0.0f},
                {newUp.x, newUp.y, newUp.z, 0.0f},
                {newForward.x, newForward.y, newForward.z, 0.0f},
                {pos.x, pos.y, pos.z, 1.0f}
            };  

            return matrix;
        }

        public static float[][] cameraMovement() {

            Coordinate vUp = new Coordinate(0, 1, 0);
            Coordinate vTarget = new Coordinate(0, 0, 1);
            float[][] matCameraRot = Matrices.Y_ROTATION_MATRIX(Draw.camera.fYaw);
            Draw.camera.lookDirection = Matrices.VectorMatrixMultiplication(vTarget, matCameraRot);
            vTarget = Triangle.vectorAdd(Draw.camera, Draw.camera.lookDirection);

            float[][] matCamera = matrix_PointAt(Draw.camera, vTarget, vUp);

            return matrix_quickInverse(matCamera);
        }



    /*
    * This is the end of the javidx9 java translation.
    */




}
