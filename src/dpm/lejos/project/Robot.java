package project;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public class Robot {

	public final int ACCELERATION;
	public final int CLAW_SPEED;
	public static NXTRegulatedMotir clawActuator;
	public ColorSensor clawColor;
	public TouchSensor clawTouch;
	public final int CRUISE_SPEED;
	public static NXTRegulatedMotor motorPort;
	public static NXTRegulatedMotor motorStrb;
	public final int ROTATION_SPEED;
	public RemoteNXT slave;
	public static UltrasonicSensor usFront;
	public static UltrasonicSensor usPort;
	public static UltrasonicSensor usStrb;
	public double wheelBase;
	public double wheelRadius;
	/**
	 * Color sensor facing down for odometry correction
	 */
	public static ColorSensor colorSensor;

	public Robot(){

	}

	public void finalize() throws Throwable {

	}
}//end Robot