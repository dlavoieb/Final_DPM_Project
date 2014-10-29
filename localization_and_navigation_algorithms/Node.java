import java.util.ArrayList;

/**
 * Created by danielmacario on 14-10-28.
 */
public class Node {

    private Coordinate coordinate;
    private ArrayList<Node> neighbours;


    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.neighbours = new ArrayList<Node>();
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

}
