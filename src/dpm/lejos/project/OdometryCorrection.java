package dpm.lejos.project;

import lejos.nxt.comm.RConsole;

import java.util.Arrays;

/**
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class OdometryCorrection extends Thread{
    private Odometer odo;
    private Robot robot;
    private Navigation navigation;

    private double [] last;

    private double[] now;

    private enum Side {LEFT, RIGHT}

    public boolean stopOBcorrection;


    public OdometryCorrection(Odometer odo, Robot robot, Navigation navigation){
        this.odo = odo;
        this.robot = robot;
        this.navigation = navigation;
    }

    public double calculate(Side side){
        double xDist = now[0] - last[0];
        double yDist = now[1] - last[1];

        double position = Math.sqrt(xDist * xDist +yDist * yDist);

        switch (side) {
            case LEFT:
                return (-Math.atan2(position, Robot.lsDistance));
            case RIGHT:
                return (Math.atan2(position, Robot.lsDistance));
            default:
                return -1;
        }
    }

    public double getNewTheta(double theta){
        double oldTheta = odo.getThetaInDegrees();
        double newTheta = 0;

        if      (oldTheta >= -45  && oldTheta <= 45)   newTheta = theta;
        else if (oldTheta >= 45   && oldTheta <= 135)  newTheta = Math.PI/2.0 + theta;
        else if (oldTheta >= -135 && oldTheta <= -45)  newTheta = - Math.PI/2.0 + theta;
        else if (oldTheta >= 135  || oldTheta <= -135) {
            newTheta = Math.PI + theta;
            if (newTheta > Math.PI) {
                newTheta -= 2 * Math.PI;
            }
        }

        return newTheta;
    }

    private void stopMotors(){
        robot.motorLeft.stop();
        robot.motorRight.stop();
    }

    public void run(){
        double newTheta;

        while(true) {
            if (navigation.isMovingForward()) {
                //Right sensor passes first
                if (odo.isRightLine()) {
                    RConsole.println("--\nCorrecting Odometry\tRight sensor first\n--");
                    last = odo.getPosition();
                    RConsole.println(Arrays.toString(last));

                    //Wait for left Sensor
                    while (true) {
                        if (odo.isLeftLine()) {
                            now = odo.getPosition();
                            RConsole.println(Arrays.toString(now));

                            double intermediateTheta = calculate(Side.RIGHT);
                            newTheta = getNewTheta(intermediateTheta);
                            RConsole.println("New Theta: " + String.valueOf(Math.toDegrees(newTheta)));
                            sleep(20);

                            odo.setTheta(newTheta);

                            while (odo.isLeftLine()) {
                                sleep(20);
                            }

                            if (Math.abs(now[2] - newTheta) > Math.toRadians(Robot.CORRECTION_THRESHOLD)) {
                                stopMotors();
                            }

                            last = now = null;

                            break;
                        }
                    }
                }
                //Left Sensor passes first
                if (odo.isLeftLine()) {
                    RConsole.println("--\nCorrecting Odometry\tLeft sensor first\n--");
                    last = odo.getPosition();
                    RConsole.println(Arrays.toString(last));

                    //Wait for right Sensor
                    while (true) {
                        if (odo.isRightLine()) {
                            now = odo.getPosition();
                            RConsole.println(Arrays.toString(now));

                            newTheta = calculate(Side.LEFT);

                            RConsole.println("Computed Theta: " + String.valueOf(Math.toDegrees(newTheta)));
                            newTheta = getNewTheta(newTheta);
                            RConsole.println("New Theta: " + String.valueOf(Math.toDegrees(newTheta)));

                            sleep(20);

                            while (odo.isRightLine()) {
                                sleep(20);
                            }

                            odo.setTheta(newTheta);

                            if (Math.abs(now[2] - newTheta) > Math.toRadians(Robot.CORRECTION_THRESHOLD)) {
                                stopMotors();
                                RConsole.println("Stop Motors, closeEnough should do the rest");
                            }

                            last = now = null;

                            break;
                        }
                    }
                }

                if (stopOBcorrection){
                    double theta = odo.getThetaInDegrees();
                    if ((theta >= -45 && theta <= 45) || (theta >= 135 || theta <= -135)) {
                        //travelling along the x axis
                        if (Math.abs(odo.getY() % (Robot.tileLength / 2.0)) < Robot.acceptableSideError) {
                            stopOBcorrection = false;
                        }

                    } else if ((theta >= 45 && theta <= 135) || (theta >= -135 && theta <= -45)) {
                        //travelling in the y axis
                        if (Math.abs(odo.getX() % (Robot.tileLength / 2.0)) < Robot.acceptableSideError) {
                            stopOBcorrection = false;
                        }
                    }
                }

                //check for side error
                if (!stopOBcorrection) {
                    double theta = odo.getThetaInDegrees();
                    if ((theta >= -45 && theta <= 45) || (theta >= 135 || theta <= -135)) {
                        //travelling along the x axis
                        if (Math.abs(odo.getY() % (Robot.tileLength / 2.0)) > Robot.acceptableSideError) {
                            RConsole.println("travelling along the x axis\n bigger difference");
                            stopMotors();
                            stopOBcorrection = true;
                        }

                    } else if ((theta >= 45 && theta <= 135) || (theta >= -135 && theta <= -45)) {
                        //travelling in the y axis
                        if (Math.abs(odo.getX() % (Robot.tileLength / 2.0)) > Robot.acceptableSideError) {
                            stopMotors();
                            stopOBcorrection = true;
                        }
                    }
                }

                sleep(10);
            }
            else {
                sleep(1000);
            }
        }
    }

    public void setNavigation(Navigation navigation){
        this.navigation = navigation;
    }


    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
