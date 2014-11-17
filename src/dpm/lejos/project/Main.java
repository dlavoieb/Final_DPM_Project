package dpm.lejos.project;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Mapper;
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

    /*
    0,0    0,1    0,2   0,3

    1,0    1,1    1,2   1,3

    2,0    2,1    2,2   2,3

    3,0    3,1    3,2   3,3
    */

    public static void main(String [] argv){
        RConsole.openUSB(15000);

        Button.waitForAnyPress();

        Robot robot = new Robot();

        Odometer odometer = new Odometer(robot);
        OdometryDisplay display = new OdometryDisplay(odometer);
        Navigation navigation = new Navigation(robot, odometer, Mapper.MapID.LAB4);
        Orienteering orienteering = new Orienteering(robot, navigation, Mapper.MapID.LAB4);
        Grabber grabber = new Grabber(robot);
        BlockDetection blockDetection = new BlockDetection(robot, odometer, navigation);

        MissionPlanner missionPlanner = new MissionPlanner(navigation, grabber, orienteering, odometer, display, blockDetection);

//        missionPlanner.odometryTest();
//        missionPlanner.localizationTest();
        missionPlanner.navigationTest(robot);
//        missionPlanner.localizationAndNavigationTest();
//        missionPlanner.rotationTest(e);
//        missionPlanner.clawTest();
//        missionPlanner.blockDetectionTest();

        System.exit(0);
    }

    public static void virtualNavigationTest(Robot robot, Navigation nav) {
        robot.setPositionOnGrid(new Coordinate(1,0));
        nav.travelTo(new Coordinate(0,3));
    }

}//end Main