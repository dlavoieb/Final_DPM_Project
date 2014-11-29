package dpm.lejos.orientation;

import dpm.lejos.Map.*;
import lejos.nxt.comm.RConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Macario
 * @version 1.0
 */
public class Mapper {

    private MapID mapID;

    public enum MapID {Lab5, Map1, Map2, Beta1, Beta2, Beta3}

    public Node[][] graphPlane;


    /**
     * prepare the map
     * @param mapID number of the map layout used
     */
    public Mapper(MapID mapID) {
        this.mapID = mapID;
        this.graphPlane = generateGraph();
    }

    /**
     * method encoding the course layout as an array of nodes
     * @return the representation of the course layout
     */
    public Node[][] generateGraph() {
        ArrayList<Coordinate> obstacles;

        switch (mapID){

            case Lab5:

                obstacles = new ArrayList<Coordinate>();
                obstacles.add(new Coordinate(0,0));
                obstacles.add(new Coordinate(1,2));
                obstacles.add(new Coordinate(1,3));
                obstacles.add(new Coordinate(3,1));

                return MAP4x4.createMap(obstacles);

            case Map1:
                //return MAP1.createMap();

            case Map2:
                //return MAP2.createMap();

            case Beta1:

                obstacles = new ArrayList<Coordinate>();

                obstacles.add(new Coordinate(0,1));
                obstacles.add(new Coordinate(0,2));
                obstacles.add(new Coordinate(1,2));
                obstacles.add(new Coordinate(1,7));
                obstacles.add(new Coordinate(2,0));
                obstacles.add(new Coordinate(2,3));
                obstacles.add(new Coordinate(2,6));
                obstacles.add(new Coordinate(3,2));
                obstacles.add(new Coordinate(4,4));
                obstacles.add(new Coordinate(4,7));
                obstacles.add(new Coordinate(5,4));
                obstacles.add(new Coordinate(5,6));
                obstacles.add(new Coordinate(5,7));
                obstacles.add(new Coordinate(6,4));
                obstacles.add(new Coordinate(7,7));

                return MAP8x8.createMap(obstacles);

            case Beta2:
                //return MAP8x8.createMap2();

            case Beta3:
                //return MAP8x8.createMap3();
            default:
                return null;
            }
    }

    public void printDirections(List<Node> directions) {
        for (Node node : directions)
            RConsole.println("Coords = " + node.getX() + " " + node.getY());
    }

}
