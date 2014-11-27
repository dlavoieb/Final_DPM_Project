package dpm.lejos.orientation;

import java.util.ArrayList;

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
    private boolean isObstacle;
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

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }
}
