package javagame.game3d.src.math;

import javagame.game3d.src.GameFrame;

public class Matrices {
    
    public static float fov = 90.0f;
    public static float znear = 0.1f;
    public static float zfar = 1000.0f;
    public static float aspectRatio = GameFrame.screenHeight/GameFrame.screenWidth;
    public static float fovRad = 1.0f / (float) Math.tan(Math.toRadians(fov/2));

    public static float[][] PROJECTION_MATRIX() {

        return new float[][] {
            { aspectRatio * fovRad , 0      , 0                              , 0},
            { 0                    , fovRad , 0                              , 0},
            { 0                    , 0      , zfar / (zfar - znear)          , 1},
            { 0                    , 0      , (-zfar*znear) / (zfar - znear) , 0}
        };
    }




    // Matrix Operations

    /** To multiply the matrix and coordinate we are going to use the ijth entry theorem.
     * 
     *  This theorem states that given an m x n matrix and and n x p matrix, the 
     *  (i, j)th entry of AB can be found through the summation of k = 1 to n
     *  for each expression of a(ik) multiplied by b(kj).
     * 
     *  For simplicity we are going to manually do all 4 operations below.
     **/
    public static Coordinate VectorMatrixMultiplication(Coordinate c, float[][] matrix) {

        Coordinate product = new Coordinate();
        product.x = c.x * matrix[0][0] + c.y * matrix[1][0] + c.z * matrix[2][0] + matrix[3][0];
        product.y = c.x * matrix[0][1] + c.y * matrix[1][1] + c.z * matrix[2][1] + matrix[3][1];
        product.z = c.x * matrix[0][2] + c.y * matrix[1][2] + c.z * matrix[2][2] + matrix[3][2];
        float w = c.x * matrix[0][3] + c.y * matrix[1][3] + c.z * matrix[2][3] + matrix[3][3];

        if(w != 0.0f) {
            product.x = product.x / w;
            product.y = product.y / w;
            product.z = product.z / w;

        }

        return product;
    }
}
