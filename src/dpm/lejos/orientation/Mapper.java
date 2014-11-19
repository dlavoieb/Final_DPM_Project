package dpm.lejos.orientation;

import dpm.lejos.Map.*;
import lejos.nxt.comm.RConsole;

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
        switch (mapID){

            case Lab5:
                return LAB5.createMap();
            case Map1:
                //return MAP1.createMap();

            case Map2:
                //return MAP2.createMap();

            case Beta1:
                return MAP8x8.createMap1();

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
