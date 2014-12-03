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

    public MapID mapID;

    public enum MapID {Final1, Final2, Final3, Final4, Final5, Final6}

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

            case Final1:

                obstacles = new ArrayList<Coordinate>();
                obstacles.add(new Coordinate(0,2));
                obstacles.add(new Coordinate(1,3));
                obstacles.add(new Coordinate(2,0));
                obstacles.add(new Coordinate(2,9));
                obstacles.add(new Coordinate(4,4));
                obstacles.add(new Coordinate(4,7));
                obstacles.add(new Coordinate(4,9));
                obstacles.add(new Coordinate(5,0));
                obstacles.add(new Coordinate(5,8));
                obstacles.add(new Coordinate(5,10));
                obstacles.add(new Coordinate(6,3));
                obstacles.add(new Coordinate(7,4));
                obstacles.add(new Coordinate(7,9));
                obstacles.add(new Coordinate(8,1));
                obstacles.add(new Coordinate(8,6));
                obstacles.add(new Coordinate(9,2));
                obstacles.add(new Coordinate(9,5));
                obstacles.add(new Coordinate(9,10));
                obstacles.add(new Coordinate(10,8));
                obstacles.add(new Coordinate(10,11));
                obstacles.add(new Coordinate(11,5));
                obstacles.add(new Coordinate(11,8));

                return MAP12x12.createMap(obstacles);

            case Final2:
                obstacles = new ArrayList<Coordinate>();

                obstacles.add(new Coordinate(0,9));
                obstacles.add(new Coordinate(1,4));
                obstacles.add(new Coordinate(1,8));
                obstacles.add(new Coordinate(2,3));
                obstacles.add(new Coordinate(2,4));
                obstacles.add(new Coordinate(2,6));
                obstacles.add(new Coordinate(3,6));
                obstacles.add(new Coordinate(3,11));
                obstacles.add(new Coordinate(4,10));
                obstacles.add(new Coordinate(5,3));
                obstacles.add(new Coordinate(6,0));
                obstacles.add(new Coordinate(6,3));
                obstacles.add(new Coordinate(6,4));
                obstacles.add(new Coordinate(6,5));
                obstacles.add(new Coordinate(6,8));
                obstacles.add(new Coordinate(6,11));
                obstacles.add(new Coordinate(7,0));
                obstacles.add(new Coordinate(7,8));
                obstacles.add(new Coordinate(9,3));
                obstacles.add(new Coordinate(9,11));
                obstacles.add(new Coordinate(10,9));
                obstacles.add(new Coordinate(11,7));

                return MAP12x12.createMap(obstacles);

            case Final3:

                obstacles = new ArrayList<Coordinate>();

                obstacles.add(new Coordinate(0,7));
                obstacles.add(new Coordinate(0,11));
                obstacles.add(new Coordinate(1,3));
                obstacles.add(new Coordinate(2,10));
                obstacles.add(new Coordinate(3,0));
                obstacles.add(new Coordinate(3,6));
                obstacles.add(new Coordinate(4,4));
                obstacles.add(new Coordinate(5,2));
                obstacles.add(new Coordinate(5,4));
                obstacles.add(new Coordinate(5,6));
                obstacles.add(new Coordinate(5,9));
                obstacles.add(new Coordinate(6,4));
                obstacles.add(new Coordinate(7,3));
                obstacles.add(new Coordinate(7,10));
                obstacles.add(new Coordinate(8,0));
                obstacles.add(new Coordinate(8,2));
                obstacles.add(new Coordinate(8,10));
                obstacles.add(new Coordinate(9,3));
                obstacles.add(new Coordinate(9,11));
                obstacles.add(new Coordinate(10,4));
                obstacles.add(new Coordinate(11,7));
                obstacles.add(new Coordinate(11,10));

                return MAP12x12.createMap(obstacles);

            case Final4:

                obstacles = new ArrayList<Coordinate>();

                obstacles.add(new Coordinate(0,3));
                obstacles.add(new Coordinate(1,2));
                obstacles.add(new Coordinate(1,5));
                obstacles.add(new Coordinate(1,11));
                obstacles.add(new Coordinate(2,3));
                obstacles.add(new Coordinate(3,0));
                obstacles.add(new Coordinate(3,8));
                obstacles.add(new Coordinate(5,9));
                obstacles.add(new Coordinate(6,2));
                obstacles.add(new Coordinate(6,11));
                obstacles.add(new Coordinate(7,0));
                obstacles.add(new Coordinate(7,1));
                obstacles.add(new Coordinate(7,6));
                obstacles.add(new Coordinate(7,8));
                obstacles.add(new Coordinate(7,9));
                obstacles.add(new Coordinate(8,0));
                obstacles.add(new Coordinate(8,4));
                obstacles.add(new Coordinate(9,0));
                obstacles.add(new Coordinate(9,3));
                obstacles.add(new Coordinate(10,11));
                obstacles.add(new Coordinate(11,2));
                obstacles.add(new Coordinate(11,9));


                return MAP12x12.createMap(obstacles);

            case Final5:

                obstacles = new ArrayList<Coordinate>();

                obstacles.add(new Coordinate(1,3));
                obstacles.add(new Coordinate(1,4));
                obstacles.add(new Coordinate(1,8));
                obstacles.add(new Coordinate(1,10));
                obstacles.add(new Coordinate(2,6));
                obstacles.add(new Coordinate(2,11));
                obstacles.add(new Coordinate(3,0));
                obstacles.add(new Coordinate(3,5));
                obstacles.add(new Coordinate(4,4));
                obstacles.add(new Coordinate(4,7));
                obstacles.add(new Coordinate(5,4));
                obstacles.add(new Coordinate(5,7));
                obstacles.add(new Coordinate(5,9));
                obstacles.add(new Coordinate(6,0));
                obstacles.add(new Coordinate(6,8));
                obstacles.add(new Coordinate(8,0));
                obstacles.add(new Coordinate(8,3));
                obstacles.add(new Coordinate(8,4));
                obstacles.add(new Coordinate(9,9));
                obstacles.add(new Coordinate(9,11));
                obstacles.add(new Coordinate(10,3));
                obstacles.add(new Coordinate(10,8));

                return MAP12x12.createMap(obstacles);

            case Final6:

                obstacles = new ArrayList<Coordinate>();

                obstacles.add(new Coordinate(0,5));
                obstacles.add(new Coordinate(1,0));
                obstacles.add(new Coordinate(1,3));
                obstacles.add(new Coordinate(1,8));
                obstacles.add(new Coordinate(1,10));
                obstacles.add(new Coordinate(2,6));
                obstacles.add(new Coordinate(3,7));
                obstacles.add(new Coordinate(3,9));
                obstacles.add(new Coordinate(4,4));
                obstacles.add(new Coordinate(5,5));
                obstacles.add(new Coordinate(5,7));
                obstacles.add(new Coordinate(6,1));
                obstacles.add(new Coordinate(6,6));
                obstacles.add(new Coordinate(6,10));
                obstacles.add(new Coordinate(7,5));
                obstacles.add(new Coordinate(7,9));
                obstacles.add(new Coordinate(8,2));
                obstacles.add(new Coordinate(9,3));
                obstacles.add(new Coordinate(9,4));
                obstacles.add(new Coordinate(9,5));
                obstacles.add(new Coordinate(9,8));
                obstacles.add(new Coordinate(11,9));

                return MAP12x12.createMap(obstacles);

            default:
                return null;
            }
    }

    public void printDirections(List<Node> directions) {
        for (Node node : directions)
            RConsole.println("Coords = " + node.getX() + " " + node.getY());
    }

}
