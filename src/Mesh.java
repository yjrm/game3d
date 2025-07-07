package javagame.game3d.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import javagame.game3d.src.math.Coordinate;
import javagame.game3d.src.math.Triangle;

public class Mesh {

    private Vector<Triangle> meshTriangles;

    public Mesh(String fileName) {
        meshTriangles = new Vector<>();
        extractOBJFile(fileName);
    }

    public void extractOBJFile(String fileName) {

        Vector<Coordinate> cords = new Vector<>();

        try {

            FileReader fileInput = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileInput);

            String line;
            while( (line = br.readLine()) != null ) {
                String[] sections = line.split("\\s+");
                
                
                if(sections[0].equals("v")) {
                    
                    cords.add(
                        new Coordinate(
                            Float.parseFloat(sections[1]),
                            Float.parseFloat(sections[2]),
                            Float.parseFloat(sections[3])
                        )
                    );

                } else if(sections[0].equals("f")) {

                    String[] one   = sections[1].split("/");
                    String[] two   = sections[2].split("/");
                    String[] three = sections[3].split("/");

                    Triangle tri = new Triangle(
                           cords.get( Integer.parseInt( one  [0] ) - 1 ),
                           cords.get( Integer.parseInt( two  [0] ) - 1 ),
                           cords.get( Integer.parseInt( three[0] ) - 1 )
                        );
                

                    this.meshTriangles.add(tri);

                }
            }

            // Attempt to get all of the memory back
            cords = null;
            System.gc();

            fileInput.close();
            br.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public Vector<Triangle> getMesh() {
        return meshTriangles;
    }

    
}
