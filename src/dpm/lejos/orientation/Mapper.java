package dpm.lejos.orientation;

import dpm.lejos.project.Navigation;
import dpm.lejos.project.Robot;
import lejos.nxt.comm.RConsole;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Macario
 * @version 1.0
 */
public class Mapper {

    private MapID mapID;

    public enum MapID {LAB4, MAP1, MAP2}

    public Node[][] graphPlane;


    /**
     * prepare the map
     * @param mapID number of the map layout used
     */
    public Mapper(MapID mapID) {
        this.mapID = mapID;
        this.graphPlane = generateGraph();
    }

    //TODO: encode the 12x12 grid as a graph. We receive the data by bluetooth, then to traverse all we need to is set certain nodes to be obstacles

    /**
     * method encoding the course layout as an array of nodes
     * @return the representation of the course layout
     */
    public Node[][] generateGraph() {

        Node r0c0 = new Node(new Coordinate(0,0));
        Node r0c1 = new Node(new Coordinate(0,1));
        Node r0c2 = new Node(new Coordinate(0,2));
        Node r0c3 = new Node(new Coordinate(0,3));
        Node r0c4 = new Node(new Coordinate(0,4));
        Node r0c5 = new Node(new Coordinate(0,5));
        Node r0c6 = new Node(new Coordinate(0,6));
        Node r0c7 = new Node(new Coordinate(0,7));
        Node r0c8 = new Node(new Coordinate(0,8));
        Node r0c9 = new Node(new Coordinate(0,9));
        Node r0c10 = new Node(new Coordinate(0,10));
        Node r0c11 = new Node(new Coordinate(0,11));

        Node r1c0 = new Node(new Coordinate(1,0));
        Node r1c1 = new Node(new Coordinate(1,1));
        Node r1c2 = new Node(new Coordinate(1,2));
        Node r1c3 = new Node(new Coordinate(1,3));
        Node r1c4 = new Node(new Coordinate(1,4));
        Node r1c5 = new Node(new Coordinate(1,5));
        Node r1c6 = new Node(new Coordinate(1,6));
        Node r1c7 = new Node(new Coordinate(1,7));
        Node r1c8 = new Node(new Coordinate(1,8));
        Node r1c9 = new Node(new Coordinate(1,9));
        Node r1c10 = new Node(new Coordinate(1,10));
        Node r1c11 = new Node(new Coordinate(1,11));

        Node r2c0 = new Node(new Coordinate(2,0));
        Node r2c1 = new Node(new Coordinate(2,1));
        Node r2c2 = new Node(new Coordinate(2,2));
        Node r2c3 = new Node(new Coordinate(2,3));
        Node r2c4 = new Node(new Coordinate(2,4));
        Node r2c5 = new Node(new Coordinate(2,5));
        Node r2c6 = new Node(new Coordinate(2,6));
        Node r2c7 = new Node(new Coordinate(2,7));
        Node r2c8 = new Node(new Coordinate(2,8));
        Node r2c9 = new Node(new Coordinate(2,9));
        Node r2c10 = new Node(new Coordinate(2,10));
        Node r2c11 = new Node(new Coordinate(2,11));

        Node r3c0 = new Node(new Coordinate(3,0));
        Node r3c1 = new Node(new Coordinate(3,1));
        Node r3c2 = new Node(new Coordinate(3,2));
        Node r3c3 = new Node(new Coordinate(3,3));
        Node r3c4 = new Node(new Coordinate(3,4));
        Node r3c5 = new Node(new Coordinate(3,5));
        Node r3c6 = new Node(new Coordinate(3,6));
        Node r3c7 = new Node(new Coordinate(3,7));
        Node r3c8 = new Node(new Coordinate(3,8));
        Node r3c9 = new Node(new Coordinate(3,9));
        Node r3c10 = new Node(new Coordinate(3,10));
        Node r3c11 = new Node(new Coordinate(3,11));

        Node r4c0 = new Node(new Coordinate(4,0));
        Node r4c1 = new Node(new Coordinate(4,1));
        Node r4c2 = new Node(new Coordinate(4,2));
        Node r4c3 = new Node(new Coordinate(4,3));
        Node r4c4 = new Node(new Coordinate(4,4));
        Node r4c5 = new Node(new Coordinate(4,5));
        Node r4c6 = new Node(new Coordinate(4,6));
        Node r4c7 = new Node(new Coordinate(4,7));
        Node r4c8 = new Node(new Coordinate(4,8));
        Node r4c9 = new Node(new Coordinate(4,9));
        Node r4c10 = new Node(new Coordinate(4,10));
        Node r4c11 = new Node(new Coordinate(4,11));

        Node r5c0 = new Node(new Coordinate(5,0));
        Node r5c1 = new Node(new Coordinate(5,1));
        Node r5c2 = new Node(new Coordinate(5,2));
        Node r5c3 = new Node(new Coordinate(5,3));
        Node r5c4 = new Node(new Coordinate(5,4));
        Node r5c5 = new Node(new Coordinate(5,5));
        Node r5c6 = new Node(new Coordinate(5,6));
        Node r5c7 = new Node(new Coordinate(5,7));
        Node r5c8 = new Node(new Coordinate(5,8));
        Node r5c9 = new Node(new Coordinate(5,9));
        Node r5c10 = new Node(new Coordinate(5,10));
        Node r5c11 = new Node(new Coordinate(5,11));

        Node r6c0 = new Node(new Coordinate(6,0));
        Node r6c1 = new Node(new Coordinate(6,1));
        Node r6c2 = new Node(new Coordinate(6,2));
        Node r6c3 = new Node(new Coordinate(6,3));
        Node r6c4 = new Node(new Coordinate(6,4));
        Node r6c5 = new Node(new Coordinate(6,5));
        Node r6c6 = new Node(new Coordinate(6,6));
        Node r6c7 = new Node(new Coordinate(6,7));
        Node r6c8 = new Node(new Coordinate(6,8));
        Node r6c9 = new Node(new Coordinate(6,9));
        Node r6c10 = new Node(new Coordinate(6,10));
        Node r6c11 = new Node(new Coordinate(6,11));

        Node r7c0 = new Node(new Coordinate(7,0));
        Node r7c1 = new Node(new Coordinate(7,1));
        Node r7c2 = new Node(new Coordinate(7,2));
        Node r7c3 = new Node(new Coordinate(7,3));
        Node r7c4 = new Node(new Coordinate(7,4));
        Node r7c5 = new Node(new Coordinate(7,5));
        Node r7c6 = new Node(new Coordinate(7,6));
        Node r7c7 = new Node(new Coordinate(7,7));
        Node r7c8 = new Node(new Coordinate(7,8));
        Node r7c9 = new Node(new Coordinate(7,9));
        Node r7c10 = new Node(new Coordinate(7,10));
        Node r7c11 = new Node(new Coordinate(7,11));

        Node r8c0 = new Node(new Coordinate(8,0));
        Node r8c1 = new Node(new Coordinate(8,1));
        Node r8c2 = new Node(new Coordinate(8,2));
        Node r8c3 = new Node(new Coordinate(8,3));
        Node r8c4 = new Node(new Coordinate(8,4));
        Node r8c5 = new Node(new Coordinate(8,5));
        Node r8c6 = new Node(new Coordinate(8,6));
        Node r8c7 = new Node(new Coordinate(8,7));
        Node r8c8 = new Node(new Coordinate(8,8));
        Node r8c9 = new Node(new Coordinate(8,9));
        Node r8c10 = new Node(new Coordinate(8,10));
        Node r8c11 = new Node(new Coordinate(8,11));

        Node r9c0 = new Node(new Coordinate(9,0));
        Node r9c1 = new Node(new Coordinate(9,1));
        Node r9c2 = new Node(new Coordinate(9,2));
        Node r9c3 = new Node(new Coordinate(9,3));
        Node r9c4 = new Node(new Coordinate(9,4));
        Node r9c5 = new Node(new Coordinate(9,5));
        Node r9c6 = new Node(new Coordinate(9,6));
        Node r9c7 = new Node(new Coordinate(9,7));
        Node r9c8 = new Node(new Coordinate(9,8));
        Node r9c9 = new Node(new Coordinate(9,9));
        Node r9c10 = new Node(new Coordinate(9,10));
        Node r9c11 = new Node(new Coordinate(9,11));

        Node r10c0 = new Node(new Coordinate(10,0));
        Node r10c1 = new Node(new Coordinate(10,1));
        Node r10c2 = new Node(new Coordinate(10,2));
        Node r10c3 = new Node(new Coordinate(10,3));
        Node r10c4 = new Node(new Coordinate(10,4));
        Node r10c5 = new Node(new Coordinate(10,5));
        Node r10c6 = new Node(new Coordinate(10,6));
        Node r10c7 = new Node(new Coordinate(10,7));
        Node r10c8 = new Node(new Coordinate(10,8));
        Node r10c9 = new Node(new Coordinate(10,9));
        Node r10c10 = new Node(new Coordinate(10,10));
        Node r10c11 = new Node(new Coordinate(10,11));

        Node r11c0 = new Node(new Coordinate(11,0));
        Node r11c1 = new Node(new Coordinate(11,1));
        Node r11c2 = new Node(new Coordinate(11,2));
        Node r11c3 = new Node(new Coordinate(11,3));
        Node r11c4 = new Node(new Coordinate(11,4));
        Node r11c5 = new Node(new Coordinate(11,5));
        Node r11c6 = new Node(new Coordinate(11,6));
        Node r11c7 = new Node(new Coordinate(11,7));
        Node r11c8 = new Node(new Coordinate(11,8));
        Node r11c9 = new Node(new Coordinate(11,9));
        Node r11c10 = new Node(new Coordinate(11,10));
        Node r11c11 = new Node(new Coordinate(11,11));


        Node[][] graphPlane;

        switch (mapID){

            case LAB4:
                // r0c0, r1c2, r1c3, r3c1 are obstacles - means no nodes

                r0c1.addNeighbours(new Node[] {r0c2, r1c1});
                r0c2.addNeighbours(new Node[] {r0c1, r0c3, r1c2});
                r0c3.addNeighbours(new Node[] {r0c2, r1c3});
                r1c0.addNeighbours(new Node[] {r1c1, r2c0});
                r1c1.addNeighbours(new Node[] {r1c0, r0c1, r2c1, r1c2});
                r2c0.addNeighbours(new Node[] {r2c1, r3c0, r1c0});
                r2c1.addNeighbours(new Node[] {r2c0, r1c1, r2c2});
                r2c2.addNeighbours(new Node[] {r2c1, r2c3, r3c2});
                r2c3.addNeighbours(new Node[] {r2c2, r3c3});
                r3c0.addNeighbours(new Node[] {r2c0});
                r3c2.addNeighbours(new Node[] {r2c2, r3c3});
                r3c3.addNeighbours(new Node[] {r2c3, r3c2});

                graphPlane = new Node[][] {{r0c0, r0c1, r0c2, r0c3},
                                            {r1c0, r1c1, r1c2, r1c3},
                                            {r2c0, r2c1, r2c2, r2c3},
                                            {r3c0, r3c1, r3c2, r3c3}};
                return graphPlane;

            case MAP1:

                r0c0.addNeighbours(new Node[] {r1c0, r0c1});
                r0c1.addNeighbours(new Node[] {r0c0, r1c1 });

                r0c3.addNeighbours(new Node[] {r0c4});
                r0c4.addNeighbours(new Node[] {r0c3, r1c4, r0c5});
                r0c5.addNeighbours(new Node[] {r0c4, r1c5});

                r0c7.addNeighbours(new Node[] {r1c7, r0c8});
                r0c8.addNeighbours(new Node[] {r0c7, r1c8, r0c9});
                r0c9.addNeighbours(new Node[] {r0c8, r1c9, r0c10});
                r0c10.addNeighbours(new Node[] {r0c9, r1c10, r0c11});
                r0c11.addNeighbours(new Node[] {r0c10});


                r1c1.addNeighbours(new Node[] {r2c1, r1c2});
                r1c2.addNeighbours(new Node[] {r1c1, r2c2});

                r1c4.addNeighbours(new Node[] {r0c4, r2c4, r1c5});
                r1c5.addNeighbours(new Node[] {r1c4, r0c5, r2c5, r1c6});
                r1c6.addNeighbours(new Node[] {r1c5, r2c6, r1c7});
                r1c7.addNeighbours(new Node[] {r1c6, r0c7, r2c7, r1c8});
                r1c8.addNeighbours(new Node[] {r1c7, r0c8, r2c8, r1c9});
                r1c9.addNeighbours(new Node[] {r1c8, r0c9, r1c10});
                r1c10.addNeighbours(new Node[] {r1c9, r0c10, r2c10});


                r2c0.addNeighbours(new Node[] {r3c0, r2c1});
                r2c1.addNeighbours(new Node[] {r2c0, r1c1, r3c1, r2c2});
                r2c2.addNeighbours(new Node[] {r2c1, r1c2, r2c3});
                r2c3.addNeighbours(new Node[] {r2c2, r1c3, r3c3, r2c4});
                r2c4.addNeighbours(new Node[] {r2c3, r1c4, r2c5});
                r2c5.addNeighbours(new Node[] {r2c4, r1c5, r2c6});
                r2c6.addNeighbours(new Node[] {r2c5, r1c6, r3c6, r2c7});
                r2c7.addNeighbours(new Node[] {r2c6, r1c7, r3c7, r2c8});
                r2c8.addNeighbours(new Node[] {r2c7, r1c8, r3c8});

                r2c10.addNeighbours(new Node[] {r1c10, r3c10, r2c11});
                r2c11.addNeighbours(new Node[] {r2c10, r1c11, r3c11});

                r3c0.addNeighbours(new Node[] {r2c0, r4c0, r3c1});
                r3c1.addNeighbours(new Node[] {r3c0, r2c1, r4c1});

                r3c3.addNeighbours(new Node[] {r2c3, r4c3});


                r3c6.addNeighbours(new Node[] {r2c6, r4c6, r3c7});
                r3c7.addNeighbours(new Node[] {r3c6, r2c7, r4c7, r3c8});
                r3c8.addNeighbours(new Node[] {r3c7, r2c8, r3c9});
                r3c9.addNeighbours(new Node[] {r3c8, r4c9, r3c10});
                r3c10.addNeighbours(new Node[] {r3c9, r2c10, r4c10, r3c11});
                r3c11.addNeighbours(new Node[] {r3c10, r2c11, r4c11});

                r4c0.addNeighbours(new Node[] {r3c0, r5c0, r4c1});
                r4c1.addNeighbours(new Node[] {r4c0, r3c1, r5c1, r4c2});
                r4c2.addNeighbours(new Node[] {r4c1, r5c2, r4c3});
                r4c3.addNeighbours(new Node[] {r4c2, r3c3, r5c3, r4c4});
                r4c4.addNeighbours(new Node[] {r4c3, r5c4, r4c5});
                r4c5.addNeighbours(new Node[] {r4c4, r4c6});
                r4c6.addNeighbours(new Node[] {r4c5, r3c6, r5c6, r4c7});
                r4c7.addNeighbours(new Node[] {r4c6, r3c7, r5c7});

                r4c9.addNeighbours(new Node[] {r3c9, r5c9, r4c10});
                r4c10.addNeighbours(new Node[] {r4c9, r3c10, r5c10, r4c11});
                r4c11.addNeighbours(new Node[] {r4c10, r3c11, r5c11});

                r5c0.addNeighbours(new Node[] {r4c0, r6c0, r5c1});
                r5c1.addNeighbours(new Node[] {r5c0, r4c1, r6c1, r5c2});
                r5c2.addNeighbours(new Node[] {r5c1, r4c2, r6c2, r5c3});
                r5c3.addNeighbours(new Node[] {r5c2, r4c3, r6c3, r5c4});
                r5c4.addNeighbours(new Node[] {r5c3, r4c4, r6c4});

                r5c6.addNeighbours(new Node[] {r4c6, r6c6, r5c7});
                r5c7.addNeighbours(new Node[] {r5c6, r4c7, r6c7, r5c8});
                r5c8.addNeighbours(new Node[] {r5c7, r6c8, r5c9});
                r5c9.addNeighbours(new Node[] {r5c8, r4c9, r6c9, r5c10});
                r5c10.addNeighbours(new Node[] {r5c9, r4c10, r6c10, r5c11});
                r5c11.addNeighbours(new Node[] {r5c10, r4c11, r6c11});

                r6c0.addNeighbours(new Node[] {r5c0, r7c0, r6c1});
                r6c1.addNeighbours(new Node[] {r6c0, r5c1, r7c1, r6c2});
                r6c2.addNeighbours(new Node[] {r6c1, r5c2, r6c3});
                r6c3.addNeighbours(new Node[] {r6c2, r5c3, r7c3, r6c4});
                r6c4.addNeighbours(new Node[] {r6c3, r5c4, r7c4, r6c5});
                r6c5.addNeighbours(new Node[] {r6c4, r7c5, r6c6});
                r6c6.addNeighbours(new Node[] {r6c5, r5c6, r6c7});
                r6c7.addNeighbours(new Node[] {r6c6, r5c7, r7c7, r6c8});
                r6c8.addNeighbours(new Node[] {r6c7, r5c8, r7c8, r6c9});
                r6c9.addNeighbours(new Node[] {r6c8, r5c9, r7c9, r6c10});
                r6c10.addNeighbours(new Node[] {r6c9, r5c10, r7c10, r6c11});
                r6c11.addNeighbours(new Node[] {r6c10, r5c11, r7c11});

                r7c0.addNeighbours(new Node[] {r6c0, r7c1});
                r7c1.addNeighbours(new Node[] {r7c0, r6c1, r8c1});

                r7c3.addNeighbours(new Node[] {r6c3, r8c3, r7c4});
                r7c4.addNeighbours(new Node[] {r7c3, r6c4, r8c4, r7c5});
                r7c5.addNeighbours(new Node[] {r7c4, r6c5, r8c5});

                r7c7.addNeighbours(new Node[] {r6c7, r8c7, r7c8});
                r7c8.addNeighbours(new Node[] {r7c7, r6c8, r8c8, r7c9});
                r7c9.addNeighbours(new Node[] {r7c8, r6c9, r8c9, r7c10});
                r7c10.addNeighbours(new Node[] {r7c9, r6c10, r8c10, r7c11});
                r7c11.addNeighbours(new Node[] {r7c10, r6c11, r8c11});


                r8c1.addNeighbours(new Node[] {r7c1, r9c1, r8c2});
                r8c2.addNeighbours(new Node[] {r8c1 , r8c3});
                r8c3.addNeighbours(new Node[] {r8c2, r7c3, r8c4});
                r8c4.addNeighbours(new Node[] {r8c3, r7c4, r9c4, r8c5});
                r8c5.addNeighbours(new Node[] {r8c4, r7c5, r9c5});

                r8c7.addNeighbours(new Node[] {r7c7, r9c7, r8c8});
                r8c8.addNeighbours(new Node[] {r8c7, r7c8, r9c8, r8c9});
                r8c9.addNeighbours(new Node[] {r8c8, r7c9, r9c9, r8c10});
                r8c10.addNeighbours(new Node[] {r8c9, r7c10, r9c10, r8c11});
                r8c11.addNeighbours(new Node[] {r8c10, r7c11, r9c11});

                r9c0.addNeighbours(new Node[] {r10c0, r9c1});
                r9c1.addNeighbours(new Node[] {r9c0, r8c1, r10c1});


                r9c4.addNeighbours(new Node[] {r8c4, r10c4, r9c5});
                r9c5.addNeighbours(new Node[] {r9c4, r8c5, r10c5, r9c6});
                r9c6.addNeighbours(new Node[] {r9c5 , r9c7});
                r9c7.addNeighbours(new Node[] {r9c6, r8c7, r10c7, r9c8});
                r9c8.addNeighbours(new Node[] {r9c7, r8c8, r9c9});
                r9c9.addNeighbours(new Node[] {r9c8, r8c9, r10c9, r9c10});
                r9c10.addNeighbours(new Node[] {r9c9, r8c10, r10c10, r9c11});
                r9c11.addNeighbours(new Node[] {r9c10, r8c11, r10c11});

                r10c0.addNeighbours(new Node[] {r9c0, r11c0, r10c1});
                r10c1.addNeighbours(new Node[] {r10c0, r9c1, r11c1, r10c2});
                r10c2.addNeighbours(new Node[] {r10c1, r11c2});

                r10c4.addNeighbours(new Node[] {r9c4, r11c4, r10c5});
                r10c5.addNeighbours(new Node[] {r10c4, r9c5, r11c5});

                r10c7.addNeighbours(new Node[] {r9c7, r11c7});

                r10c9.addNeighbours(new Node[] {r9c9, r11c9, r10c10});
                r10c10.addNeighbours(new Node[] {r10c9, r9c10, r11c10, r10c11});
                r10c11.addNeighbours(new Node[] {r10c10, r9c11, r11c11});

                r11c0.addNeighbours(new Node[] {r10c0, r11c1});
                r11c1.addNeighbours(new Node[] {r11c0, r10c1, r11c2});
                r11c2.addNeighbours(new Node[] {r11c1, r10c2});

                r11c4.addNeighbours(new Node[] {r10c4, r11c5});
                r11c5.addNeighbours(new Node[] {r11c4, r10c5});

                r11c7.addNeighbours(new Node[] {r10c7});

                r11c9.addNeighbours(new Node[] {r10c9, r11c10});
                r11c10.addNeighbours(new Node[] {r11c9, r10c10, r11c11});
                r11c11.addNeighbours(new Node[] {r11c10, r10c11});


                break;

            case MAP2:
                r0c0.addNeighbours(new Node[] {r1c0, r0c1});
                r0c1.addNeighbours(new Node[] {r0c0, r1c1});


                r0c4.addNeighbours(new Node[] {r1c4, r0c5});
                r0c5.addNeighbours(new Node[] {r0c4, r1c5, r0c6});
                r0c6.addNeighbours(new Node[] {r0c5, r1c6});

                r0c8.addNeighbours(new Node[] {r1c8, r0c9});
                r0c9.addNeighbours(new Node[] {r0c8, r1c9, r0c10});
                r0c10.addNeighbours(new Node[] {r0c9, r1c10, r0c11});
                r0c11.addNeighbours(new Node[] {r0c10, r1c11});

                r1c0.addNeighbours(new Node[] { r0c0, r2c0, r1c1});
                r1c1.addNeighbours(new Node[] {r1c0, r0c1, r1c2});
                r1c2.addNeighbours(new Node[] {r1c1, r2c2, r1c3});
                r1c3.addNeighbours(new Node[] {r1c2, r2c3, r1c4});
                r1c4.addNeighbours(new Node[] {r1c3, r0c4, r2c4, r1c5});
                r1c5.addNeighbours(new Node[] {r1c4, r0c5, r2c5, r1c6});
                r1c6.addNeighbours(new Node[] {r1c5, r0c6, r2c6, r1c7});
                r1c7.addNeighbours(new Node[] {r1c6, r2c7, r1c8});
                r1c8.addNeighbours(new Node[] {r1c7, r0c8, r1c9});
                r1c9.addNeighbours(new Node[] {r1c8, r0c9, r2c9, r1c10});
                r1c10.addNeighbours(new Node[] {r1c9, r0c10, r2c10, r1c11});
                r1c11.addNeighbours(new Node[] {r1c10, r0c11, r2c11});

                r2c0.addNeighbours(new Node[] {r1c0, r3c0});

                r2c2.addNeighbours(new Node[] {r1c2, r3c2, r2c3});
                r2c3.addNeighbours(new Node[] {r2c2, r1c3, r3c3, r2c4});
                r2c4.addNeighbours(new Node[] {r2c3, r1c4, r3c4, r2c5});
                r2c5.addNeighbours(new Node[] {r2c4, r1c5, r2c6});
                r2c6.addNeighbours(new Node[] {r2c5, r1c6, r3c6, r2c7});
                r2c7.addNeighbours(new Node[] {r2c6, r1c7});

                r2c9.addNeighbours(new Node[] {r1c9, r3c9, r2c10});
                r2c10.addNeighbours(new Node[] {r2c9, r1c10, r3c10, r2c11});
                r2c11.addNeighbours(new Node[] {r2c10, r1c11});

                r3c0.addNeighbours(new Node[] {r2c0, r4c0, r3c1});
                r3c1.addNeighbours(new Node[] {r3c0, r4c1, r3c2});
                r3c2.addNeighbours(new Node[] {r3c1, r2c2, r4c2, r3c3});
                r3c3.addNeighbours(new Node[] {r3c2, r2c3, r4c3, r3c4});
                r3c4.addNeighbours(new Node[] {r3c3, r2c4, r4c4});

                r3c6.addNeighbours(new Node[] {r2c6, r4c6});

                r3c8.addNeighbours(new Node[] {r4c8, r3c9});
                r3c9.addNeighbours(new Node[] {r3c8, r2c9, r3c10});
                r3c10.addNeighbours(new Node[] {r3c9, r2c10, r4c10});


                r4c0.addNeighbours(new Node[] {r3c0, r5c0, r4c1});
                r4c1.addNeighbours(new Node[] {r4c0, r3c1, r5c1, r4c2});
                r4c2.addNeighbours(new Node[] {r4c1, r3c2, r5c2, r4c3});
                r4c3.addNeighbours(new Node[] {r4c2, r3c3, r5c3, r4c4});
                r4c4.addNeighbours(new Node[] {r4c3, r3c4, r5c4, r4c5});
                r4c5.addNeighbours(new Node[] {r4c4, r5c5, r4c6});
                r4c6.addNeighbours(new Node[] {r4c5, r3c6, r4c7});
                r4c7.addNeighbours(new Node[] {r4c6, r5c7, r4c8});
                r4c8.addNeighbours(new Node[] {r4c7, r3c8, r5c8});

                r4c10.addNeighbours(new Node[] {r3c10, r5c10, r4c11});
                r4c11.addNeighbours(new Node[] {r4c10});

                r5c0.addNeighbours(new Node[] {r4c0, r6c0, r5c1});
                r5c1.addNeighbours(new Node[] {r5c0, r4c1, r6c1, r5c2});
                r5c2.addNeighbours(new Node[] {r5c1, r4c2, r6c2, r5c3});
                r5c3.addNeighbours(new Node[] {r5c2, r4c3, r6c3, r5c4});
                r5c4.addNeighbours(new Node[] {r5c3, r4c4, r6c4, r5c5});
                r5c5.addNeighbours(new Node[] {r5c4, r4c5, r6c5});

                r5c7.addNeighbours(new Node[] {r4c7, r6c7, r5c8});
                r5c8.addNeighbours(new Node[] {r5c7, r4c8, r6c8, r5c9});
                r5c9.addNeighbours(new Node[] {r5c8, r6c9, r5c10});
                r5c10.addNeighbours(new Node[] {r5c9, r4c10});


                r6c0.addNeighbours(new Node[] {r5c0, r7c0, r6c1});
                r6c1.addNeighbours(new Node[] {r6c0, r5c1, r7c1, r6c2});
                r6c2.addNeighbours(new Node[] {r6c1, r5c2, r7c2, r6c3});
                r6c3.addNeighbours(new Node[] {r6c2, r5c3, r7c3, r6c4});
                r6c4.addNeighbours(new Node[] {r6c3, r5c4, r7c4, r6c5});
                r6c5.addNeighbours(new Node[] {r6c4, r5c5, r7c5, r6c6});
                r6c6.addNeighbours(new Node[] {r6c5, r6c7});
                r6c7.addNeighbours(new Node[] {r6c6, r5c7, r7c7, r6c8});
                r6c8.addNeighbours(new Node[] {r6c7, r5c8, r7c8, r6c9});
                r6c9.addNeighbours(new Node[] {r6c8, r5c9, r7c9});

                r6c11.addNeighbours(new Node[] {r7c11});

                r7c0.addNeighbours(new Node[] {r6c0, r8c0, r7c1});
                r7c1.addNeighbours(new Node[] {r7c0, r6c1, r8c1, r7c2});
                r7c2.addNeighbours(new Node[] {r7c1, r6c2, r7c3});
                r7c3.addNeighbours(new Node[] {r7c2, r6c3, r8c3, r7c4});
                r7c4.addNeighbours(new Node[] {r7c3, r6c4, r8c4, r7c5});
                r7c5.addNeighbours(new Node[] {r7c4, r6c5});

                r7c7.addNeighbours(new Node[] {r6c7, r8c7, r7c8});
                r7c8.addNeighbours(new Node[] {r7c7, r6c8, r8c8, r7c9});
                r7c9.addNeighbours(new Node[] {r7c8, r6c9, r8c9, r7c10});
                r7c10.addNeighbours(new Node[] {r7c9, r8c10, r7c11});
                r7c11.addNeighbours(new Node[] {r7c10, r6c11, r8c11});

                r8c0.addNeighbours(new Node[] {r7c0, r9c0, r8c1});
                r8c1.addNeighbours(new Node[] {r8c0, r7c1, r9c1});

                r8c3.addNeighbours(new Node[] {r7c3, r9c3, r8c4});
                r8c4.addNeighbours(new Node[] {r8c3, r7c4, r9c4});

                r8c6.addNeighbours(new Node[] {r9c6, r8c7});
                r8c7.addNeighbours(new Node[] {r8c6, r7c7, r9c7, r8c8});
                r8c8.addNeighbours(new Node[] {r8c7, r7c8, r9c8, r8c9});
                r8c9.addNeighbours(new Node[] {r8c8, r7c9, r9c9, r8c10});
                r8c10.addNeighbours(new Node[] {r8c9, r7c10, r8c11});
                r8c11.addNeighbours(new Node[] {r8c10, r7c11, r9c11});

                r9c0.addNeighbours(new Node[] {r8c0, r10c0, r9c1});
                r9c1.addNeighbours(new Node[] {r9c0, r8c1, r10c1, r9c2});
                r9c2.addNeighbours(new Node[] {r9c1, r10c2, r9c3});
                r9c3.addNeighbours(new Node[] {r9c2, r8c3, r9c4});
                r9c4.addNeighbours(new Node[] {r9c3, r8c4});

                r9c6.addNeighbours(new Node[] {r8c6, r10c6, r9c7});
                r9c7.addNeighbours(new Node[] {r9c6, r8c7, r10c7, r9c8});
                r9c8.addNeighbours(new Node[] {r9c7, r8c8, r10c8, r9c9});
                r9c9.addNeighbours(new Node[] {r9c8, r8c9, r10c9});

                r9c11.addNeighbours(new Node[] {r8c11});

                r10c0.addNeighbours(new Node[] {r9c0, r11c0, r10c1});
                r10c1.addNeighbours(new Node[] {r10c0, r9c1, r11c1, r10c2});
                r10c2.addNeighbours(new Node[] {r10c1, r9c2, r11c2});


                r10c5.addNeighbours(new Node[] {r11c5, r10c6});
                r10c6.addNeighbours(new Node[] {r10c5, r9c6, r10c7});
                r10c7.addNeighbours(new Node[] {r10c6, r9c7, r10c8});
                r10c8.addNeighbours(new Node[] {r10c7, r9c8, r11c8, r10c9});
                r10c9.addNeighbours(new Node[] {r10c8, r9c9, r10c10});
                r10c10.addNeighbours(new Node[] {r10c9, r11c10});


                r11c0.addNeighbours(new Node[] {r10c0, r11c1});
                r11c1.addNeighbours(new Node[] {r11c0, r10c1, r11c2});
                r11c2.addNeighbours(new Node[] {r11c1, r10c2, r11c3});
                r11c3.addNeighbours(new Node[] {r11c2, r11c4});
                r11c4.addNeighbours(new Node[] {r11c3, r11c5});
                r11c5.addNeighbours(new Node[] {r11c4, r10c5});


                r11c8.addNeighbours(new Node[] {r10c8});

                r11c10.addNeighbours(new Node[] {r10c10, r11c11});
                r11c11.addNeighbours(new Node[] {r11c10});

                break;
            default:
                graphPlane = null;
                return graphPlane;
            }

        graphPlane = new Node[][] { {r0c0,r0c1,r0c2,r0c3,r0c4,r0c5,r0c6,r0c7,r0c8,r0c9,r0c10,r0c11},
                                    {r1c0,r1c1,r1c2,r1c3,r1c4,r1c5,r1c6,r1c7,r1c8,r1c9,r1c10,r1c11},
                                    {r2c0,r2c1,r2c2,r2c3,r2c4,r2c5,r2c6,r2c7,r2c8,r2c9,r2c10,r2c11},
                                    {r3c0,r3c1,r3c2,r3c3,r3c4,r3c5,r3c6,r3c7,r3c8,r3c9,r3c10,r3c11},
                                    {r4c0,r4c1,r4c2,r4c3,r4c4,r4c5,r4c6,r4c7,r4c8,r4c9,r4c10,r4c11},
                                    {r5c0,r5c1,r5c2,r5c3,r5c4,r5c5,r5c6,r5c7,r5c8,r5c9,r5c10,r5c11},
                                    {r6c0,r6c1,r6c2,r6c3,r6c4,r6c5,r6c6,r6c7,r6c8,r6c9,r6c10,r6c11},
                                    {r7c0,r7c1,r7c2,r7c3,r7c4,r7c5,r7c6,r7c7,r7c8,r7c9,r7c10,r7c11},
                                    {r8c0,r8c1,r8c2,r8c3,r8c4,r8c5,r8c6,r8c7,r8c8,r8c9,r8c10,r8c11},
                                    {r9c0,r9c1,r9c2,r9c3,r9c4,r9c5,r9c6,r9c7,r9c8,r9c9,r9c10,r9c11},
                                    {r10c0,r10c1,r10c2,r10c3,r10c4,r10c5,r10c6,r10c7,r10c8,r10c9,r10c10,r10c11},
                                    {r11c0,r11c1,r11c2,r11c3,r11c4,r11c5,r11c6,r11c7,r11c8,r11c9,r11c10,r11c11}
        };

        return  graphPlane;
    }

