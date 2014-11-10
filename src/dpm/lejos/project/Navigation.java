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
 * @version 1.5
 */
public class Navigation {

    private Odometer m_Odometer;
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
     * perform the navigation planning to get to the desired coordinate
     *
     * call to <code>performMoves</code> that will move the robot
     * @param endingCoordinate the desired finish point
     */
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

    /**
     * preform the list of movements
     * @param directions the list of movements to follow
     */
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

	/**
	 * method used to send the robot to a
     * predetermined absolute location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
    public void travelTo(double x, double y){
        try {

            double[] currentPosition = m_Odometer.getPosition();

            Vector vector = vectorDisplacement(currentPosition, new double[]{x, y});

            RConsole.println("Magnitude: " + String.valueOf(vector.getMagnitude()));
            RConsole.println("Orientation: " + String.valueOf(vector.getOrientation()));
            rotateTo(vector.getOrientation());

            m_robot.motorLeft.setSpeed(m_robot.CRUISE_SPEED);
            m_robot.motorRight.setSpeed(m_robot.CRUISE_SPEED);

            m_robot.motorLeft.rotate(Utils.robotDistanceToMotorAngle(vector.getMagnitude(), m_robot));
            m_robot.motorRight.rotate(Utils.robotDistanceToMotorAngle(vector.getMagnitude(), m_robot));

            while(isNavigating()){
                Thread.sleep(10);
            }


            if (!closeEnough(x, y)) {
                RConsole.println("Not close enough, redo!");
                travelTo(x, y);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * moves the robot to the specified coordinate
     * See the plane encoding in the orienteering or navigator classes
     * @param destination
     */
    public void travelTo(Coordinate destination) {
        navigate(destination);
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
     * Rotates the robot to the desired angle using the optimal angle and direction
     *
     * Positive angle goes counterCW
     * @param theta the desired angle to rotate to
     */
    public void rotateTo(double theta){

        //implementation of slide 13 in navigation tutorial
        double thetaCurrent = m_Odometer.getTheta();

        double rotationAngle = computeOptimalRotationAngle(thetaCurrent,theta);

        m_robot.motorLeft.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorRight.setSpeed(m_robot.ROTATE_SPEED);
        int angle = Utils.robotRotationToMotorAngle(rotationAngle, m_robot);

        m_robot.motorLeft.rotate(-angle, true);
        m_robot.motorRight.rotate(angle, false);


        if (!closeEnough(theta)){
            RConsole.println("Not close enough, redo!");
            rotateTo(theta);
        }
    }

    /**
     * rotate the physical robot 90 degrees counterclockwise
     */
    public void rotate90CounterClock() {
        rotateTo(m_Odometer.getTheta() + 90);
    }

    /**
     * rotate the physical robot 90 degrees clockwise
     */
    public void rotate90ClockWise() {
        rotateTo(m_Odometer.getTheta() - 90);
    }

    /**
    * position the robot facing north
    * @param destinationDirection the current heading
    */
    //TODO: refactor to use rotateTo instead of multiple CW/CCW
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

    /**
     * Check if the robot is travelling
     * @return is the robot travelling
     */
    public boolean isNavigating(){
        return m_robot.motorLeft.isMoving() || m_robot.motorRight.isMoving();
    }

    /**
     * stop position regulation to allow free moving of the motors
     */
    public void floatMotors(){
        m_robot.motorRight.flt(true);
        m_robot.motorLeft.flt(true);
    }

    /**
     * Check if the coordinates of the robot are within an acceptable range to those specified
     * @param x target x coordinate
     * @param y target y coordinate
     * @return boolean true if in acceptable range
     */
    public boolean closeEnough(double x, double y) {
        return Math.abs(x - m_Odometer.getX()) < m_robot.ACCEPTABLE_LINEAR && Math.abs(y - m_Odometer.getY()) < m_robot.ACCEPTABLE_LINEAR;
    }

    /**
     * Check if the orientation of the robot is within an acceptable range of the specified angle
     * @param theta target orientation
     * @return boolean true if in acceptable range
     * */
    public boolean closeEnough(double theta) {
        return Math.abs(theta - m_Odometer.getTheta()) <= Math.toDegrees(m_robot.ACCEPTABLE_ANGLE);
    }

    /**
     * Check if the coordinates of the robot are within an acceptable range to those specified
     * @param coordinate the target coordinate
     * @return boolean true if in acceptable range
     */
    public boolean closeEnough(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - m_Odometer.getX()) < m_robot.ACCEPTABLE_LINEAR && Math.abs(coordinate.getY() - m_Odometer.getY()) < m_robot.ACCEPTABLE_LINEAR;
    }

    /**
     * Compute the optimal way to get from angle a to angle b
     * @param currentTheta current heading
     * @param desiredTheta desired heading
     * @return the signed number of degrees to rotate, sign indicated direction
     */
    public static double computeOptimalRotationAngle(double currentTheta, double desiredTheta){
        //implementation of slide 13 in navigation tutorial
        if (desiredTheta-currentTheta < -Math.PI){
            return (desiredTheta-currentTheta)+2* Math.PI;
        } else if (desiredTheta - currentTheta > Math.PI){
            return desiredTheta - currentTheta - 2* Math.PI;
        } else {
            return desiredTheta - currentTheta;
        }
    }

    /**
     * Converts a set of coordinates in a vector displacement with orientation and magnitude
     * @param currentPosition array of 3 elements respectively (x, y, theta) representing the current psotition and orientation
     * @param destination array of 2 elements being (x, y)
     * @return Vector representing the displacement to happen (r, theta)
     */
    public static Vector vectorDisplacement(double[] currentPosition, double[] destination){
        Vector vector = new Vector();
        if (currentPosition.length == 3 && destination.length == 2){
            //expnaded pythagora
            vector.setMagnitude(Math.sqrt(destination[0] * destination[0] - 2 * destination[0] * currentPosition[0] + currentPosition[0] * currentPosition[0] + destination[1] * destination[1] - 2 * destination[1] * currentPosition[1] + currentPosition[1] * currentPosition[1]));

            double x = destination[0] - currentPosition[0];
            double y = destination[1] - currentPosition[1];

            if (x>=0) {
                vector.setOrientation(Math.atan((y) / (x)));
            } else if (x<0 && y>0){
                vector.setOrientation(Math.atan((y) / (x)) + Math.PI);
            } else if (x<0 && y<0){
                vector.setOrientation(Math.atan((y) / (x))-Math.PI);
            }
        }
        return vector;
    }


}//end Navigation