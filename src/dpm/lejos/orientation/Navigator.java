package dpm.lejos.orientation;

import dpm.lejos.project.Navigation;
import dpm.lejos.orientation.Orienteering.Direction;
import dpm.lejos.project.Robot;
import lejos.nxt.comm.RConsole;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Daniel Macario
 * @version 1.0
 */
public class Navigator {

    private Robot robot;
    private Navigation navigation;
    private Node[][] graphPlane;

    public Navigator(Robot robot, Navigation navigation) {
        this.robot = robot;
        this.navigation = navigation;
        this.graphPlane = generateGraph();
    }

    //TODO: encode the 12x12 grid as a graph. We receive the data by bluetooth, then to traverse all we need to is
    //TODO: set certains nodes to be obstacles

    /**
     * method encoding the course layout as an array of nodes
     * @return the representation of the course layout
     */
    public Node[][] generateGraph() {

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

        Node[][] graphPlane = {{r0c0, r0c1, r0c2, r0c3},
                               {r1c0, r1c1, r1c2, r1c3},
                               {r2c0, r2c1, r2c2, r2c3},
                               {r3c0, r3c1, r3c2, r3c3}};

        return graphPlane;

    }

    public void navigate(Coordinate endingCoordinate){

        Coordinate startingCoordinate = robot.getPositionOnGrid();
        RConsole.println("start x = " + Integer.toString(startingCoordinate.getX()) + " y = " + Integer.toString(startingCoordinate.getY()));
        //TODO: make sure that this is not BACKWARDS!!!!
        Node current = graphPlane[startingCoordinate.getX()][startingCoordinate.getY()];
        Node finish = graphPlane[endingCoordinate.getX()][startingCoordinate.getY()];

        ArrayList<Node> reverseDirections = new ArrayList<Node>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(current);
        current.setVisited(true);

        while(!queue.isEmpty()){
            current = queue.remove(0);
            if (current.equals(finish)){
                break;
            }else{
                for(Node node : current.getNeighbours()){
                    if(!node.getVisited()){
                        queue.add(node);
                        node.setVisited(true);
                        node.setPrevious(current);
                    }
                }
            }
        }

        for(Node node = finish; node != null; node = node.getPrevious()) {
            reverseDirections.add(0, node);
        }
        
        printDirections(reverseDirections);
        travelTo(reverseDirections);
    }

    //Grid encoding

    /*
    0,0    0,1    0,2   0,3

    1,0    1,1    1,2   1,3

    2,0    2,1    2,2   2,3

    3,0    3,1    3,2   3,3
    */

    private void travelTo(ArrayList<Node> directions) {

        Coordinate currentPosition = directions.remove(0).getCoordinate();
        Direction currentDirection = robot.getDirection();
        Coordinate nextPosition;
        for (Node node : directions) {
            nextPosition = node.getCoordinate();

            //Note that the robot can only move in one axis at a time
            //So the new coordinate will either have a new X or a new Y
            //TODO: discuss if we should adapt the coordinate system

            if (currentPosition.getX() != nextPosition.getX()) {
                //Robot is moving in the y-axis
                if (currentPosition.getX() > nextPosition.getX()) {
                    //Robot is moving north
                    navigation.rotateToDirection(Direction.NORTH);
                    navigation.moveForward();
                } else if (currentPosition.getX() < nextPosition.getX()) {
                    //robot is moving south
                    navigation.rotateToDirection(Direction.SOUTH);
                    navigation.moveForward();
                }
            } else if (currentPosition.getY() != nextPosition.getY()) {
                //Robot is moving in the x-axis
                if (currentPosition.getY() > nextPosition.getY()) {
                    //Robot is moving west
                    navigation.rotateToDirection(Direction.WEST);
                    navigation.moveForward();
                } else if (currentPosition.getY() < nextPosition.getY()) {
                    //robot is moving east
                    navigation.moveForward();
                }
            }
        }

        //set position of the robot to the last tile visited
        //TODO: verify that this actually works.
        robot.setPositionOnGrid(directions.get(directions.size() - 1).getCoordinate());
    }

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
