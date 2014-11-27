package dpm.lejos.Map;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Node;

import java.util.ArrayList;

/**
 * Created by danielmacario on 14-11-27.
 */
public class MAP4x4 {

    public static Node[][] createMap(ArrayList<Coordinate> obstacles) {

        Node r0c0 = new Node(new Coordinate(0,0));
        Node r0c1 = new Node(new Coordinate(0,1));
        Node r0c2 = new Node(new Coordinate(0,2));
        Node r0c3 = new Node(new Coordinate(0,3));

        Node r1c0 = new Node(new Coordinate(1,0));
        Node r1c1 = new Node(new Coordinate(1,1));
        Node r1c2 = new Node(new Coordinate(1,2));
        Node r1c3 = new Node(new Coordinate(1,3));

        Node r2c0 = new Node(new Coordinate(2,0));
        Node r2c1 = new Node(new Coordinate(2,1));
        Node r2c2 = new Node(new Coordinate(2,2));
        Node r2c3 = new Node(new Coordinate(2,3));

        Node r3c0 = new Node(new Coordinate(3,0));
        Node r3c1 = new Node(new Coordinate(3,1));
        Node r3c2 = new Node(new Coordinate(3,2));
        Node r3c3 = new Node(new Coordinate(3,3));

        //Grid encoding

    /*
    0,0    0,1    0,2   0,3

    1,0    1,1    1,2   1,3

    2,0    2,1    2,2   2,3

    3,0    3,1    3,2   3,3
    */

        r0c0.addNeighbours(new Node[] {r0c1, r1c0});
        r0c1.addNeighbours(new Node[] {r0c2, r1c1, r0c0});
        r0c2.addNeighbours(new Node[] {r0c1, r1c2, r0c3});
        r0c3.addNeighbours(new Node[] {r0c2, r1c3});
        r1c0.addNeighbours(new Node[] {r1c1, r2c0, r0c0});
        r1c1.addNeighbours(new Node[] {r1c0, r0c1, r2c1, r1c2});
        r1c2.addNeighbours(new Node[] {r1c1, r0c2, r1c3, r2c2});
        r1c3.addNeighbours(new Node[] {r1c2, r0c3, r2c3});
        r2c0.addNeighbours(new Node[] {r2c1, r3c0, r1c0});
        r2c1.addNeighbours(new Node[] {r2c0, r1c1, r2c2, r3c1});
        r2c2.addNeighbours(new Node[] {r2c1, r2c3, r3c2, r1c2});
        r2c3.addNeighbours(new Node[] {r2c2, r3c3, r1c3});
        r3c0.addNeighbours(new Node[] {r2c0, r3c1});
        r3c1.addNeighbours(new Node[] {r3c0, r2c1, r3c2});
        r3c2.addNeighbours(new Node[] {r3c1, r2c2, r3c3});
        r3c3.addNeighbours(new Node[] {r2c3, r3c2});

        Node[][] map  =  new Node[][]
            {{r0c0, r0c1, r0c2, r0c3},
            {r1c0, r1c1, r1c2, r1c3},
            {r2c0, r2c1, r2c2, r2c3},
            {r3c0, r3c1, r3c2, r3c3}};

        for (Coordinate obstacleCoordinate : obstacles) {
            map[obstacleCoordinate.getX()][obstacleCoordinate.getY()].setObstacle(true);
        }

        return map;
    }

}
