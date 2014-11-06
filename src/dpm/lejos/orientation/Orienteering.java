package dpm.lejos.orientation;

import dpm.lejos.project.Navigation;
import dpm.lejos.project.Robot;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Orientation and navigation methods
 * for initial position determination
 *
 * @author Daniel Macario David Lavoie-Boutin
 * @version 1.0
 */
public class Orienteering {

    private Tile[][] plane;
    private Direction startingDir;
    private Direction endingDir;
    private static int DISTANCE_THRESHOLD = 30;
    private Navigation navigation;
    private Robot robot;

    public Orienteering() {
        this.plane = createPlane();
        this.navigation = new Navigation(new Robot());
    }

    public Orienteering(Robot robot, Navigation navigation) {
        this.plane = createPlane();
        this.robot = robot;
        robot.setPlane(plane);
        this.navigation = navigation;
    }

    //Grid encoding

    /*
    0,0    0,1    0,2   0,3

    1,0    1,1    1,2   1,3

    0,0    2,1    2,2   2,3

    3,0    3,1    3,2   3,3
    */

    /**
     * Optimized localization algorithm with 3 US
     *
     * Determines the initial location and orientation of the robot
     * based on data obtained from its surroundings.
     *
     */
    public void virtualDeterministicPositioning() {
        this.plane = createPlane();

        ArrayList<Motion> motionTrace = new ArrayList<Motion>();

        int counter = 0;
        boolean hasWallLeft;
        boolean hasWallRight;
        boolean hasWallAhead;
        while(countPossibilities(this.plane) > 1) {
            int distanceLeft = getFilteredData(robot.usLeft);
            int distanceRight = getFilteredData(robot.usRight);
            int distanceForward = getFilteredData(robot.usFront);

//            RConsole.println("data left = " + distanceLeft);
//            RConsole.println("data right = " + distanceRight);
//            RConsole.println("data front = " + distanceForward);


            hasWallLeft = distanceLeft < DISTANCE_THRESHOLD;
            hasWallRight = distanceRight < DISTANCE_THRESHOLD;
            hasWallAhead = distanceForward < DISTANCE_THRESHOLD;

            if (hasWallAhead) {
                simulateOnAllTiles(motionTrace, plane, hasWallAhead, hasWallLeft, hasWallRight);
                navigation.rotate90CounterClock();
                motionTrace.add(Motion.ROTATECCW);
            } else {
                simulateOnAllTiles(motionTrace, plane, hasWallAhead, hasWallLeft, hasWallRight);
                navigation.moveForward();
                motionTrace.add(Motion.FORWARD);
            }

            //TODO: use printPlaneOptions(plane) for debugging purposes
            counter++;
        }

        RConsole.println(Integer.toString(counter));

        Coordinate startingPosition = findStartingPosition();
        Coordinate endingPosition = findEndingPosition(motionTrace, startingPosition);
        RConsole.println("Start X = " + startingPosition.getX() + " Y = " + startingPosition.getY());
        RConsole.println("Starting dir = " + startingDir);
        RConsole.println("X = " + endingPosition.getX() + " Y = " + endingPosition.getY());
        RConsole.println("Ending dir = " + endingDir);

        //Set the attributes used to know the position of the robot on the grid
        robot.setPositionOnGrid(endingPosition);
        robot.setDirection(endingDir);

        //Use for debugging purposes
        //printPlaneOptions(plane);
    }

    /**
     * Creates a copy of the plane not just a second reference
     * @param plane the plane object reference
     * @return duplicate of the plane
     */
    public Tile[][] planeCopy(Tile[][] plane) {

        Tile[][] newPlane = new Tile[plane.length][plane[0].length];
        for (int i = 0; i < newPlane.length; i++){
            for (int j = 0; j < newPlane[0].length; j++) {
                newPlane[i][j] = new Tile(plane[i][j]);
            }
        }

        /*printPlaneOptions(plane);
        System.out.println();
        printPlaneOptions(newPlane);*/

        return newPlane;
    }


