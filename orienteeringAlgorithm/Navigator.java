import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Hashtable;

/**
 * Created by danielmacario on 14-10-26.
 */
public class Navigator {

    private Orienteering or;
    private Tile[][] plane;
    private Tile tileA;
    private Tile tileB;

    public Navigator() {
        Orienteering or = new Orienteering();
        plane = or.getPlane();
        generateGraph();
        tileA = plane[0][2];
        tileB = plane[0][3];
        getDirections(tileA, tileB);
    }

    public void generateGraph() {
        plane[0][1].addNeighbourTiles(new Tile[]{plane[1][1], plane[0][2]});
        plane[0][1].setId(new Coordinate(0,1));
        plane[0][2].addNeighbourTiles(new Tile[]{plane[0][1], plane[0][3]});
        plane[0][2].setId(new Coordinate(0,2));
        plane[0][3].addNeighbourTiles(new Tile[]{plane[0][2]});
        plane[0][3].setId(new Coordinate(0,3));
        plane[1][0].addNeighbourTiles(new Tile[]{plane[1][1], plane[2][0]});
        plane[1][0].setId(new Coordinate(1,0));
        plane[1][1].addNeighbourTiles(new Tile[]{plane[0][1], plane[1][0], plane[2][1]});
        plane[1][1].setId(new Coordinate(1,1));
        plane[2][0].addNeighbourTiles(new Tile[]{plane[2][1], plane[1][0], plane[3][0]});
        plane[2][0].setId(new Coordinate(2,0));
        plane[2][1].addNeighbourTiles(new Tile[]{plane[1][1], plane[2][0], plane[2][2]});
        plane[2][1].setId(new Coordinate(2,1));
        plane[2][2].addNeighbourTiles(new Tile[]{plane[2][1], plane[2][3], plane[3][2]});
        plane[2][2].setId(new Coordinate(2,2));
        plane[2][3].addNeighbourTiles(new Tile[]{plane[3][3], plane[2][2]});
        plane[2][3].setId(new Coordinate(2,3));
        plane[3][0].addNeighbourTiles(new Tile[]{plane[2][0]});
        plane[3][0].setId(new Coordinate(3,0));
        plane[3][2].addNeighbourTiles(new Tile[]{plane[2][2], plane[3][3]});
        plane[3][2].setId(new Coordinate(3,2));
        plane[3][3].addNeighbourTiles(new Tile[]{plane[3][2], plane[2][3]});
        plane[3][3].setId(new Coordinate(3,3));
    }

    /**
     * Build a tree doing BFS which will be used to determine the shortest
     * path between two tiles.
     * @param startingTile
     * @param destinationTile
     */
    public ArrayList<Tile> getDirections(Tile startingTile, Tile destinationTile) {
        Hashtable<Tile, Boolean> visited = new Hashtable<Tile, Boolean>();
        Hashtable<Tile, Tile> prev = new Hashtable<Tile, Tile>();
        ArrayList<Tile> directions = new ArrayList<Tile>();
        ArrayList<Tile> q = new ArrayList<Tile>();

        Tile current = startingTile;
        q.add(current);
        visited.put(current, true);
        int counter = 0;
        while(!q.isEmpty()){
            System.out.println("Iter num = " + counter);
            counter++;
            //get the first element from the queue
            current = q.remove(0);
            if (current.equals(destinationTile)){
                System.out.println("We broke");
                break;
            }else{
                for(Tile tile : current.getNeighbours()){
                    if(!visited.contains(tile)){
                        q.add(tile);
                        visited.put(tile, true);
                        prev.put(tile, current);
                    }
                }
            }
        }
        if (!current.equals(destinationTile)){
            System.out.println("can't reach destination");
        }
        System.out.println("Size of prev = " + prev.size());
        for(Tile node = destinationTile; node != null; node = prev.get(node)) {
            System.out.println("Coords = " + node.getId().getX() + " " + node.getId().getY());
            //directions.add(node);
        }
        Collections.reverse(directions);
        return directions;
    }

    public void printDirections(ArrayList<Tile> directions) {
        for (Tile dir : directions)
            System.out.println("Coords = " + dir.getId().getX() + " " + dir.getId().getY());
    }

}
