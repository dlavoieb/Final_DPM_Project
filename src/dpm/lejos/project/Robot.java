package dpm.lejos.project;

import lejos.nxt.*;
import lejos.nxt.comm.RS485;
import lejos.nxt.remote.RemoteMotor;
import lejos.nxt.remote.RemoteNXT;
import lejos.util.Delay;

import java.io.IOException;

/**
 * Provides access to all the robots sensors and actuators
 * Holds all the parameters and constants relevant to the whole system
 * @author David Lavoie-Boutin
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
    public double lsOffset;

    public RemoteMotor clawLift;
    public RemoteMotor clawClose;

	public ColorSensor clawColor;
	public TouchSensor clawTouch;

    public NXTRegulatedMotor motorPort = Motor.A; //   <---
	public NXTRegulatedMotor motorStrb = Motor.B; //   <---

    /**
     * Secondary nxt providing additional I/O ports
     */
    public RemoteNXT slave;

    /**
     * The front facing ultrasonic sensor
     */
    public UltrasonicSensor usFront = new UltrasonicSensor(SensorPort.S1); //   <---
    /**
     * The port facing ultrasonic sensor
     */
	public UltrasonicSensor usPort = new UltrasonicSensor(SensorPort.S2);  //   <---
    /**
     * The starboard facing ultrasonic sensor
     */
	public UltrasonicSensor usStrb = new UltrasonicSensor(SensorPort.S3);  //   <---

	/**
	 * Color sensor facing down for odometry correction
	 */
	public ColorSensor colorSensor;




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
	}


}//end Robot