    /**
     * Print plane options
     * @param plane the plane object reference
     */
    public void printPlaneOptions(Tile[][] plane) {

        Direction[] dirs = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

        for (Direction dir : dirs) {
            for (Tile[] aPlane : plane) {
                for (int j = 0; j < plane.length; j++) {
                    System.out.print(aPlane[j].isPossible(dir) + "  ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * simulate the recorded motion for all
     * starting position possible
     * @param motionTrace the stack of movements applied so far
     * @param plane the playground layout
     * @param hasWallAhead boolean
     * @param hasWallLeft boolean
     * @param hasWallRight boolean
     */
    public void simulateOnAllTiles(ArrayList<Motion> motionTrace, Tile[][] plane, boolean hasWallAhead,
                                   boolean hasWallLeft, boolean hasWallRight) {
        for (int i = 0; i < plane.length; i++) {
            for (int j = 0; j < plane.length; j++) {
                for (int k = 0; k < plane.length; k++) {
                    if (!plane[i][j].isObstacle()) {
                        Direction selectedDir;
                        VirtualRobot vr;
                        if (k == 0) {
                            vr = new VirtualRobot(i, j, Direction.NORTH);
                            selectedDir = Direction.NORTH;
                        } else if (k == 1) {
                            vr = new VirtualRobot(i, j, Direction.EAST);
                            selectedDir = Direction.EAST;
                        } else if (k == 2) {
                            vr = new VirtualRobot(i, j, Direction.WEST);
                            selectedDir = Direction.WEST;
                        } else {
                            vr = new VirtualRobot(i, j, Direction.SOUTH);
                            selectedDir = Direction.SOUTH;
                        }

                        if (plane[i][j].isPossible(vr.getDir())) {
                            if (!motionTrace.isEmpty()) {
                                for (Motion motion : motionTrace ) {
                                    if (motion == Motion.FORWARD ) {
                                        vr.moveForward();
                                    } else if (motion == Motion.ROTATECCW) {
                                        vr.rotateCCW();
                                    } else {
                                        vr.rotateCW();
                                    }
                                }
                            }

                            boolean hasObstacleAhead = vr.hasWallAhead(plane);
                            boolean hasObstacleLeft = vr.hasWallLeft(plane);
                            boolean hasObstacleRight = vr.hasWallRight(plane);

                            if (hasObstacleAhead != hasWallAhead || hasWallLeft != hasObstacleLeft || hasWallRight != hasObstacleRight) {
                                plane[i][j].setPossibilityToFalse(selectedDir);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints the number of moves performed once we have
     * reached the end of the entire motion
     * @param motionTrace the stack of movements applied so far
     */
//    private void printGoodbye(ArrayList<Motion> motionTrace){
//        LCD.drawString("Completed orienteering", 0,0);
//        LCD.drawString("Number of moves:", 0,1);
//        LCD.drawString(String.valueOf(motionTrace.size()),0,2);
//
//        Sound.twoBeeps();
//    }

    /**
     * print the initial conditions to the LCD display
     * @param startingPosition the initial position
     *//*
    private void printInitialConditions(Coordinate startingPosition){
        LCD.clear();

        //adjusting the coordinate frame to match the TA's expectations
        int y=Math.abs(startingPosition.getY()-3)*30-15;
        int x=startingPosition.getX()*30-15;

        LCD.drawString("Figured out \ninitial position", 0,3);
        LCD.drawString("X: "+ String.valueOf(x), 0,5);
        LCD.drawString("Y: "+ String.valueOf(y), 0,6);
        LCD.drawString("Dir: "+ String.valueOf(startingDir), 0,7);

    }*/

    /**
     * Calculates the starting location and orientation of the robot
     * once it has has finished eliminating impossible
     * starting points on the grid
     * @return Coordinates where the robot started
     */
    public Coordinate findStartingPosition() {

    	Direction[] directions = { Direction.NORTH,
    							   Direction.SOUTH,
    							   Direction.EAST,
    							   Direction.WEST
              					 };

    	Coordinate coord = new Coordinate(0,0);
    	for(Direction dir : directions) {
    		for(int i = 0; i < plane.length; i++) {
    			for(int j = 0; j < plane.length; j++) {
    				if (plane[i][j].isPossible(dir)) {
    					//TODO: make sure coords are not backwards
                        coord = new Coordinate(i,j);
    					this.startingDir = dir;
    					break;
    				}
    			}
    		}
    	}
    	return coord;
	}

    /**
     * using the virtual implementation of the robot and the
     * stack of recorded moves, we find out where the orienteering
     * algorithm has lead us
     *
     * @param motionTrace the stack of recorded moves
     * @param startingPosition the initial position
     * @return the current position
     */
	public Coordinate findEndingPosition(ArrayList<Motion> motionTrace, Coordinate startingPosition) {
		VirtualRobot vr = new VirtualRobot(startingPosition.getX(), startingPosition.getY(), this.startingDir);
		if (!motionTrace.isEmpty()) {
            for (Motion motion : motionTrace ) {
                if (motion == Motion.FORWARD ) {
                    vr.moveForward();
                } else {
                    vr.rotateCCW();
                }
            }
        }
		this.endingDir = vr.getDir();
        //TODO: make sure coords are not backwards
        return new Coordinate(vr.getX(), vr.getY());
    }

    /**
     * Count the remaining possibilities for the starting position
     * @param plane the plane object reference
     * @return the number of possible starting position
     */
    public int countPossibilities(Tile[][] plane) {
        int posCount = 0;
        for (Tile[] aPlane : plane) {
            for (int j = 0; j < plane.length; j++) {
                if (!aPlane[j].isObstacle()) {
                    if (aPlane[j].isPossible(Direction.NORTH)) posCount++;
                    if (aPlane[j].isPossible(Direction.SOUTH)) posCount++;
                    if (aPlane[j].isPossible(Direction.WEST)) posCount++;
                    if (aPlane[j].isPossible(Direction.EAST)) posCount++;
                }
            }
        }
        return posCount;
    }

    /**
     * Create the Tile array with the walls
     * and place the obstacles on the playground
     * @return The newly created and populated Tile array
     */
    public Tile[][] createPlane() {
        Tile[][] plane = new Tile[4][4];

        for (int i = 0; i < plane.length; i++) {
            for (int j = 0; j < plane.length; j++) {
                plane[i][j] = new Tile();
            }
        }

        for (int i = 0; i < plane.length; i++) {
            plane[0][i].setObstacle(Direction.NORTH, true);
            plane[3][i].setObstacle(Direction.SOUTH, true);
            plane[i][0].setObstacle(Direction.WEST, true);
            plane[i][3].setObstacle(Direction.EAST, true);
        }
        fillRemainingPositions(plane);

        countPossibilities(plane);

        return plane;
    }

    /**
     * retrieves the plane object
     * @return
     */
    public Tile[][] getPlane() {
        return this.plane;
    }

    /**
     * setup for initial playground layout
     * @param plane the reference to the Tile array
     */
    public void fillRemainingPositions(Tile[][] plane) {
        plane[0][1].setObstacle(Direction.WEST, true);
        plane[0][2].setObstacle(Direction.SOUTH, true);
        plane[0][3].setObstacle(Direction.SOUTH, true);
        plane[1][0].setObstacle(Direction.NORTH, true);
        plane[1][1].setObstacle(Direction.EAST, true);
        plane[2][2].setObstacle(Direction.NORTH, true);
        plane[2][1].setObstacle(Direction.SOUTH, true);
        plane[2][3].setObstacle(Direction.NORTH, true);
        plane[3][0].setObstacle(Direction.EAST, true);
        plane[3][2].setObstacle(Direction.WEST, true);
        plane[0][0].setObstacle(true);
        plane[1][2].setObstacle(true);
        plane[1][3].setObstacle(true);
        plane[3][1].setObstacle(true);
        //close possibilities of tiles that are obstacles
        plane[0][0].closeAllPossibilities();
        plane[1][2].closeAllPossibilities();
        plane[1][3].closeAllPossibilities();
        plane[3][1].closeAllPossibilities();
    }

    /*
    0,0    0,1    0,2   0,3

    1,0    1,1    1,2   1,3

    2,0    2,1    2,2   2,3

    3,0    3,1    3,2   3,3
    */

    /**
     * take five readings with the ultrasonic sensor
     * and return the median value
     * @return the filtered distance read with the us sensor
     * @param us
     */
    private int getFilteredData(UltrasonicSensor us) {
		int distance;
		int[] dist = new int[5];

		for (int i = 0; i < 5; i++) {

            us.ping();
            // wait for ping to complete
            //TODO: decide on a time for us readings
            sleep(25);
            // there will be a delay
            dist[i] = us.getDistance();

		}

        Arrays.sort(dist);
		distance = dist[2];

        //if (distance < DISTANCE_THRESHOLD)

		return distance;
	}

    /**
     * Sleep thread
     * @param delay the delay in milliseconds
     *
     */
	public void sleep(int delay) {
		try { Thread.sleep(delay); } catch (InterruptedException e) {e.printStackTrace();}
	}

    /**
     * Class to track the position and path of the robot.
     * This allows us to compare the movements of the robot
     * to compare with the obstacle position
     *
     * @author Daniel Macario
     * @version 1.1
     */
    public class VirtualRobot {

        private int x;
        private int y;
        private Direction dir;
        private final Object lock = new Object();

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
                if (this.dir == Direction.NORTH) x--;
                else if (this.dir == Direction.SOUTH) x++;
                else if (this.dir == Direction.EAST) y++;
                else y--;
            }
        }

        /**
         * Rotate the robot counter-clockwise
         */
        public void rotateCCW() {
            synchronized (lock) {
                if (this.dir == Direction.NORTH) this.dir = Direction.WEST;
                else if (this.dir == Direction.WEST) this.dir = Direction.SOUTH;
                else if (this.dir == Direction.SOUTH) this.dir = Direction.EAST;
                else if (this.dir == Direction.EAST)  this.dir = Direction.NORTH;
            }
        }

        /**
         * Rotate the robot clockwise
         */
        public void rotateCW() {
            synchronized (lock) {
                if (this.dir == Direction.NORTH) this.dir = Direction.EAST;
                else if (this.dir == Direction.WEST) this.dir = Direction.NORTH;
                else if (this.dir == Direction.SOUTH) this.dir = Direction.WEST;
                else if (this.dir == Direction.EAST) this.dir = Direction.SOUTH;
            }
        }

        /**
         * simulate the full stack of recorded motion
         * @param m the stack of moves
         */
        public void performMotion(Motion m) {
            if (m == Motion.FORWARD) moveForward();
            else if (m == Motion.ROTATECCW) rotateCCW();
            else rotateCW();
        }

        /**
         * Method determining if the robot is facing a wall
         * @param plane the playground layout
         * @return is there a wall ahead considering the current position and orientation
         */
        public boolean hasWallAhead(Tile[][] plane) {
            synchronized (lock) {
                if (this.dir == Direction.NORTH) return plane[x][y].hasObstacle(Direction.NORTH);
                else if (this.dir == Direction.SOUTH) return plane[x][y].hasObstacle(Direction.SOUTH);
                else if (this.dir == Direction.EAST) return plane[x][y].hasObstacle(Direction.EAST);
                else if (this.dir == Direction.WEST) return plane[x][y].hasObstacle(Direction.WEST);
                return false;
            }
        }


        /**
         * Determines if the robot has a wall to its left
         * @param plane the plane object reference
         * @return boolean if there is a wall to the left
         */
        public boolean hasWallLeft(Tile[][] plane) {
            synchronized (lock) {
                if (this.dir == Direction.NORTH) return plane[x][y].hasObstacle(Direction.WEST);
                else if (this.dir == Direction.SOUTH) return plane[x][y].hasObstacle(Direction.EAST);
                else if (this.dir == Direction.EAST) return plane[x][y].hasObstacle(Direction.NORTH);
                else if (this.dir == Direction.WEST) return plane[x][y].hasObstacle(Direction.SOUTH);
                return false;
            }
        }

        /**
         * Determines if the robot has a walls to its right
         * @param plane the plane object reference
         * @return boolean if there is a wall to the right
         */
        public boolean hasWallRight(Tile[][] plane) {
            synchronized (lock) {
                if (this.dir == Direction.NORTH) return plane[x][y].hasObstacle(Direction.EAST);
                else if (this.dir == Direction.SOUTH) return plane[x][y].hasObstacle(Direction.WEST);
                else if (this.dir == Direction.EAST) return plane[x][y].hasObstacle(Direction.SOUTH);
                else if (this.dir == Direction.WEST) return plane[x][y].hasObstacle(Direction.NORTH);
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

    /**
     * Possible movements
     * @author Daniel Macario
     * @version 1.1
     */
    public enum Motion {
        /**
         * rotate 90 degree counter-clockwise
         */
        ROTATECCW,
        /**
         * rotate 90 degrees clock-wise
         */
        ROTATECW
        ,
        /**
         * Travel to the next tile in the current direction
         */
        FORWARD
    }

    /**
     * Four possible cardinal points
     * @author Daniel Macario
     * @version 1.1
     */
    public enum Direction {
        /**
         * Facing north
         */
        NORTH,
        /**
         * Facing south
         */
        SOUTH,
        /**
         * Facing east
         */
        EAST,
        /**
         * facing west
         */
        WEST
    }

}
