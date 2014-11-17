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
	public Mapper mapper;

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

    public Navigation(Robot robot, Odometer odometer, Mapper.MapID id){
        m_robot = robot;
        mapper = new Mapper(id);
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
        Mapper.Node finish = mapper.graphPlane[endingCoordinate.getX()][endingCoordinate.getY()];

        ArrayList<Mapper.Node> reverseDirections = new ArrayList<Mapper.Node>();
        ArrayList<Mapper.Node> queue = new ArrayList<Mapper.Node>();
        queue.add(current);
        current.setVisited(true);

        while(!queue.isEmpty()){
            current = queue.remove(0);
            if (current.equals(finish)){
                break;
            } else {
                for(Mapper.Node node : current.getNeighbours()){
                    if(!node.getVisited()){
                        queue.add(node);
                        node.setVisited(true);
                        node.setPrevious(current);
                    }
                }
            }
        }

        RConsole.println(Integer.toString(reverseDirections.size()));

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
        int index = 0;
        while (directions.size()>1) {
            Mapper.Node initial = directions.get(0);
            Mapper.Node next = directions.get(1);
            if (initial.getX() == next.getX()) {
                //next has same x
                while (index + 1< directions.size()) {
                    if (directions.get(index).getX() == directions.get(index + 1).getX()) {
                        index++;
                    } else {
                        break;
                    }
                }
                removeBefore(directions, index);

                //reset index to 0 since we remove everything before it.
                index = 0;

                // travelTo(directions.get(index).getCoordinate());
                System.out.println("X: " + directions.get(index).getX() + ", Y: " + directions.get(index).getY());

            } else if (initial.getY() == next.getY()) {
                // next has same y
                while (index + 1 < directions.size()) {
                    if (directions.get(index).getY() == directions.get(index + 1).getY()) {
                        index++;
                    } else {
                        break;
                    }
                }
                removeBefore(directions, index);
                //reset index to 0 since we remove everything before it.
                index = 0;

                //travelTo(directions.get(index).getCoordinate());
                System.out.println("X: " + directions.get(index).getX() + ", Y: " + directions.get(index).getY());

            } else
                return;
        }
    }

    private void removeBefore(ArrayList<?> list, int index){
        for (int i = index - 1; i >= 0; i--) {
            // remove all elements in the stack before the position we travel to
            list.remove(i);
        }
    }


	/**
	 * method used to send the robot to a
     * predetermined absolute location
	 *
     * we assume that there is now no
     * obstacles between us and our destination
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
        travelTo(destination.getX()* m_robot.tileLength + m_robot.tileLength/2.0 ,destination.getY()* m_robot.tileLength + m_robot.tileLength/2.0);
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
        m_robot.motorRight.flt();
        m_robot.motorLeft.flt();
    }

    /**
     * Check if the coordinates of the robot are within an acceptable range to those specified
     * @param x target x coordinate
     * @param y target y coordinate
     * @return boolean true if in acceptable range
     */
    private boolean closeEnough(double x, double y) {
        return Math.abs(x - m_Odometer.getX()) < m_robot.ACCEPTABLE_LINEAR && Math.abs(y - m_Odometer.getY()) < m_robot.ACCEPTABLE_LINEAR;
    }

    /**
     * Check if the orientation of the robot is within an acceptable range of the specified angle
     * @param theta target orientation
     * @return boolean true if in acceptable range
     * */
    private boolean closeEnough(double theta) {
        return Math.abs(theta - m_Odometer.getTheta()) <= Math.toDegrees(m_robot.ACCEPTABLE_ANGLE);
    }

    /**
     * Check if the coordinates of the robot are within an acceptable range to those specified
     * @param coordinate the target coordinate
     * @return boolean true if in acceptable range
     */
    private boolean closeEnough(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - m_Odometer.getX()) < m_robot.ACCEPTABLE_LINEAR && Math.abs(coordinate.getY() - m_Odometer.getY()) < m_robot.ACCEPTABLE_LINEAR;
    }

    /**
     * Compute the optimal way to get from angle a to angle b
     * @param currentTheta current heading
     * @param desiredTheta desired heading
     * @return the signed number of degrees to rotate, sign indicated direction
     */
    private static double computeOptimalRotationAngle(double currentTheta, double desiredTheta){
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
    private static Vector vectorDisplacement(double[] currentPosition, double[] destination){
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