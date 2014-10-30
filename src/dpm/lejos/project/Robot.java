package dpm.lejos.project;

import lejos.nxt.*;
import lejos.nxt.comm.RS485;
import lejos.nxt.remote.RemoteMotor;
import lejos.nxt.remote.RemoteNXT;
import lejos.util.Delay;

import java.io.IOException;

/**
 * Provides access to all the robots sensors and actuators
 *
 * Holds all the parameters and constants relevant to the whole system
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Robot {

	public static final int ACCELERATION = 500;
	public static final int CLAW_SPEED = 200;
    public static final int CRUISE_SPEED = 200;
    public static final int ROTATION_SPEED = 150;

    public double wheelBase = 10; //TODO : Get real value
    public double wheelRadius = 2; //TODO : Get real value
    public double lightSensorOffset = 5; //TODO : Get real value

    public int LIGHT_THRESHOLD = 500;

    /**
     * motor for lifting and lowering the arms
     */
    public RemoteMotor clawLift;
    /**
     * motor for opening and closing the grabbing mechanism
     */
    public RemoteMotor clawClose;

    /**
     * color sensor on the grabbing mechanism
     */
	public ColorSensor clawColor = new ColorSensor(SensorPort.S3);
    /**
     * touch sensor on the grabbing mechanism
     */
	public TouchSensor clawTouch = new TouchSensor(SensorPort.S1);

    public NXTRegulatedMotor motorPort = Motor.A; //   <---
	public NXTRegulatedMotor motorStrb = Motor.B; //   <---

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
	public UltrasonicSensor usPort;
    /**
     * The starboard facing ultrasonic sensor
     */
	public UltrasonicSensor usStrb;

	/**
	 * Color sensor facing down for odometry correction
	 */
	public ColorSensor colorSensor = new ColorSensor(SensorPort.S2);

    /**
     * default constructor
     *
     * no parameters, all defaults should be initialized in this class
     */
    public Robot(){
        try {
            LCD.clear();
            LCD.drawString("Connecting...",0,0);
            slave = new RemoteNXT("TEAM08-2", RS485.getConnector());
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
        clawClose = slave.B;

        usFront = new UltrasonicSensor(slave.S1);
        usPort = new UltrasonicSensor(slave.S2);
        usStrb = new UltrasonicSensor(slave.S3);

    }


}//end Robot