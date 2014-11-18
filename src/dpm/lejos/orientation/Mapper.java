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

    public enum MapID {LAB5, MAP1, MAP2, BETA1, BETA2, BETA3}

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

            case LAB5:
                return LAB5.createMap();

            case MAP1:
                return MAP1.createMap();

            case MAP2:
                return MAP2.createMap();

            case BETA1:
               return BETA1.createMap();

            case BETA2:
              return BETA2.createMap();

            case BETA3:
              return BETA3.createMap();

            default:
                graphPlane = null;
                return graphPlane;
            }
    }

    public void printDirections(List<Node> directions) {
        for (Node node : directions)
            RConsole.println("Coords = " + node.getX() + " " + node.getY());
    }

}
