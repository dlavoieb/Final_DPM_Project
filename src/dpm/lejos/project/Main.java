package dpm.lejos.project;

import dpm.lejos.orientation.Orienteering;
import lejos.nxt.Button;
import lejos.nxt.comm.RConsole;

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
        Orienteering orienteering = new Orienteering(robot, navigation);


        orienteering.virtualDeterministicPositioning();
//        display.start();
//        odometer.start();
//
//        navigation.moveForward();
//        navigation.rotate90ClockWise();
//        navigation.moveForward();
//        navigation.rotate90ClockWise();
//        navigation.moveForward();
//        navigation.rotate90ClockWise();
//        navigation.moveForward();
//        navigation.rotate90ClockWise();
        while (Button.waitForAnyPress() != Button.ID_ESCAPE){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

}//end Main