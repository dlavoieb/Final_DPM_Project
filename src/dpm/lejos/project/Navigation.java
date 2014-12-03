package dpm.lejos.project;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Mapper;
import dpm.lejos.orientation.Node;
import lejos.nxt.comm.RConsole;

import java.util.ArrayList;

/**
 * Provides all the necessary navigation capabilities
 *
 * All motion is managed by this class
 *
 * @author Daniel Macario, David Lavoie-Boutin
 * @version 1.5
 */
public class Navigation {

    private Odometer m_Odometer;
	public Mapper mapper;

    private Robot m_robot;

    private boolean movingForward = false;

    /**
     * default constructor
     *
     * @param robot the robot object
     * @param odometer the odometer object reference
     */
	public Navigation(Robot robot, Odometer odometer){
        m_robot = robot;
        mapper = new Mapper(Mapper.MapID.Final1);
        m_Odometer=odometer;
        m_Odometer.setNavigation(this);

	}

    /**
     * Initialize a navigation instance associated to a particular map.
     * @param robot Robot instance used for navigation.
     * @param odometer Odometer instance used to keep track of the position of
     *                 the robot.
     * @param id ID the robot will be placed in.
     */
    public Navigation(Robot robot, Odometer odometer, int id){
        m_robot = robot;
        createMapper(id);
        m_Odometer=odometer;
        m_Odometer.setNavigation(this);
        m_Odometer.startCorrection();
    }

    /**
     * Based on the integer id passed we create an instance of the map used
     * for navigation.
     * @param id Integer ID specifying the number of the map used
     *           to navigate in.
     */
    public void createMapper(int id) {
        switch (id) {
            case 1:
                mapper = new Mapper(Mapper.MapID.Final1);
                break;
            case 2:
                mapper = new Mapper(Mapper.MapID.Final2);
                break;
            case 3:
                mapper = new Mapper(Mapper.MapID.Final3);
                break;
            case 4:
                mapper = new Mapper(Mapper.MapID.Final4);
                break;
            case 5:
                mapper = new Mapper(Mapper.MapID.Final5);
                break;
            case 6:
                mapper = new Mapper(Mapper.MapID.Final6);
                break;
        }
    }

    /**
     * Perform the navigation planning to get to the desired coordinate
     *
     * call to <code>computeMoveList</code> that will move the robot
     * @param endingCoordinate the desired finish point
     */
    public void navigate(Coordinate endingCoordinate){

        Coordinate startingCoordinate = m_robot.getPositionOnGrid();
        RConsole.println("start x = " + Integer.toString(startingCoordinate.getX()) + " y = " + Integer.toString(startingCoordinate.getY()));
        mapper.graphPlane[10][0].setObstacle(true);
        mapper.graphPlane[10][1].setObstacle(true);
        mapper.graphPlane[11][0].setObstacle(true);
        mapper.graphPlane[11][1].setObstacle(true);

        cleanGraph();

        Node current = mapper.graphPlane[startingCoordinate.getX()][startingCoordinate.getY()];
        Node finish = mapper.graphPlane[endingCoordinate.getX()][endingCoordinate.getY()];

        ArrayList<Node> reverseDirections = new ArrayList<Node>();
        ArrayList<Node> queue = new ArrayList<Node>();
        queue.add(current);
        current.setVisited(true);

        while(!queue.isEmpty()){
            current = queue.remove(0);
            if (current.equals(finish)){
                break;
            } else {
                for(Node node : current.getNeighbours()){
                    if(!node.getVisited() && !node.isObstacle()){
                        queue.add(node);
                        node.setVisited(true);
                        node.setPrevious(current);
                    }
                }
            }
        }

        RConsole.println(Integer.toString(reverseDirections.size()));

        for(Node node = finish; node != null; node = node.getPrevious()) {
            reverseDirections.add(0, node);
        }

        mapper.printDirections(reverseDirections);
        ArrayList<Coordinate> moveList = computeMoveListWithChains(reverseDirections);
        performMoves(moveList);
    }

    /**
     * Cleans the graph before initiating the navigation procedure again, so that
     * all the nodes are reset to the default state of not visited.
     */
    private void cleanGraph() {

        for (Node[] row : mapper.graphPlane) {
            for (Node node : row) {
                node.setVisited(false);
                node.setPrevious(null);
            }
        }

    }

