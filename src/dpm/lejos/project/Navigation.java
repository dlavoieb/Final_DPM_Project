package dpm.lejos.project;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Mapper;
import dpm.lejos.orientation.Orienteering.*;
import lejos.nxt.comm.RConsole;

import java.util.ArrayList;

/**
 * Provides all the necessary navigation capabilities
 *
 * All motion is managed by this class
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Navigation {

	private boolean noObstacle = true;
	private Odometer m_Odometer;
	private ObstacleDetection m_ObstacleDetection;
    private Mapper mapper;

    private Robot m_robot;

    /**
     * default constructor
     *
     * @param robot the robot object
     */
	public Navigation(Robot robot, Odometer odometer){
        m_robot = robot;
        mapper = new Mapper(Mapper.MapID.LAB4);
        m_Odometer=odometer;

	}

	/**
	 * method used to rotateCCW the
     * robot to an absolute angle
     *
	 * @param angle the angle to rotateCCW to
	 */
	public void rotateTo(double angle){

	}

	/**
	 * method used to send the robot to a
     * predetermined absolute location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void travelTo(double x, double y){

	}

    /**
     * moves the robot to the specified coordinate
     * See the plane encoding in the orienteering or navigator classes
     * @param destination
     */
    public void travelTo(Coordinate destination) {
        navigate(destination);
    }

    public void floatMotors(){
        m_robot.motorRight.flt(true);
        m_robot.motorLeft.flt(true);
    }

    /**
     * move the robot one tile forward
     */
    public void moveForward() {
        m_robot.motorLeft.setSpeed(m_robot.CRUISE_SPEED);
        m_robot.motorRight.setSpeed(m_robot.CRUISE_SPEED);
        m_robot.motorLeft.rotate(Utils.robotDistanceToMotorAngle(m_robot.tileLength, m_robot), true);
        m_robot.motorRight.rotate(Utils.robotDistanceToMotorAngle(m_robot.tileLength, m_robot), false);
    }

    /**
     * rotate the physical robot 90 degrees counterclockwise
     */
    public void rotate90CounterClock() {
        m_robot.motorLeft.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorRight.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorLeft.rotate(Utils.robotRotationToMotorAngle(-90, m_robot), true);
        m_robot.motorRight.rotate(Utils.robotRotationToMotorAngle(90, m_robot), false);
    }

    /**
     * rotate the physical robot 90 degrees clockwise
     */
    public void rotate90ClockWise() {
        m_robot.motorLeft.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorRight.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorLeft.rotate(Utils.robotRotationToMotorAngle(90, m_robot), true);
        m_robot.motorRight.rotate(Utils.robotRotationToMotorAngle(-90, m_robot), false);
    }

    /**
    * position the robot facing north
    * @param destinationDirection the current heading
    */
	public void rotateToDirection(Direction destinationDirection) {

        Direction robotDirection = m_robot.getDirection();

        if (robotDirection == Direction.NORTH) {
            switch (destinationDirection) {
                case SOUTH:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
                case EAST:
                    rotate90ClockWise();
                    break;
                case WEST:
                    rotate90CounterClock();
                    break;
            }
        } else if (robotDirection == Direction.SOUTH) {
            switch (destinationDirection) {
                case NORTH:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
                case EAST:
                    rotate90CounterClock();
                    break;
                case WEST:
                    rotate90ClockWise();
                    break;
            }
        } else if (robotDirection == Direction.EAST) {
            switch (destinationDirection) {
                case NORTH:
                    rotate90CounterClock();
                    break;
                case SOUTH:
                    rotate90ClockWise();
                    break;
                case WEST:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
            }
        } else {
            switch (destinationDirection) {
                case NORTH:
                    rotate90ClockWise();
                    break;
                case SOUTH:
                    rotate90CounterClock();
                    break;
                case EAST:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
            }
        }

        m_robot.setDirection(destinationDirection);

    }



    public void performMoves(ArrayList<Mapper.Node> directions) {

        Coordinate currentPosition = directions.remove(0).getCoordinate();
        Coordinate nextPosition;
        for (Mapper.Node node : directions) {
            nextPosition = node.getCoordinate();

            //Note that the robot can only move in one axis at a time
            //So the new coordinate will either have a new X or a new Y
            //TODO: discuss if we should adapt the coordinate system

            if (currentPosition.getX() != nextPosition.getX()) {
                //Robot is moving in the y-axis
                if (currentPosition.getX() > nextPosition.getX()) {
                    //Robot is moving north
                    rotateToDirection(Direction.NORTH);
                    moveForward();
                } else if (currentPosition.getX() < nextPosition.getX()) {
                    //robot is moving south
                    rotateToDirection(Direction.SOUTH);
                    moveForward();
                }
            } else if (currentPosition.getY() != nextPosition.getY()) {
                //Robot is moving in the x-axis
                if (currentPosition.getY() > nextPosition.getY()) {
                    //Robot is moving west
                    rotateToDirection(Direction.WEST);
                    moveForward();
                } else if (currentPosition.getY() < nextPosition.getY()) {
                    //robot is moving east
                    moveForward();
                }
            }
        }

        //set position of the robot to the last tile visited
        //TODO: verify that this actually works.
        m_robot.setPositionOnGrid(directions.get(directions.size() - 1).getCoordinate());
    }

    public void navigate(Coordinate endingCoordinate){

        Coordinate startingCoordinate = m_robot.getPositionOnGrid();
        RConsole.println("start x = " + Integer.toString(startingCoordinate.getX()) + " y = " + Integer.toString(startingCoordinate.getY()));
        //TODO: make sure that this is not BACKWARDS!!!!
        Mapper.Node current = mapper.graphPlane[startingCoordinate.getX()][startingCoordinate.getY()];
        Mapper.Node finish = mapper.graphPlane[endingCoordinate.getX()][startingCoordinate.getY()];

        ArrayList<Mapper.Node> reverseDirections = new ArrayList<Mapper.Node>();
        ArrayList<Mapper.Node> queue = new ArrayList<Mapper.Node>();
        queue.add(current);
        current.setVisited(true);

        while(!queue.isEmpty()){
            current = queue.remove(0);
            if (current.equals(finish)){
                break;
            }else{
                for(Mapper.Node node : current.getNeighbours()){
                    if(!node.getVisited()){
                        queue.add(node);
                        node.setVisited(true);
                        node.setPrevious(current);
                    }
                }
            }
        }

        for(Mapper.Node node = finish; node != null; node = node.getPrevious()) {
            reverseDirections.add(0, node);
        }

        mapper.printDirections(reverseDirections);
        performMoves(reverseDirections);
    }
}//end Navigation