package dpm.lejos.project;

import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public class Odometer extends Thread{

	private final LightSensor centerLS;
	private final NXTRegulatedMotor portMotor;
	private final NXTRegulatedMotor stbdMotor;
	private double theta = 0;
	private double x = 0;
	private double y = 0;
	public LineDetector m_LineDetector;

	public Odometer(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param portMotor
	 * @param strbMotor
	 * @param frontLS
	 */
	public Odometer(NXTRegulatedMotor portMotor, NXTRegulatedMotor strbMotor, LightSensor frontLS ){

	}

	/**
	 * 
	 * @param position
	 */
	public void getPosition(double[] position){

	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(double[] position){

	}
}//end Odometer