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

	private NXTRegulatedMotor leftMotor;
	private NXTRegulatedMotor rightMotor;
    private boolean correct = false;
    private LineDetector m_LineDetector;

    private final Object lock;
    private static Robot m_robot;


    //Odometer is encoded in radians!
    private double x, y, theta;
    private int prevTachoL, prevTachoR;

    /**
     * default constructor for unit tests
     *
     */
    public Odometer (){
        this.lock = new Object();
    }

    /**
     * constructor for the odometer
     * @param robot robot object containing initialized drive motors and light sensors
     */
	public Odometer(Robot robot){

        lock = new Object();
        m_robot = robot;

        leftMotor = m_robot.motorLeft;
        rightMotor = m_robot.motorRight;
        leftMotor.resetTachoCount();
        rightMotor.resetTachoCount();


        theta = 0;
        x = 0;
        y = 0;

        prevTachoL = 0;
        prevTachoR = 0;
        m_LineDetector = new LineDetector(m_robot, true);

    }

    public Odometer (Robot robot, boolean correct){
        this(robot);
        this.correct = correct;
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

            // Get variation in the tacho counts
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

            if (correct){
                correct();
            }

            // this ensures that the odometer only runs once every period
            updateEnd = System.currentTimeMillis();
            if (updateEnd - updateStart < m_robot.ODOMETER_PERIOD) {
                try {
                    Thread.sleep(m_robot.ODOMETER_PERIOD - (updateEnd - updateStart));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
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
            return new double[]{x, y, getThetaNormalized()};
        }
    }

    public double getThetaNormalized() {
        synchronized (lock) {
            if (theta < -Math.PI){
                return theta + ( 2 * Math.PI);
            } else if (theta > Math.PI){
                return theta - ( 2 * Math.PI);
            } else {
                return theta;
            }
        }
    }

    public double getX(){
        synchronized (lock) {
            return x;
        }
    }

    public double getY(){
        synchronized (lock) {
            return y;
        }
    }

    public double getThetaInDegrees(){
        synchronized (lock) {
            return Math.toDegrees(getThetaNormalized());
        }
    }

    public double getThetaRaw() {
        synchronized (lock) {
            return theta;
        }
    }

    public void startCorrection(){
        this.correct = true;
    }

    private void correct(){
        if (m_LineDetector.isLine()){
            if( Math.abs(Math.round(theta / 180.0) - (theta / 180.0)) < 0.1)
            // if angle is close enough to +/- 180
            {
                //moving in the x line
                x = Math.round( (x + m_robot.lightSensorOffset) / m_robot.tileLength) * m_robot.tileLength - m_robot.lightSensorOffset;
            }
            else
            {
                //moving in the y line
                y = Math.round( (y + m_robot.lightSensorOffset) / m_robot.tileLength) * m_robot.tileLength - m_robot.lightSensorOffset;
            }
        }
    }

	/**
	 * Single point of entry to set
     * the position of the odometer
	 *
     * @param position array containing the position
     *                 of the robot [x,y,theta]
	 */
	public void setPosition(double[] position){
        if (position.length != 3) return;
        synchronized (lock){
            x = position[0];
            y = position[1];
            theta = position[2];
        }
	}

    public void setX(double x) {
        synchronized (lock) {
            this.x = x;
        }
    }

    public void setY(double y) {
        synchronized (lock) {
            this.y = y;
        }
    }

    public void setThetaInDegrees(int theta) {
        this.theta = Math.toRadians(theta);
    }

    public void setTheta(double theta) {
        this.theta = theta % (2 * Math.PI);
    }
}//end Odometer