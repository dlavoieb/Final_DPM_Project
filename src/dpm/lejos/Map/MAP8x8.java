package dpm.lejos.Map;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Node;

/**
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class MAP8x8 {

    private static Node r0c0 = new Node(new Coordinate(0,0));
    private static Node r0c1 = new Node(new Coordinate(0,1));
    private static Node r0c2 = new Node(new Coordinate(0,2));
    private static Node r0c3 = new Node(new Coordinate(0,3));
    private static Node r0c4 = new Node(new Coordinate(0,4));
    private static Node r0c5 = new Node(new Coordinate(0,5));
    private static Node r0c6 = new Node(new Coordinate(0,6));
    private static Node r0c7 = new Node(new Coordinate(0,7));

    private static Node r1c0 = new Node(new Coordinate(1,0));
    private static Node r1c1 = new Node(new Coordinate(1,1));
    private static Node r1c2 = new Node(new Coordinate(1,2));
    private static Node r1c3 = new Node(new Coordinate(1,3));
    private static Node r1c4 = new Node(new Coordinate(1,4));
    private static Node r1c5 = new Node(new Coordinate(1,5));
    private static Node r1c6 = new Node(new Coordinate(1,6));
    private static Node r1c7 = new Node(new Coordinate(1,7));

    private static Node r2c0 = new Node(new Coordinate(2,0));
    private static Node r2c1 = new Node(new Coordinate(2,1));
    private static Node r2c2 = new Node(new Coordinate(2,2));
    private static Node r2c3 = new Node(new Coordinate(2,3));
    private static Node r2c4 = new Node(new Coordinate(2,4));
    private static Node r2c5 = new Node(new Coordinate(2,5));
    private static Node r2c6 = new Node(new Coordinate(2,6));
    private static Node r2c7 = new Node(new Coordinate(2,7));

    private static Node r3c0 = new Node(new Coordinate(3,0));
    private static Node r3c1 = new Node(new Coordinate(3,1));
    private static Node r3c2 = new Node(new Coordinate(3,2));
    private static Node r3c3 = new Node(new Coordinate(3,3));
    private static Node r3c4 = new Node(new Coordinate(3,4));
    private static Node r3c5 = new Node(new Coordinate(3,5));
    private static Node r3c6 = new Node(new Coordinate(3,6));
    private static Node r3c7 = new Node(new Coordinate(3,7));

    private static Node r4c0 = new Node(new Coordinate(4,0));
    private static Node r4c1 = new Node(new Coordinate(4,1));
    private static Node r4c2 = new Node(new Coordinate(4,2));
    private static Node r4c3 = new Node(new Coordinate(4,3));
    private static Node r4c4 = new Node(new Coordinate(4,4));
    private static Node r4c5 = new Node(new Coordinate(4,5));
    private static Node r4c6 = new Node(new Coordinate(4,6));
    private static Node r4c7 = new Node(new Coordinate(4,7));

    private static Node r5c0 = new Node(new Coordinate(5,0));
    private static Node r5c1 = new Node(new Coordinate(5,1));
    private static Node r5c2 = new Node(new Coordinate(5,2));
    private static Node r5c3 = new Node(new Coordinate(5,3));
    private static Node r5c4 = new Node(new Coordinate(5,4));
    private static Node r5c5 = new Node(new Coordinate(5,5));
    private static Node r5c6 = new Node(new Coordinate(5,6));
    private static Node r5c7 = new Node(new Coordinate(5,7));

    private static Node r6c0 = new Node(new Coordinate(6,0));
    private static Node r6c1 = new Node(new Coordinate(6,1));
    private static Node r6c2 = new Node(new Coordinate(6,2));
    private static Node r6c3 = new Node(new Coordinate(6,3));
    private static Node r6c4 = new Node(new Coordinate(6,4));
    private static Node r6c5 = new Node(new Coordinate(6,5));
    private static Node r6c6 = new Node(new Coordinate(6,6));
    private static Node r6c7 = new Node(new Coordinate(6,7));

    private static Node r7c0 = new Node(new Coordinate(7,0));
    private static Node r7c1 = new Node(new Coordinate(7,1));
    private static Node r7c2 = new Node(new Coordinate(7,2));
    private static Node r7c3 = new Node(new Coordinate(7,3));
    private static Node r7c4 = new Node(new Coordinate(7,4));
    private static Node r7c5 = new Node(new Coordinate(7,5));
    private static Node r7c6 = new Node(new Coordinate(7,6));
    private static Node r7c7 = new Node(new Coordinate(7,7));

    public static Node[][] createMap1(){

        r0c0.addNeighbours(new Node[] {r1c0});
        r0c3.addNeighbours(new Node[] {r1c3, r0c4});
        r0c4.addNeighbours(new Node[] {r0c3, r1c4, r0c5});
        r0c5.addNeighbours(new Node[] {r0c4, r1c5, r0c6});
        r0c6.addNeighbours(new Node[] {r0c5, r1c6, r0c7});
        r0c7.addNeighbours(new Node[] {r0c6});
        r1c0.addNeighbours(new Node[] {r0c0, r1c1});
        r1c1.addNeighbours(new Node[] {r1c0, r2c1});
        r1c3.addNeighbours(new Node[] {r0c3, r1c4});
        r1c4.addNeighbours(new Node[] {r1c3, r0c4, r2c4, r1c5});
        r1c5.addNeighbours(new Node[] {r1c4, r0c5, r2c5, r1c6});
        r1c6.addNeighbours(new Node[] {r1c5, r0c6});
        r2c1.addNeighbours(new Node[] {r1c1, r3c1, r2c2});
        r2c2.addNeighbours(new Node[] {r2c1});
        r2c4.addNeighbours(new Node[] {r1c4, r3c4, r2c5});
        r2c5.addNeighbours(new Node[] {r2c4, r1c5, r3c5});
        r2c7.addNeighbours(new Node[] {r3c7});
        r3c0.addNeighbours(new Node[] {r4c0, r3c1});
        r3c1.addNeighbours(new Node[] {r3c0, r2c1, r4c1});
        r3c3.addNeighbours(new Node[] {r4c3, r3c4});
        r3c4.addNeighbours(new Node[] {r3c3, r2c4, r3c5});
        r3c5.addNeighbours(new Node[] {r3c4, r2c5, r4c5, r3c6});
        r3c6.addNeighbours(new Node[] {r3c5, r4c6, r3c7});
        r3c7.addNeighbours(new Node[] {r3c6, r2c7});
        r4c0.addNeighbours(new Node[] {r3c0, r5c0, r4c1});
        r4c1.addNeighbours(new Node[] {r4c0, r3c1, r5c1, r4c2});
        r4c2.addNeighbours(new Node[] {r4c1, r5c2, r4c3});
        r4c3.addNeighbours(new Node[] {r4c2, r3c3, r5c3});
        r4c5.addNeighbours(new Node[] {r3c5, r5c5, r4c6});
        r4c6.addNeighbours(new Node[] {r4c5, r3c6});
        r5c0.addNeighbours(new Node[] {r4c0, /*r6c0,*/ r5c1});
        r5c1.addNeighbours(new Node[] {r5c0, r4c1, /*r6c1,*/ r5c2});
        r5c2.addNeighbours(new Node[] {r5c1, r4c2, r6c2, r5c3});
        r5c3.addNeighbours(new Node[] {r5c2, r4c3, r6c3});
        r5c5.addNeighbours(new Node[] {r4c5, r6c5});
