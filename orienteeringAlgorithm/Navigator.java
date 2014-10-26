import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by danielmacario on 14-10-26.
 */
public class Navigator {

    private Orienteering or;
    private Tile[][] plane;

    public Navigator() {
        Orienteering or = new Orienteering();
        plane = or.getPlane();
    }

    public void generateGraph() {
        plane[0][1].addNeighbourTiles(new Tile[]{plane[1][1], plane[0][2]});
        plane[0][2].addNeighbourTiles(new Tile[]{plane[0][1], plane[0][3]});
        plane[0][3].addNeighbourTiles(new Tile[]{plane[0][2]});
        plane[1][0].addNeighbourTiles(new Tile[]{plane[1][1], plane[2][0]});
        plane[1][1].addNeighbourTiles(new Tile[]{plane[0][1], plane[1][0], plane[2][1]});
        plane[2][0].addNeighbourTiles(new Tile[]{plane[2][1], plane[1][0], plane[3][0]});
        plane[2][1].addNeighbourTiles(new Tile[]{plane[1][1], plane[2][0], plane[2][2]});
        plane[2][2].addNeighbourTiles(new Tile[]{plane[2][1], plane[2][3], plane[3][2]});
        plane[2][3].addNeighbourTiles(new Tile[]{plane[3][3], plane[2][2]});
        plane[3][0].addNeighbourTiles(new Tile[]{plane[2][0]});
        plane[3][2].addNeighbourTiles(new Tile[]{plane[2][2], plane[3][3]});
        plane[3][3].addNeighbourTiles(new Tile[]{plane[3][2], plane[2][3]});
    }

    /**
     * Build a tree doing BFS which will be used to determine the shortest
     * path between two tiles.
     * @param startingTile
     * @param destinationTile
     */
    public void generateTree(Tile startingTile, Tile destinationTile) {
        ArrayList<Tile> unexploredTiles = new ArrayList<Tile>();

        unexploredTiles.add(startingTile);

        Tile t;
        while(!unexploredTiles.isEmpty()) {

            t = unexploredTiles.remove(0);
            t.setVisited(true);
            if (t.equals(destinationTile)) return;

            for (Tile neighbour : t.getNeighbours()) {
                if (!neighbour.getVisited()) {
                    unexploredTiles.add(neighbour);

                    //set logic for building tree...
                }
            }

        }
    }

}
