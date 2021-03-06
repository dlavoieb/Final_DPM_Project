package dpm.lejos.orientation;

import java.util.ArrayList;

/**
 * Class representing every unit tile on the playground board
 *
 * Each tile has 4 sides and measures 30 cm x 30 cm or 1 in x 1 in
 *
 * @author Daniel Macario
 * @version 2.0
 */
public class Tile {

    private boolean isObstacle;
    private boolean possibleN;
    private boolean possibleS;
    private boolean possibleW;
    private boolean possibleE;
    private boolean obstacleN;
    private boolean obstacleS;
    private boolean obstacleW;
    private boolean obstacleE;
    private ArrayList<Tile> neighbourTiles;
    private boolean visited;
    private Coordinate id;

    /**
     * Default constructor
     *
     * Makes new tile where all sides are clear and tile is not an obstacle
     */
    public Tile() {
        this.isObstacle = false;
        this.possibleN = true;
        this.possibleS = true;
        this.possibleW = true;
        this.possibleE = true;
        this.obstacleN = false;
        this.obstacleS = false;
        this.obstacleW = false;
        this.obstacleE = false;
        this.neighbourTiles = new ArrayList<Tile>();
        this.visited = false;
        this.id = null;
    }

    /**
     * Copy Constructor
     * @param tile the Reference object
     */
    public Tile(Tile tile) {
        this.isObstacle = tile.isObstacle();
        this.possibleN = tile.isPossible(Orienteering.Direction.NORTH);
        this.possibleS = tile.isPossible(Orienteering.Direction.SOUTH);
        this.possibleW = tile.isPossible(Orienteering.Direction.WEST);
        this.possibleE = tile.isPossible(Orienteering.Direction.EAST);
        this.obstacleN = tile.hasObstacle(Orienteering.Direction.NORTH);
        this.obstacleS = tile.hasObstacle(Orienteering.Direction.SOUTH);
        this.obstacleW = tile.hasObstacle(Orienteering.Direction.WEST);
        this.obstacleE = tile.hasObstacle(Orienteering.Direction.EAST);
    }

    /**
     * Add tile to neighbour tiles array
     *
     * @param tiles the tile array
     */
    public void addNeighbourTiles(Tile[] tiles) {
        for (Tile tile : tiles) {
            this.neighbourTiles.add(tile);
        }
    }

    public ArrayList<Tile> getNeighbours() {
        return this.neighbourTiles;
    }


    /**
     * Get if there is an obstacle in certain direction
     * @param dir the direction to poll
     * @return presence of an obstacle in the polled direction
     */
    public boolean hasObstacle(Orienteering.Direction dir) {
        if (dir == Orienteering.Direction.NORTH) {
            return this.obstacleN;
        } else if (dir == Orienteering.Direction.WEST) {
            return this.obstacleW;
        } else if (dir == Orienteering.Direction.EAST) {
            return this.obstacleE;
        } else {
            return this.obstacleS;
        }
    }

    /**
     * Set the presence of an obstacle in a direction to false, i.e. clear
     * @param dir the direction to change
     */
    
    public void setPossibilityToFalse(Orienteering.Direction dir) {
        if (dir == Orienteering.Direction.NORTH) {
            this.possibleN = false;
        } else if (dir == Orienteering.Direction.WEST) {
            this.possibleW = false;
        } else if (dir == Orienteering.Direction.EAST) {
            this.possibleE = false;
        } else {
            this.possibleS = false;
        }
    }

    /**
     * General setter for the presence of an obstacle
     * @param dir the direction to change
     * @param value the desired value to set
     */
    public void setObstacle(Orienteering.Direction dir, boolean value) {
        if (dir == Orienteering.Direction.NORTH) {
            this.obstacleN = value;
        } else if (dir == Orienteering.Direction.WEST) {
            this.obstacleW = value;
        } else if (dir == Orienteering.Direction.EAST) {
            this.obstacleE = value;
        } else {
            this.obstacleS = value;
        }
    }

    /**
     * accessor for the presence of an obstacle in a given direction
     * @param dir the direction to poll
     * @return the existence of an obstacle in that direction
     */
    public boolean isPossible(Orienteering.Direction dir) {
        if (dir == Orienteering.Direction.NORTH) {
            return this.possibleN;
        } else if (dir == Orienteering.Direction.WEST) {
            return this.possibleW;
        } else if (dir == Orienteering.Direction.EAST) {
            return this.possibleE;
        } else {
            return this.possibleS;
        }
    }

    /**
     * Closes all possible starting points for the tile
     */    
    public void closeAllPossibilities() {
        this.possibleN = false;
        this.possibleS = false;
        this.possibleW = false;
        this.possibleE = false;
    }

    /**
     * Set if this tile is an obstacle
     * @param isObstacle the tile is an obstacle?
     */
    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public Orienteering.Direction setPossibilityToTrue(int x, int y, int tileX, int tileY) {

        if (tileX == x) {
            if (y > tileY) {
                this.possibleE = true;
                return Orienteering.Direction.EAST;
            } else {
                this.possibleW = true;
                return Orienteering.Direction.WEST;
            }
        } else {
            if (x > tileX) {
                this.possibleS = true;
                return Orienteering.Direction.SOUTH;
            } else {
                this.possibleN = true;
                return Orienteering.Direction.NORTH;
            }
        }
    }

    public void generateMapFromGraph(ArrayList<Coordinate> coordinates, int tileX, int tileY) {

        ArrayList<Orienteering.Direction> directionsChecked = new ArrayList<Orienteering.Direction>();

        for (Coordinate coordinate: coordinates) {
            directionsChecked.add(setPossibilityToTrue(coordinate.getX(), coordinate.getY(), tileX, tileY));
        }

        if (!directionsChecked.contains(Orienteering.Direction.EAST)) {
            this.setObstacle(Orienteering.Direction.EAST, true);
        }

        if (!directionsChecked.contains(Orienteering.Direction.WEST)) {
            this.setObstacle(Orienteering.Direction.WEST, true);
        }

        if (!directionsChecked.contains(Orienteering.Direction.NORTH)) {
            this.setObstacle(Orienteering.Direction.NORTH, true);
        }

        if (!directionsChecked.contains(Orienteering.Direction.SOUTH)) {
            this.setObstacle(Orienteering.Direction.SOUTH, true);
        }

    }

    /**
     * accessor for the state of the current tile
     * @return is the tile an obstacle
     */
    public boolean isObstacle() {
        return isObstacle;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public Coordinate getId() {
        return id;
    }

    public void setId(Coordinate id) {
        this.id = id;
    }
}
