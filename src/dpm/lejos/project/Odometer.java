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
    private Navigation navigation;
    private OdometryCorrection odometryCorrection;

	private NXTRegulatedMotor leftMotor;
	private NXTRegulatedMotor rightMotor;

    private LineDetector lineDetectorLeft;
    private LineDetector lineDetectorRight;

    private final Object lock;

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

        leftMotor = robot.motorLeft;
        rightMotor = robot.motorRight;
        leftMotor.resetTachoCount();
        rightMotor.resetTachoCount();

        theta = 0;
        x = 0;
        y = 0;

        prevTachoL = 0;
        prevTachoR = 0;
        lineDetectorLeft = new LineDetector(robot.colorSensorLeft, Robot.LIGHT_THRESHOLD, true);
        lineDetectorRight = new LineDetector(robot.colorSensorRight, Robot.LIGHT_THRESHOLD, true);
        odometryCorrection = new OdometryCorrection(this, robot, navigation);
    }

    /**
     * Runnable method overriding the thread method.
     *
     * This method will be ran in a separate thread to the
     * main execution thread, and it will
     * continuously poll the tachometers and
     * to calculate the position of the robot on the grid
     * at all times.
     */
    public void run() {
        long updateStart, updateEnd;

        while (true) {
            updateStart = System.currentTimeMillis();

            // Get variation in the tacho counts
            int tachoDeltaL = leftMotor.getTachoCount() - prevTachoL;
            int tachoDeltaR = rightMotor.getTachoCount() - prevTachoR;

            //convert tacho counts to travelled distance
            double dLeft = (Robot.wheelRadius * Math.PI * tachoDeltaL) / 180;
            double dRright = (Robot.wheelRadius * Math.PI * tachoDeltaR) / 180;
            double dCenter = (dLeft + dRright) /2;

            //use difference in distance to get angle
            double deltaTheta = (dRright - dLeft) / Robot.wheelBase;

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
            if (updateEnd - updateStart < Robot.ODOMETER_PERIOD) {
                try {
                    Thread.sleep(Robot.ODOMETER_PERIOD - (updateEnd - updateStart));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    /**
     * Initiates the odometer thread.
     */
    public void startCorrection() {
        this.odometryCorrection.start();
    }

	/**
	 * This method is the single entry point
     * to get the position of the robot.
     *
     * @return position array [x,y,theta]
  	 */
	public double [] getPosition(){
        synchronized (lock) {
            return new double[]{x, y, getThetaNormalized()};
        }
    }

    /**
     * Retrieves the current angle of orientation of the robot.
     * @return
     */
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

    public void setNavigation(Navigation navigation){
        this.navigation = navigation;
        this.odometryCorrection.setNavigation(navigation);
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