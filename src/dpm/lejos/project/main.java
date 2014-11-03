package dpm.lejos.project;

import lejos.nxt.Button;

/**
 * Main executable
 *
 * Targeted to the master brick
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Main {

	public static void main(String [] argv){

        Robot robot = new Robot();

        Odometer odometer = new Odometer(robot);
        OdometryDisplay display = new OdometryDisplay(odometer);
        Navigation navigation = new Navigation(robot);

        odometer.start();
        display.start();

        navigation.moveForward();
        navigation.rotate90ClockWise();
        navigation.moveForward();
        navigation.rotate90ClockWise();
        navigation.moveForward();
        navigation.rotate90ClockWise();
        navigation.moveForward();
        navigation.rotate90ClockWise();

        Button.waitForAnyPress();

        System.exit(0);
    }

}//end Main