    //Grid encoding

    /*
    0,0    0,1    0,2   0,3

    1,0    1,1    1,2   1,3

    2,0    2,1    2,2   2,3

    3,0    3,1    3,2   3,3
    */

    public void printDirections(List<Node> directions) {
        for (Node node : directions)
            RConsole.println("Coords = " + node.getX() + " " + node.getY());
    }

    /**
     * Class for layout representation
     *
     * Each <code>Node</code> represents a fixed tile of the playground
     * so there should be 12x12 so 144 instances of these Nodes
     * @author Daniel Macario
     * @version 1.0
     */
    public class Node {

        private Coordinate coordinate;
        private ArrayList<Node> neighbours;
        private boolean visited;
        private Node previous;

        public Node(Coordinate coordinate) {
            this.coordinate = coordinate;
            this.neighbours = new ArrayList<Node>();
            this.visited = false;
        }

        public ArrayList<Node> getNeighbours() {
            return neighbours;
        }

        public void setNeighbours(ArrayList<Node> neighbours) {
            this.neighbours = neighbours;
        }

        public void addNeighbour(Node n) {
            this.neighbours.add(n);
        }

        public void addNeighbours(Node[] n) {
            for (Node node : n) {
                neighbours.add(node);
            }
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public boolean getVisited() {
            return this.visited;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        public int getX() {
            return this.coordinate.getX();
        }

        public int getY() {
            return this.coordinate.getY();
        }

        public Node getPrevious() {
            return this.previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

    }
}
