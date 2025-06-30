package javagame.game3d.src.math;

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

        return tri;
    }

    public Coordinate[] getCoordinates() {
        return new Coordinate[] {
           this.one, this.two, this.three
        };
    }

}
