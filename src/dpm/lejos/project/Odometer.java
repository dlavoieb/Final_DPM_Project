package project;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public class Odometer {

	private final LightSensor centerLS;
	private final MXTRegulatedMotor portMotor;
	private final MXTRegulatedMotor stbdMotor;
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
	 * @param LightSensor
	 */
	public Odometer(NXTRegulatedMotor portMotor, NXTRegulatedMotor strbMotor, frontLS LightSensor){

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
	public void setPosition(doubl[] position){

	}
}//end Odometer