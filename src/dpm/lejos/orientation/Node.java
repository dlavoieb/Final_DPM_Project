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

    /**
     * Initializes a node object specifying a tile on the map
     * for navigation purposes.
     * @param coordinate
     */
    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.neighbours = new ArrayList<Node>();
        this.visited = false;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    /**
     * Specifies the neighbours for a given node on the grid.
     * @param neighbours An ArrayList specifying the neighbouring nodes to
     *                   the current instance.
     */
    public void setNeighbours(ArrayList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Adds the nodes passed as neighbours to current Node
     * instance.
     * @param nodes An array containing the set of neighbours
     *              to the current instance.
     */
    public void addNeighbours(Node[] nodes) {
        for (Node node : nodes) {
            neighbours.add(node);
        }
    }

    /**
     * Adds the passed node to the neighbours arraylist of
     * the current instance.
     * @param n A node that is a neighbour to the current instance.
     */
    public void addNeighbour(Node n) {
        this.neighbours.add(n);
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
