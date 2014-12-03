package dpm.lejos.project;

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

    public static int CORRECTION_THRESHOLD = 5;
    public static int ACCELERATION = 6500;
    public static int CLAW_SPEED = 200;
    //TODO: decide actual cruise speed - seems too slow
    public static int CRUISE_SPEED = 200;
    public static int ROTATE_SPEED = 150;

    public static int clawLowerDistance = 470;
    public static int clawPrepare = -360;
    public static int clawCloseAngle = -250;
    public static double wheelBase = 12.5; //TODO : Continue to tweak
    public static double wheelRadius = 2.02; //TODO: Still need to calibrate
    public static double lsDistance = 11.7;

    public static double ACCEPTABLE_ANGLE = 1.00;
    public static double ACCEPTABLE_LINEAR = 1.00;
    static int acceptableSideError = 3;
    public static int DISTANCE_THRESHOLD = 30;

    public static int LIGHT_THRESHOLD = 10;
    public static double tileLength = 30;
    public static int ODOMETER_PERIOD = 100;

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
        clawClose = Motor.C;
        usLeft = new UltrasonicSensor(SensorPort.S3);
        usRight = new UltrasonicSensor(SensorPort.S4);
        colorSensorLeft = new ColorSensor(SensorPort.S1);
        colorSensorRight = new ColorSensor(SensorPort.S2);

        motorLeft = Motor.A;
        motorRight = Motor.B;
    }

    public  Robot (boolean test){}

}//end Robot