//        r6c0.addNeighbours(new Node[] {r5c0, r7c0, r6c1});
//        r6c1.addNeighbours(new Node[] {r6c0, r5c1, r7c1, r6c2});
        r6c2.addNeighbours(new Node[] {/*r6c1,*/ r5c2, r7c2, r6c3});
        r6c3.addNeighbours(new Node[] {r6c2, r5c3, r7c3});
        r6c5.addNeighbours(new Node[] {r5c5, r7c5, r6c6});
        r6c6.addNeighbours(new Node[] {r6c5, r7c6, r6c7});
        r6c7.addNeighbours(new Node[] {r6c6});
//        r7c0.addNeighbours(new Node[] {r6c0, r7c1});
//        r7c1.addNeighbours(new Node[] {r7c0, r6c1, r7c2});
        r7c2.addNeighbours(new Node[] {/*r7c1*/ r6c2, r7c3});
        r7c3.addNeighbours(new Node[] {r7c2, r6c3, r7c4});
        r7c4.addNeighbours(new Node[] {r7c3, r7c5});
        r7c5.addNeighbours(new Node[] {r7c4, r6c5, r7c6});
        r7c6.addNeighbours(new Node[] {r7c5, r6c6});

        return new Node[][] {{r0c0,r0c1,r0c2,r0c3,r0c4,r0c5,r0c6,r0c7},
                {r1c0,r1c1,r1c2,r1c3,r1c4,r1c5,r1c6,r1c7},
                {r2c0,r2c1,r2c2,r2c3,r2c4,r2c5,r2c6,r2c7},
                {r3c0,r3c1,r3c2,r3c3,r3c4,r3c5,r3c6,r3c7},
                {r4c0,r4c1,r4c2,r4c3,r4c4,r4c5,r4c6,r4c7},
                {r5c0,r5c1,r5c2,r5c3,r5c4,r5c5,r5c6,r5c7},
                {r6c0,r6c1,r6c2,r6c3,r6c4,r6c5,r6c6,r6c7},
                {r7c0,r7c1,r7c2,r7c3,r7c4,r7c5,r7c6,r7c7}};
    }

    public static Node[][] createMap2(){
        r0c0.addNeighbours(new Node[] {r1c0, r0c1});
        r0c1.addNeighbours(new Node[] {r0c0, r0c2});
        r0c2.addNeighbours(new Node[] {r0c1, r1c2});
        r0c5.addNeighbours(new Node[] {r1c5, r0c6});
        r0c6.addNeighbours(new Node[] {r0c5, r1c6});
        r1c0.addNeighbours(new Node[] {r0c0});
        r1c2.addNeighbours(new Node[] {r0c2, r2c2, r1c3});
        r1c3.addNeighbours(new Node[] {r1c2, r2c3});
        r1c5.addNeighbours(new Node[] {r0c5, r2c5, r1c6});
        r1c6.addNeighbours(new Node[] {r1c5, r0c6, r2c6});
        r2c1.addNeighbours(new Node[] {r3c1, r2c2});
        r2c2.addNeighbours(new Node[] {r2c1, r1c2, r2c3});
        r2c3.addNeighbours(new Node[] {r2c2, r1c3, r3c3, r2c4});
        r2c4.addNeighbours(new Node[] {r2c3, r2c5});
        r2c5.addNeighbours(new Node[] {r2c4, r1c5, r3c5, r2c6});
        r2c6.addNeighbours(new Node[] {r2c5, r1c6, r3c6, r2c7});
        r2c7.addNeighbours(new Node[] {r2c6, r3c7});
        r3c0.addNeighbours(new Node[] {r4c0, r3c1});
        r3c1.addNeighbours(new Node[] {r3c0, r2c1, r4c1});
        r3c3.addNeighbours(new Node[] {r2c3, r4c3});
        r3c5.addNeighbours(new Node[] {r2c5, r4c5, r3c6});
        r3c6.addNeighbours(new Node[] {r3c5, r2c6, r4c6, r3c7});
        r3c7.addNeighbours(new Node[] {r3c6, r2c7, r4c7});
        r4c0.addNeighbours(new Node[] {r3c0, r5c0, r4c1});
        r4c1.addNeighbours(new Node[] {r4c0, r3c1, r5c1});
        r4c3.addNeighbours(new Node[] {r3c3, r5c3, r4c4});
        r4c4.addNeighbours(new Node[] {r4c3, r5c4, r4c5});
        r4c5.addNeighbours(new Node[] {r4c4, r3c5, r5c5, r4c6});
        r4c6.addNeighbours(new Node[] {r4c5, r3c6, r5c6, r4c7});
        r4c7.addNeighbours(new Node[] {r4c6, r3c7, r5c7});
        r5c0.addNeighbours(new Node[] {r4c0, /*r6c0,*/ r5c1});
        r5c1.addNeighbours(new Node[] {r5c0, r4c1, /*r6c1,*/ r5c2});
        r5c2.addNeighbours(new Node[] {r5c1, r6c2, r5c3});
        r5c3.addNeighbours(new Node[] {r5c2, r4c3, r5c4});
        r5c4.addNeighbours(new Node[] {r5c3, r4c4, r6c4, r5c5});
        r5c5.addNeighbours(new Node[] {r5c4, r4c5, r6c5, r5c6});
        r5c6.addNeighbours(new Node[] {r5c5, r4c6, r6c6, r5c7});
        r5c7.addNeighbours(new Node[] {r5c6, r4c7, r6c7});
        //r6c0.addNeighbours(new Node[] {r5c0, r7c0, r6c1});
        //r6c1.addNeighbours(new Node[] {r6c0, r5c1, r7c1, r6c2});
        r6c2.addNeighbours(new Node[] {r6c1, r5c2});
        r6c4.addNeighbours(new Node[] {r5c4, r7c4, r6c5});
        r6c5.addNeighbours(new Node[] {r6c4, r5c5, r6c6});
        r6c6.addNeighbours(new Node[] {r6c5, r5c6, r7c6, r6c7});
        r6c7.addNeighbours(new Node[] {r6c6, r5c7});
        //r7c0.addNeighbours(new Node[] {r6c0, r7c1});
        //r7c1.addNeighbours(new Node[] {r7c0, r6c1});
        r7c3.addNeighbours(new Node[] {r7c4});
        r7c4.addNeighbours(new Node[] {r7c3, r6c4});
        r7c6.addNeighbours(new Node[] {r6c6});

        return new Node[][] {{r0c0,r0c1,r0c2,r0c3,r0c4,r0c5,r0c6,r0c7},
                {r1c0,r1c1,r1c2,r1c3,r1c4,r1c5,r1c6,r1c7},
                {r2c0,r2c1,r2c2,r2c3,r2c4,r2c5,r2c6,r2c7},
                {r3c0,r3c1,r3c2,r3c3,r3c4,r3c5,r3c6,r3c7},
                {r4c0,r4c1,r4c2,r4c3,r4c4,r4c5,r4c6,r4c7},
                {r5c0,r5c1,r5c2,r5c3,r5c4,r5c5,r5c6,r5c7},
                {r6c0,r6c1,r6c2,r6c3,r6c4,r6c5,r6c6,r6c7},
                {r7c0,r7c1,r7c2,r7c3,r7c4,r7c5,r7c6,r7c7}};
    }

    public static Node[][] createMap3(){
        r0c1.addNeighbours(new Node[] {r1c1, r0c2});
        r0c2.addNeighbours(new Node[] {r0c1, r0c3});
        r0c3.addNeighbours(new Node[] {r0c2});
        r0c5.addNeighbours(new Node[] {r1c5, r0c6});
        r0c6.addNeighbours(new Node[] {r0c5, r1c6, r0c7});
        r0c7.addNeighbours(new Node[] {r0c6});
        r1c0.addNeighbours(new Node[] {r2c0, r1c1});
        r1c1.addNeighbours(new Node[] {r1c0, r0c1, r2c1});
        r1c4.addNeighbours(new Node[] {r2c4, r1c5});
        r1c5.addNeighbours(new Node[] {r1c4, r0c5, r1c6});
        r1c6.addNeighbours(new Node[] {r1c5, r0c6, r2c6});
        r2c0.addNeighbours(new Node[] {r1c0, r3c0, r2c1});
        r2c1.addNeighbours(new Node[] {r2c0, r1c1, r3c1, r2c2});
        r2c2.addNeighbours(new Node[] {r2c1, r3c2, r2c3});
        r2c3.addNeighbours(new Node[] {r2c2, r2c4});
        r2c4.addNeighbours(new Node[] {r2c3, r1c4, r3c4});
        r2c6.addNeighbours(new Node[] {r1c6, r2c7});
        r2c7.addNeighbours(new Node[] {r2c6});
        r3c0.addNeighbours(new Node[] {r2c0, r4c0, r3c1});
        r3c1.addNeighbours(new Node[] {r3c0, r2c1, r4c1, r3c2});
        r3c2.addNeighbours(new Node[] {r3c1, r2c2});
        r3c4.addNeighbours(new Node[] {r2c4, r4c4, r3c5});
        r3c5.addNeighbours(new Node[] {r3c4, r4c5});
        r4c0.addNeighbours(new Node[] {r3c0, r5c0, r4c1});
        r4c1.addNeighbours(new Node[] {r4c0, r3c1, r5c1});
        r4c4.addNeighbours(new Node[] {r3c4, r5c4, r4c5});
        r4c5.addNeighbours(new Node[] {r4c4, r3c5, r5c5, r4c6});
        r4c6.addNeighbours(new Node[] {r4c5, r5c6, r4c7});
        r4c7.addNeighbours(new Node[] {r4c6, r5c7});
        r5c0.addNeighbours(new Node[] {r4c0, /*r6c0,*/ r5c1});
        r5c1.addNeighbours(new Node[] {r5c0, r4c1, /*r6c1,*/ r5c2});
        r5c2.addNeighbours(new Node[] {r5c1, r6c2});
        r5c4.addNeighbours(new Node[] {r4c4, r6c4, r5c5});
        r5c5.addNeighbours(new Node[] {r5c4, r4c5, r6c5, r5c6});
        r5c6.addNeighbours(new Node[] {r5c5, r4c6, r6c6, r5c7});
        r5c7.addNeighbours(new Node[] {r5c6, r4c7, r6c7});
        //r6c0.addNeighbours(new Node[] {r5c0, r7c0, r6c1});
        //r6c1.addNeighbours(new Node[] {r6c0, r5c1, r7c1, r6c2});
        r6c2.addNeighbours(new Node[] {r6c1, r5c2, r7c2, r6c3});
        r6c3.addNeighbours(new Node[] {r6c2, r7c3, r6c4});
        r6c4.addNeighbours(new Node[] {r6c3, r5c4, r6c5});
        r6c5.addNeighbours(new Node[] {r6c4, r5c5, r6c6});
        r6c6.addNeighbours(new Node[] {r6c5, r5c6, r7c6, r6c7});
        r6c7.addNeighbours(new Node[] {r6c6, r5c7});
        //r7c0.addNeighbours(new Node[] {r6c0, r7c1});
        //r7c1.addNeighbours(new Node[] {r7c0, r6c1, r7c2});
        r7c2.addNeighbours(new Node[] {/*r7c1,*/ r6c2, r7c3});
        r7c3.addNeighbours(new Node[] {r7c2, r6c3});
        r7c6.addNeighbours(new Node[] {r6c6});


        return new Node[][] {{r0c0,r0c1,r0c2,r0c3,r0c4,r0c5,r0c6,r0c7},
                             {r1c0,r1c1,r1c2,r1c3,r1c4,r1c5,r1c6,r1c7},
                             {r2c0,r2c1,r2c2,r2c3,r2c4,r2c5,r2c6,r2c7},
                             {r3c0,r3c1,r3c2,r3c3,r3c4,r3c5,r3c6,r3c7},
                             {r4c0,r4c1,r4c2,r4c3,r4c4,r4c5,r4c6,r4c7},
                             {r5c0,r5c1,r5c2,r5c3,r5c4,r5c5,r5c6,r5c7},
                             {r6c0,r6c1,r6c2,r6c3,r6c4,r6c5,r6c6,r6c7},
                             {r7c0,r7c1,r7c2,r7c3,r7c4,r7c5,r7c6,r7c7}};
    }
}
