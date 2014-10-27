package dpm.lejos.project;

import lejos.nxt.ColorSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public class Odometer extends Thread{

	private final ColorSensor centerLS;
	private final NXTRegulatedMotor portMotor;
	private final NXTRegulatedMotor stbdMotor;
	private double theta = 0;
	private double x = 0;
	private double y = 0;
	private LineDetector m_LineDetector;

    private final Object lock;
    private static Robot m_robot;

    /**
     * constructor for the odometer
     * @param robot robot object containing initialized drive motors and light sensors
     */
	public Odometer(Robot robot){
        m_robot = robot;
        centerLS = m_robot.colorSensor;
        portMotor = m_robot.motorPort;
        stbdMotor = m_robot.motorStrb;
        lock = new Object();
	}

    /**
     * runnable method overriding the thread method.
     *
     * This method will be ran in a separate thread
     * continuously polling the tachometers and
     * integrating the instantaneous speed to get
     * the total linear and angular displacement
     */
    public void run(){

    }

	/**
	 * This method is the single entry point
     * to get the position of the robot.
  	 */
	public double [] getPosition(){
        synchronized (lock) {
            return new double[]{x, y, theta};
        }
	}

	/**
	 * Single point of entry to set
     * the position of the odometer
	 *
     * @param position array containing the position
     *                 of the robot
	 */
	public void setPosition(double[] position){
        if (position.length != 3) return;
        synchronized (lock){
            x = position[0];
            y = position[1];
            theta = position[2];
        }
	}
}//end Odometer