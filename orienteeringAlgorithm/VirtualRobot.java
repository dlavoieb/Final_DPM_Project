

/**
 * Class to track the position and path of the robot. This allows us to compare the movements of the robot to compare with the obstacle position
 * @author Daniel Macario
 */
public class VirtualRobot {

    private int x;
    private int y;
    private Direction dir;
    private final static Object lock = new Object();

    public VirtualRobot (int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    /**
     * Increment the position of the robot by one in the current direction
     */
    public void moveForward() {
        synchronized (lock) {
            if (this.dir == Direction.NORTH) {
                y--;
            } else if (this.dir == Direction.SOUTH) {
                y++;
            } else if (this.dir == Direction.EAST) {
                x++;
            } else {
                x--;
            }
        }
    }

    /**
     * Rotate the robot counter-clockwise
     */
    public void rotate() {
        synchronized (lock) {
            if (this.dir == Direction.NORTH) {
                this.dir = Direction.WEST;
            } else if (this.dir == Direction.WEST) {
                this.dir = Direction.SOUTH;
            } else if (this.dir == Direction.SOUTH) {
                this.dir = Direction.EAST;
            } else if (this.dir == Direction.EAST) {
                this.dir = Direction.NORTH;
            }
        }
    }

    public void rotatecw() {
        synchronized (lock) {
            if (this.dir == Direction.NORTH) {
                this.dir = Direction.EAST;
            } else if (this.dir == Direction.WEST) {
                this.dir = Direction.NORTH;
            } else if (this.dir == Direction.SOUTH) {
                this.dir = Direction.WEST;
            } else if (this.dir == Direction.EAST) {
                this.dir = Direction.SOUTH;
            }
        }
    }

    public void performMotion(Motion m) {
        if (m == Motion.FORWARD) moveForward();
        else if (m == Motion.ROTATE) rotate();
        else rotatecw();
    }

    /**
     * Method determining if the robot is facing a wall
     * @param plane the playground layout
     * @return is there a wall ahead considering the current position and orientation
     */
    public boolean hasWallAhead(Tile[][] plane) {
        synchronized (lock) {
            if (this.dir == Direction.NORTH) {
                return plane[y][x].hasObstacle(Direction.NORTH);
            } else if (this.dir == Direction.SOUTH) {
                return plane[y][x].hasObstacle(Direction.SOUTH);
            } else if (this.dir == Direction.EAST) {
                return plane[y][x].hasObstacle(Direction.EAST);
            } else if (this.dir == Direction.WEST) {
                return plane[y][x].hasObstacle(Direction.WEST);
            }
            return false;
        }
    }


    /**
     * Determines if the robot has a wall to its left
     * @param plane
     * @return
     */
    public boolean hasWallLeft(Tile[][] plane) {
        synchronized (lock) {
            if (this.dir == Direction.NORTH) {
                return plane[y][x].hasObstacle(Direction.WEST);
            } else if (this.dir == Direction.SOUTH) {
                return plane[y][x].hasObstacle(Direction.EAST);
            } else if (this.dir == Direction.EAST) {
                return plane[y][x].hasObstacle(Direction.NORTH);
            } else if (this.dir == Direction.WEST) {
                return plane[y][x].hasObstacle(Direction.SOUTH);
            }
            return false;
        }
    }

    /**
     * Determines if the robot has a walsl to its right
     * @param plane
     * @return
     */
    public boolean hasWallRight(Tile[][] plane) {
        synchronized (lock) {
            if (this.dir == Direction.NORTH) {
                return plane[y][x].hasObstacle(Direction.EAST);
            } else if (this.dir == Direction.SOUTH) {
                return plane[y][x].hasObstacle(Direction.WEST);
            } else if (this.dir == Direction.EAST) {
                return plane[y][x].hasObstacle(Direction.SOUTH);
            } else if (this.dir == Direction.WEST) {
                return plane[y][x].hasObstacle(Direction.NORTH);
            }
            return false;
        }
    }

    /**
     * public setter for x position
     * @param x new x position
     */
    public void setX(int x) {
        synchronized (lock) {
            this.x = x;
        }
    }

    /**
     * public direction setter
     * @param dir new direction
     */
    public void setDir(Direction dir) {
        synchronized (lock) {
            this.dir = dir;
        }
    }

    /**
     * public y position setter
     * @param y new y position
     */
    public void setY(int y) {
        synchronized (lock) {
            this.y = y;
        }
    }

    /**
     * public direction accessor
     * @return current direction
     */
    public Direction getDir() {
        synchronized (lock) {
            return dir;
        }
    }

    /**
     * public x position accessor
     * @return current x position
     */
    public int getX() {
        synchronized (lock) {
            return x;
        }
    }

    /**
     * public y position accessor
     * @return current y position
     */
    public int getY() {
        synchronized (lock) {
            return y;
        }
    }
}