import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

/**
 * Created by danielmacario on 14-10-26.
 */
public class Navigator {

    private Orienteering or;
    private Tile[][] plane;
    private Node graphRoot;
    private Tile tileA;
    private Tile tileB;

    public Navigator() {
        Orienteering or = new Orienteering();
        plane = or.getPlane();
        Node[] endNodes = generateGraph();
        getDirectionsTest(endNodes[0], endNodes[1]);

    }


    //Grid encoding

    /*
    0,0    0,1    0,2   0,3

    1,0    1,1    1,2   1,3

    2,0    2,1    2,2   2,3

    3,0    3,1    3,2   3,3
    */


    //TODO: encode the 12x12 grid as a graph. We receive the data by bluetooth, then to traverse all we need to is
    //TODO: set certains nodes to be obstacles

    public Node[] generateGraph() {


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

        return new Node[] {r3c3, r0c3};

    }

    public List getDirectionsTest(Node start, Node finish){

        Map<Node, Boolean> vis = new HashMap<Node, Boolean>();
        Map<Node, Node> prev = new HashMap<Node, Node>();

        List directions = new LinkedList();
        Queue<Node> q = new LinkedList<Node>();
        Node current = start;
        q.add(current);
        vis.put(current, true);
        while(!q.isEmpty()){
            current = q.remove();
            if (current.equals(finish)){
                break;
            }else{
                for(Node node : current.getNeighbours()){
                    if(!vis.containsKey(node)){
                        q.add(node);
                        vis.put(node, true);
                        prev.put(node, current);
                    }
                }
            }
        }
        if (!current.equals(finish)){
            System.out.println("can't reach destination");
        }
        for(Node node = finish; node != null; node = prev.get(node)) {
            directions.add(node);
        }
        Collections.reverse(directions);
        printDirections(directions);
        return directions;
    }

    public void printDirections(List<Node> directions) {
        for (Node node : directions)
            System.out.println("Coords = " + node.getX() + " " + node.getY());
    }

}