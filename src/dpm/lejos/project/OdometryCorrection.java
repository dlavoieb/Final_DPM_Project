package dpm.lejos.project;

import lejos.nxt.LCD;
import lejos.nxt.Sound;

/**
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class OdometryCorrection extends Thread{
    private Odometer odo;


    private static final double WIDTH = 8.6;

    private static final int LINE = 490;

    private double xLast,yLast;

    private double x,y;

    public OdometryCorrection(Odometer odo){
        this.odo = odo;
    }


    public double calculate(boolean right){
        double xDist = x - xLast;
        double yDist = y - yLast;
        double result;

        double position = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

        if(right){
            result = Math.PI/2 + Math.atan2(position, WIDTH);
        }
        else{
            result = Math.PI/2 - Math.atan2(position, WIDTH);
        }

        return result;

    }

    public double getNewTheta(double theta){
        double oldTheta = Math.toDegrees(odo.getThetaNormalized());
        double newTheta = 0;

        if (oldTheta >= 45 || oldTheta <= 135){
            newTheta = theta;
        } else if (oldTheta >= 315 && oldTheta <= 45){
            newTheta = Math.PI/2 + theta;
        } else if (oldTheta >= 225 || oldTheta <= 315){
            newTheta = Math.PI + theta;
        } else if (oldTheta >= 135 && oldTheta <= 225){
            newTheta = Math.PI*(3/2) + theta;
        }
        return newTheta;
    }

    public void run(){
        double newTheta = 0;
        int rightValue = odo.getRightLight();
        int leftValue = odo.getLeftLight();

        while(true){
            //Right sensor passes first
            LCD.drawInt(odo.getRightLight(), 0, 5);

            if(odo.getRightLight() < LINE){
                Sound.beep();
                yLast = odo.getY();
                xLast = odo.getX();

                //Wait for left Sensor
                while(true){
                    if(odo.getLeftLight() < LINE){
                        y = odo.getY();
                        x = odo.getX();

                        newTheta = calculate(true);

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                }
            }
            //Left Sensor passes first
            if(odo.getLeftLight() < LINE){
                yLast = odo.getY();
                xLast = odo.getX();

                //Wait for right Sensor
                while(true){
                    if(odo.getRightLight() < LINE){
                        y = odo.getY();
                        x = odo.getX();

                        newTheta = calculate(false);

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                }
            }
            while(rightValue < LINE && leftValue <LINE){
                odo.setTheta(getNewTheta(newTheta));
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}