package dpm.lejos.project;

import lejos.nxt.*;
import lejos.nxt.remote.RemoteNXT;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public class Robot {

	public static final int ACCELERATION = 500;
	public static final int CLAW_SPEED = 200;
    public static final int CRUISE_SPEED = 200;
    public static final int ROTATION_SPEED = 150;

    public double wheelBase;
    public double wheelRadius;

    public static NXTRegulatedMotor clawActuator;
	public ColorSensor clawColor;
	public TouchSensor clawTouch;

    public static NXTRegulatedMotor motorPort;
	public static NXTRegulatedMotor motorStrb;

    public RemoteNXT slave;

    /**
     * The front facing ultrasonic sensor
     */
    public static UltrasonicSensor usFront;
    /**
     * The port facing ultrasonic sensor
     */
	public static UltrasonicSensor usPort;
    /**
     * The starboard facing ultrasonic sensor
     */
	public static UltrasonicSensor usStrb;

	/**
	 * Color sensor facing down for odometry correction
	 */
	public static ColorSensor colorSensor;

	public Robot(){
        this(SensorPort.S1, SensorPort.S2, SensorPort.S3);
	}

    /**
     * full constructor
     *
     * @param usFrontPort the port for the front facing ultrasonic sensor
     * @param usPortPort the port for the port facing ultrasonic sensor
     * @param usStrbPort the port for the starboard facing ultrasonic sensor
     * @param colorPort
     * @param clawColorPort
     * @param clawTouchPort
     * @param portMotorPort
     * @param strbMotorPort
     * @param clawMotorPort
     * @param remoteNXT
     */
    public Robot (SensorPort usFrontPort, SensorPort usPortPort, SensorPort usStrbPort, SensorPort colorPort, SensorPort clawColorPort, SensorPort clawTouchPort, MotorPort portMotorPort, MotorPort strbMotorPort, MotorPort clawMotorPort, RemoteNXT remoteNXT){
        slave = remoteNXT;

        usFront = new UltrasonicSensor(usFrontPort);
        usPort = new UltrasonicSensor(usPortPort);
        usStrb = new UltrasonicSensor(usStrbPort);
        colorSensor = new ColorSensor(colorPort);
        clawColor = new ColorSensor(clawColorPort);
        clawTouch = new TouchSensor(clawTouchPort);

        motorPort = new NXTRegulatedMotor(portMotorPort);
        motorStrb = new NXTRegulatedMotor(strbMotorPort);
        clawActuator = new NXTRegulatedMotor(clawMotorPort);


    }


	public void finalize() throws Throwable {

	}
}//end Robot