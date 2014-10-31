package dpm.lejos.project;

import lejos.nxt.NXTRegulatedMotor;

/**
 * Odometer class polls the tachometers from
 * the drive motors to estimate the current
 * position and heading
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Odometer extends Thread{

	private final NXTRegulatedMotor leftMotor;
	private final NXTRegulatedMotor rightMotor;
	private LineDetector m_LineDetector;

    private final Object lock;
    private static Robot m_robot;

    private double x, y, theta;
    private int prevTachoL, prevTachoR;

    /**
     * constructor for the odometer
     * @param robot robot object containing initialized drive motors and light sensors
     */
	public Odometer(Robot robot){

        lock = new Object();
        m_robot = robot;

        leftMotor = m_robot.motorPort;
        rightMotor = m_robot.motorStrb;
        leftMotor.resetTachoCount();
        rightMotor.resetTachoCount();


        theta = 0;
        x = 0;
        y = 0;

        prevTachoL = 0;
        prevTachoR = 0;
        m_LineDetector = new LineDetector(m_robot);

    }

    /**
     * runnable method overriding the thread method.
     *
     * This method will be ran in a separate thread
     * continuously polling the tachometers and
     * integrating the instantaneous speed to get
     * the total linear and angular displacement
     */
    public void run() {
        long updateStart, updateEnd;

        while (true) {
            updateStart = System.currentTimeMillis();
            // put (some of) your odometer code here
            //Get variation tacho
            int tachoDeltaL = leftMotor.getTachoCount() - prevTachoL;
            int tachoDeltaR = rightMotor.getTachoCount() - prevTachoR;

            //convert tacho counts to travelled distance
            double dLeft = (m_robot.wheelRadius * Math.PI * tachoDeltaL) / 180;
            double dRright = (m_robot.wheelRadius * Math.PI * tachoDeltaR) / 180;
            double dCenter = (dLeft + dRright) /2;

            //use difference in distance to get angle
            double deltaTheta = (dRright - dLeft) / m_robot.wheelBase;

            synchronized (lock) {
                //update prev tacho counts
                prevTachoL += tachoDeltaL;
                prevTachoR += tachoDeltaR;

                theta = (theta + deltaTheta) % (2 * Math.PI);

                //update current position
                x += dCenter * Math.cos(theta);
                y += dCenter * Math.sin(theta);
            }

            // this ensures that the odometer only runs once every period
            updateEnd = System.currentTimeMillis();
            if (updateEnd - updateStart < m_robot.ODOMETER_PERIOD) {
                try {
                    Thread.sleep(m_robot.ODOMETER_PERIOD - (updateEnd - updateStart));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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