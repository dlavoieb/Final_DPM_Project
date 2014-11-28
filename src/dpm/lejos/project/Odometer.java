package dpm.lejos.project;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.comm.RConsole;

/**
 * Odometer class polls the tachometers from
 * the drive motors to estimate the current
 * position and heading
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Odometer extends Thread{
    private Navigation navigation;
    private OdometryCorrection odometryCorrection;

	private NXTRegulatedMotor leftMotor;
	private NXTRegulatedMotor rightMotor;

    private boolean correct = false;

    private LineDetector lineDetectorLeft;
    private LineDetector lineDetectorRight;

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
        lineDetectorLeft = new LineDetector(m_robot.colorSensorLeft, m_robot.LIGHT_THRESHOLD, true);
        lineDetectorRight = new LineDetector(m_robot.colorSensorRight, m_robot.LIGHT_THRESHOLD, true);
        odometryCorrection = new OdometryCorrection(this);
        odometryCorrection.start();
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

    public int getLeftLight(){
        return lineDetectorLeft.getPastLightValue();
    }

    public int getRightLight(){
        return lineDetectorRight.getPastLightValue();
    }

    public boolean isLeftLine() {
        return lineDetectorLeft.isLine();
    }

    public boolean isRightLine(){
        return lineDetectorRight.isLine();
    }

    public void startCorrection(){
        this.correct = true;
    }

    private void correct(){
        //logical XOR
        //correction only viable during linear travel
        if (navigation.isMovingForward() && (lineDetectorLeft.isLine() ^ lineDetectorRight.isLine())){
            RConsole.println("Correcting, one sensor not on line");
            //one sensor is on the line
            if (lineDetectorLeft.isLine()) {
                RConsole.println("Left on line");
                //left sensor on the line
                int speed = m_robot.motorLeft.getSpeed();
                while (!lineDetectorRight.isLine()){
                    RConsole.println("stop left");
                    //stop left wheel until other sensor reaches the line
                    //speed is 1 to fake the stop so the regulation thread does not erase the rotate command
                    m_robot.motorLeft.setSpeed(1);
                }
                RConsole.println("start left");
                m_robot.motorLeft.setSpeed(speed);
            }
            else {
                RConsole.println("Right on line");
                int speed = m_robot.motorRight.getSpeed();
                //right sensor on the line
                while (!lineDetectorLeft.isLine()){
                    RConsole.println("stop right");
                    //stop right wheel until other sensor reaches the line
                    //speed is 1 to fake the stop so the regulation thread does not erase the rotate command
                    m_robot.motorRight.setSpeed(1);
                }
                RConsole.println("start right");
                m_robot.motorRight.setSpeed(speed);
            }

            if( Math.abs(Math.round(theta / 180.0) - (theta / 180.0)) < 0.1)
            // if angle is close enough to +/- 180
            {
                //moving in the x line
                x = Math.round((x + m_robot.lightSensorOffset) / m_robot.tileLength) * m_robot.tileLength - m_robot.lightSensorOffset;
            } else {
                //moving in the y line
                y = Math.round( (y + m_robot.lightSensorOffset) / m_robot.tileLength) * m_robot.tileLength - m_robot.lightSensorOffset;
            }
        }
    }

    public void setNavigation(Navigation navigation){
        this.navigation = navigation;
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