    /**
     * Determines the set of nodes that we need to travel to in order
     * to go from one destination to another.
     * @param directions An ArrayList specifying the set of coordinates
     *                   to traverse in order to go from one node on the graph
     *                   to another.
     * @return An ArrayList of coordinate objects specifying the path we need
     * to follow to go from one node on the graph to another.
     */
    public ArrayList<Coordinate> computeMoveListWithChains(ArrayList<Node> directions) {
        ArrayList<Coordinate> path = new ArrayList<Coordinate>();
        for (Node node: directions) {
            path.add(node.getCoordinate());
        }
        return path;
    }

    /**
     * Minimizes the number of node chains to go from
     * one node on the graph to another.
     * @param directions the list of movements to follow
     * @return the list
     */
    public ArrayList<Coordinate> computeMoveList(ArrayList<Node> directions) {

        ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
        int index = 0;
        while (directions.size()>1) {
            Node initial = directions.get(0);
            Node next = directions.get(1);
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

                RConsole.println("X: " + directions.get(index).getX() + ", Y: " + directions.get(index).getY());
                coordinateList.add(directions.get(index).getCoordinate());

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

                RConsole.println("X: " + directions.get(index).getX() + ", Y: " + directions.get(index).getY());
                coordinateList.add(directions.get(index).getCoordinate());
            }
        }
        return coordinateList;
    }

    /**
     * Executes the shortest path calculated to go from one node
     * to another.
     * @param coordinates An ArrayList specifying the set of coordinates
     *                    that we will need to traverse in order
     *                    to go from one node to another on the graph.
     */
    public void performMoves(ArrayList <Coordinate> coordinates){
        for (Coordinate coordinate : coordinates) {
            travelTo(coordinate);
        }
    }

    private void removeBefore(ArrayList<?> list, int index){
        for (int i = index - 1; i >= 0; i--) {
            // remove all elements in the stack before the position we travel to
            list.remove(i);
        }
    }

	/**
	 * Method used to displace the robot to a
     * predetermined location on the coordinate system
	 *
     * We assume that there are no
     * obstacles between us and our destination.
     *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
    public void travelTo(double x, double y){
        try {
            double[] currentPosition = m_Odometer.getPosition();

            RConsole.println("Current Pos X = " + Double.toString(currentPosition[0]));
            RConsole.println("Current Pos Y = " + Double.toString(currentPosition[1]));
            RConsole.println("Current THETA = " + Double.toString(Math.toDegrees(currentPosition[2])));

            Vector vector = vectorDisplacement(currentPosition, new double[]{ x, y });

            RConsole.println("Magnitude: " + String.valueOf(vector.getMagnitude()));
            RConsole.println("Orientation: " + String.valueOf(Math.toDegrees(vector.getOrientation())));
            rotateTo(Math.toDegrees(vector.getOrientation()));

            movingForward = true;
            m_robot.motorLeft.setAcceleration(Robot.ACCELERATION);
            m_robot.motorRight.setAcceleration(Robot.ACCELERATION);

            m_robot.motorLeft.setSpeed(Robot.CRUISE_SPEED);
            m_robot.motorRight.setSpeed(Robot.CRUISE_SPEED);

            m_robot.motorLeft.rotate(Utils.robotDistanceToMotorAngle(vector.getMagnitude(), m_robot), true);
            m_robot.motorRight.rotate(Utils.robotDistanceToMotorAngle(vector.getMagnitude(), m_robot), true);


            while(isNavigating()){
                Thread.sleep(10);
            }
            movingForward = false;

            RConsole.println("");
            RConsole.println("travel X = " + Double.toString(x));
            RConsole.println("travel y = " + Double.toString(y));
            RConsole.println("Pos X before closeEnough = " + Double.toString(m_Odometer.getX()));
            RConsole.println("Pos Y before closeEnough = " + Double.toString(m_Odometer.getY()));
            RConsole.println("");

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
     * Rotates the robot to a specified coordinate on the grid.
     * @param x The x coordinate to rotate to.
     * @param y The y coordinate to rotate to
     */
    public void rotateToCoordinate(double x, double y) {
        try {
            double[] currentPosition = m_Odometer.getPosition();

            RConsole.println("Current Pos X = " + Double.toString(currentPosition[0]));
            RConsole.println("Current Pos Y = " + Double.toString(currentPosition[1]));
            RConsole.println("Current THETA = " + Double.toString(Math.toDegrees(currentPosition[2])));

            Vector vector = vectorDisplacement(currentPosition, new double[]{x, y});

            RConsole.println("Magnitude: " + String.valueOf(vector.getMagnitude()));
            RConsole.println("Orientation: " + String.valueOf(Math.toDegrees(vector.getOrientation())));
            rotateTo(Math.toDegrees(vector.getOrientation()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves the robot to the specified coordinate.
     * See the plane encoding in the orienteering or navigator classes
     * @param destination the coordinate reference for the destination
     */
    public void travelTo(Coordinate destination) {

        RConsole.println("COORDINATE X = " + Double.toString(destination.getX() * Robot.tileLength + Robot.tileLength /2.0));
        RConsole.println("COORDINATE Y = " + Double.toString(destination.getY() * Robot.tileLength + Robot.tileLength / 2.0));

        travelTo(destination.getX() * Robot.tileLength + Robot.tileLength / 2.0, destination.getY() * Robot.tileLength + Robot.tileLength / 2.0);
    }

    /**
     * Move the robot on tile forward, the equivalent
     * of 30 cm.
     */
    public void moveForward() {
        m_robot.motorLeft.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorRight.setSpeed(Robot.CRUISE_SPEED);

        double oldTheta = m_Odometer.getThetaInDegrees();

        if      (oldTheta >= -45  && oldTheta <= 45) {
            travelTo(m_Odometer.getX()+ Robot.tileLength, m_Odometer.getY());
        }
        else if (oldTheta >= 45   && oldTheta <= 135)  {
            travelTo(m_Odometer.getX(), m_Odometer.getY() + Robot.tileLength);
        }
        else if (oldTheta >= -135 && oldTheta <= -45)  {
            travelTo(m_Odometer.getX(), m_Odometer.getY() - Robot.tileLength);
        }
        else if (oldTheta >= 135  || oldTheta <= -135) {
            travelTo(m_Odometer.getX()- Robot.tileLength, m_Odometer.getY());
        }
    }

    /**
     * Moves the robot backward 15 cm.
     */
    public void moveBackwardHalfATile() {
        m_robot.motorLeft.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorRight.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorLeft.rotate(-Utils.robotDistanceToMotorAngle(Robot.tileLength / 2, m_robot), true);
        m_robot.motorRight.rotate(-Utils.robotDistanceToMotorAngle(Robot.tileLength / 2, m_robot), false);
    }

    /**
     * Moves the robot forward 15 cm.
     */
    public void moveForwardHalfATile() {
        m_robot.motorLeft.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorRight.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorLeft.rotate(Utils.robotDistanceToMotorAngle(Robot.tileLength / 2, m_robot), true);
        m_robot.motorRight.rotate(Utils.robotDistanceToMotorAngle(Robot.tileLength / 2, m_robot), false);
    }

    /**
     * Moves the robot forward 10 cm.
     */
    public void moveForwardThirdOfATile() {
        m_robot.motorLeft.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorRight.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorLeft.rotate(Utils.robotDistanceToMotorAngle(Robot.tileLength / 3, m_robot), true);
        m_robot.motorRight.rotate(Utils.robotDistanceToMotorAngle(Robot.tileLength / 3, m_robot), false);
    }

    /**
     * Moves the robot backwards a 10 cm.
     */
    public void moveBackwardThirdOfATile() {
        m_robot.motorLeft.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorRight.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorLeft.rotate(Utils.robotDistanceToMotorAngle(-Robot.tileLength / 3, m_robot), true);
        m_robot.motorRight.rotate(Utils.robotDistanceToMotorAngle(-Robot.tileLength / 3, m_robot), false);
    }

    /**
     * Moves the robot backwards a specified distance
     * @param x The distance to move back.
     */
    public void moveBackSpecifiedAmount(double x) {
        m_robot.motorLeft.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorRight.setSpeed(Robot.CRUISE_SPEED);
        m_robot.motorLeft.rotate(Utils.robotDistanceToMotorAngle(- x, m_robot), true);
        m_robot.motorRight.rotate(Utils.robotDistanceToMotorAngle(- x, m_robot), false);
    }

    /**
     * Rotates the robot to the desired angle using the optimal angle and direction
     *
     * Positive angle goes counterCW
     * @param theta the desired angle to rotate to
     */
    public void rotateTo(double theta){

        //Theta must be in degrees

        //implementation of slide 13 in navigation tutorial
        double thetaCurrent = m_Odometer.getThetaInDegrees();

        double rotationAngle = computeOptimalRotationAngle(thetaCurrent, theta);

        RConsole.println("Angle to rotate! = " + Double.toString(rotationAngle));

        m_robot.motorLeft.setAcceleration(5000);
        m_robot.motorRight.setAcceleration(5000);
        m_robot.motorLeft.setSpeed(Robot.ROTATE_SPEED + 150);
        m_robot.motorRight.setSpeed(Robot.ROTATE_SPEED + 150);

        if (Math.abs(rotationAngle) < 3) {
            m_robot.motorLeft.rotate((rotationAngle > 0 ? -3 : 3), true);
            m_robot.motorRight.rotate((rotationAngle > 0 ? 3 : -3), false);
        } else {
            int angle = Utils.robotRotationToMotorAngle(rotationAngle, m_robot);
            m_robot.motorLeft.rotate(-angle, true);
            m_robot.motorRight.rotate(angle, false);
        }

        RConsole.println("\nTheta destination = " + Double.toString(theta));
        RConsole.println("Theta current = " + Double.toString(m_Odometer.getThetaInDegrees()));
    }

    /**
     * rotate the physical robot 90 degrees counterclockwise
     */
    public void rotate90CounterClock() {
        rotateTo(m_Odometer.getThetaInDegrees() + 90);
    }

    /**
     * rotate the physical robot 90 degrees clockwise
     */
    public void rotate90ClockWise() {
        rotateTo(m_Odometer.getThetaInDegrees() - 90);
    }

    public void rotate10ClockWise() {
        rotateTo(m_Odometer.getThetaInDegrees() - 10);
    }

    /**
     * Check if the robot is travelling
     * @return is the robot travelling
     */
    public boolean isNavigating(){
        return m_robot.motorLeft.isMoving() || m_robot.motorRight.isMoving();
    }

    public boolean isMovingForward() {
        return movingForward;
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
    public boolean closeEnough(double x, double y) {
        RConsole.println("Entering close enough x,y");
        return Math.abs(x - m_Odometer.getX()) < Robot.ACCEPTABLE_LINEAR && Math.abs(y - m_Odometer.getY()) < Robot.ACCEPTABLE_LINEAR;
    }

    /**
     * Check if the orientation of the robot is within an acceptable range of the specified angle
     * @param theta target orientation
     * @return boolean true if in acceptable range
     * */
    public boolean closeEnough(double theta) {
        return Math.abs(theta - m_Odometer.getThetaInDegrees()) <= Robot.ACCEPTABLE_ANGLE;
    }

    /**
     * Check if the coordinates of the robot are within an acceptable range to those specified
     * @param coordinate the target coordinate
     * @return boolean true if in acceptable range
     */
    public boolean closeEnough(Coordinate coordinate) {
        return Math.abs(coordinate.getX() - m_Odometer.getX()) < Robot.ACCEPTABLE_LINEAR && Math.abs(coordinate.getY() - m_Odometer.getY()) < Robot.ACCEPTABLE_LINEAR;
    }

    /**
     * Compute the optimal way to get from angle a to angle b
     * @param currentTheta current heading
     * @param desiredTheta desired heading
     * @return the signed number of degrees to rotate, sign indicated direction
     */
    public static double computeOptimalRotationAngle(double currentTheta, double desiredTheta){
        //implementation of slide 13 in navigation tutorial

        float pi = 180;

        if (desiredTheta - currentTheta < - pi){
            return (desiredTheta - currentTheta) + 2 * pi;
        } else if (desiredTheta - currentTheta > pi){
            return desiredTheta - currentTheta - 2 * pi;
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
            //Calcualte the magnitude of the vector using Pythagoras
            vector.setMagnitude(Math.sqrt(destination[0] * destination[0] - 2 * destination[0] * currentPosition[0] + currentPosition[0] * currentPosition[0] + destination[1] * destination[1] - 2 * destination[1] * currentPosition[1] + currentPosition[1] * currentPosition[1]));

            double x = destination[0] - currentPosition[0];
            double y = destination[1] - currentPosition[1];


            //Note: orientation is stored in radians!
            if (x>=0) {
                vector.setOrientation(Math.atan((y) / (x)));
            } else if (x<0 && y>0){
                vector.setOrientation(Math.atan((y) / (x)) + Math.PI);
            } else if (x<0 && y<0){
                vector.setOrientation((Math.atan((y) / (x)) - Math.PI));
            }
        }
        return vector;
    }


}//end Navigation