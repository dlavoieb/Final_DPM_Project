package dpm.lejos.project;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Orienteering.Direction;
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

	public static void main(String [] argv) throws InterruptedException {
        RConsole.openUSB(10000);
        Robot robot = new Robot();

        Odometer odometer = new Odometer(robot);
        //OdometryDisplay display = new OdometryDisplay(odometer);
        Navigation navigation = new Navigation(robot, odometer);
        Orienteering orienteering = new Orienteering(robot, navigation);
        //Grabber grabber = new Grabber(robot);

        robot.setPositionOnGrid(new Coordinate(1,0));
        robot.setDirection(Direction.EAST);

//        navigation.rotateToDirection(Direction.NORTH);
//        Thread.sleep(1000);
//        RConsole.println("looking north");
//        navigation.rotateToDirection(Direction.SOUTH);
//        Thread.sleep(1000);
//        RConsole.println("looking south");
//        navigation.rotateToDirection(Direction.EAST);
//        Thread.sleep(1000);
//        RConsole.println("looking east");
//        navigation.rotateToDirection(Direction.WEST);
//        RConsole.println("looking west");

        //orienteering.virtualDeterministicPositioning();
        navigation.travelTo(new Coordinate(1,1));
        //display.start();
        //odometer.start();

//        int option = 0;
//        while (option == 0)
//            option = Button.waitForAnyPress();
//        switch(option) {
//            case Button.ID_LEFT:
//                navigation.moveForward();
//                navigation.rotate90ClockWise();
//                navigation.moveForward();
//                navigation.rotate90ClockWise();
//                navigation.moveForward();
//                navigation.rotate90ClockWise();
//                navigation.moveForward();
//                navigation.rotate90ClockWise();
//            case Button.ID_RIGHT:
//                grabber.lowerClaw();
//                Button.waitForAnyPress();
//                grabber.riseClaw();
//            default:
//                System.out.println("Error - invalid button");
//                System.exit(-1);
//                break;
//        }

        System.exit(0);
    }

}//end Main