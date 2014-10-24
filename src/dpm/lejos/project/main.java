package System;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 11:26:30
 */
public class Main {

	public static UltrasonicSensor frontUS;
	public static NXTRegulatedMotor portMotor = Motor.A;
	public static UltrasonicSensor portUS;
	public static NXTRegulatedMotor strbMotor = Motor.B;
	public static UltrasonicSensor strbUS;
	public Odometer m_Odometer;
	public MissionPlanner m_MissionPlanner;

	public Main(){

	}

	public void finalize() throws Throwable {

	}
}//end Main