package javagame.game3d.src.math;

public class Light extends Coordinate {

    public Light() {
        this.z = -1.0f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.w = 1.0f;
    }
    

    public static float lightCalculator(Coordinate triNormal, Coordinate lightSource) {
        float dotProduct = Coordinate.dotProduct(triNormal, lightSource);
        int sDP = (int) (dotProduct * 5);
        
        switch(sDP) {

            case 0:
                return 0.5f;
            case 1:
                return 0.7f;
            case 2:
                return 0.8f;
            case 3:
                return 0.9f;
            case 4:
                return 0.95f;
            case 5:
                return 1.0f;

        }
        
        return 0.3f;
    }
}
