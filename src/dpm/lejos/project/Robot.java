package dpm.lejos.project;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Node;
import dpm.lejos.orientation.Orienteering.*;
import dpm.lejos.orientation.Tile;
import lejos.nxt.*;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.remote.RemoteMotor;
import lejos.nxt.remote.RemoteNXT;
import lejos.util.Delay;

import java.io.IOException;

/**
 * Provides access to all the robots sensors and actuators
 *
 * Holds all the parameters and constants relevant to the whole system
 *
 * Port Mapping:
 * Master: |   A   |   B   |   C   |
 *           left    right   claw
 *           wheel   wheel   close
 *
 *         |   1   |  2   |  3   |  4  |
 *          color  color    us    us
 *          left   right   left  right
 *
 *
 * Slave: |   A   |   B   |   C   |
 *           claw
 *           lift
 *
 *        |  1   |   2   |  3   |  4  |
 *          us      touch
 *          front   claw
 *
 * @author David Lavoie-Boutin
 * @version 1.5
 */
public class Robot {

	public final int ACCELERATION = 2500;
	public final int CLAW_SPEED = 200;
    //TODO: decide actual cruise speed - seems too slow
    public final int CRUISE_SPEED = 250;
    public final int ROTATE_SPEED = 150;

    public int clawLowerDistance = 470;
    public int clawPrepare = -360;
    public int clawCloseAngle = -250;
    public double wheelBase = 12.5; //TODO : Continue to tweak
    public double wheelRadius = 2.02; //TODO: Still need to calibrate
    public double lightSensorOffset = 5; //TODO : Get real value


    public final double ACCEPTABLE_ANGLE = 1.00;
    public final double ACCEPTABLE_LINEAR = 1.00;

    private Direction direction = null;
    private Coordinate positionOnGrid;
    //the plane is used for localization
    private Tile[][] plane;

    //the plane graph is used for navigation
    private Node[][] planeGraph;

    public int LIGHT_THRESHOLD = 75;
    public double tileLength = 30;
    public int ODOMETER_PERIOD = 100;

    /**
     * motor for lifting and lowering the arms
     */
    public RemoteMotor clawLift;
    /**
     * motor for opening and closing the grabbing mechanism
     */
    public NXTRegulatedMotor clawClose;

    /**
     * touch sensor on the grabbing mechanism
     */
	public TouchSensor clawTouch;

    public NXTRegulatedMotor motorLeft;
	public NXTRegulatedMotor motorRight;

    /**
     * Secondary nxt providing additional I/O ports
     */
    public RemoteNXT slave;

    /**
     * The front facing ultrasonic sensor
     */
    public UltrasonicSensor usFront;
    /**
     * The port facing ultrasonic sensor
     */
	public UltrasonicSensor usLeft;
    /**
     * The starboard facing ultrasonic sensor
     */
	public UltrasonicSensor usRight;

	/**
	 * Color sensor facing down for odometry correction
	 */
    public ColorSensor colorSensorLeft;
    public ColorSensor colorSensorRight;

    /**
     * default constructor
     *
     * no parameters, all defaults should be initialized in this class
     */
    public Robot(){
         try {
            LCD.clear();
            LCD.drawString("Connecting...",0,0);
            NXTCommConnector connector = Bluetooth.getConnector();
            slave = new RemoteNXT("Optimus", connector);
            LCD.clear();
            LCD.drawString("Connected",0,1);
            Sound.systemSound(false, 1);
            Delay.msDelay(500);
        } catch (IOException e) {
            LCD.clear();
            LCD.drawString("Failed",0,0);
            Sound.systemSound(false, 4);
            Delay.msDelay(2000);
            System.exit(1);
        }

        clawLift = slave.A;
        clawClose = Motor.C;
        clawTouch = new TouchSensor(slave.S2);

        usFront = new UltrasonicSensor(slave.S1);
        usLeft = new UltrasonicSensor(SensorPort.S3);
        usRight = new UltrasonicSensor(SensorPort.S4);
        colorSensorLeft = new ColorSensor(SensorPort.S1);
        colorSensorRight = new ColorSensor(SensorPort.S2);

        motorLeft = Motor.A;
        motorRight = Motor.B;
        clawClose = Motor.B;
    }

    /**
     * returns the graph used for navigation
     * @return
     */
    public Node[][] getPlaneGraph() {
        return planeGraph;
    }

    /**
     * sets the graph used for navigation
     * @param planeGraph
     */
    public void setPlaneGraph(Node[][] planeGraph) {
        this.planeGraph = planeGraph;
    }

    /**
     * returns the plane object used for localization
     * and navigation purposes
     * @return
     */
    public Tile[][] getPlane() {
        return plane;
    }

    /**
     * Sets the plane object used for localization
     * and navigation purposes
     * @param plane
     */
    public void setPlane(Tile[][] plane) {
        this.plane = plane;
    }

    /**
     * returns the coordinate represeting the position of the robot
     * on the grid
     * @return
     */
    public Coordinate getPositionOnGrid() {
        return positionOnGrid;
    }

    /**
     * sets the coordinate representing the position of the robot
     * on the grid
     * @param positionOnGrid
     */
    public void setPositionOnGrid(Coordinate positionOnGrid) {
        this.positionOnGrid = positionOnGrid;
    }

    /**
     * get the current direction the robot is looking at
     * @return
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Modify the direction the robot is looking at
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}//end Robot