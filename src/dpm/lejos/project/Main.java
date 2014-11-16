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

    public static void main(String [] argv){
        RConsole.openUSB(15000);

        Button.waitForAnyPress();

        Robot robot = new Robot();

        Odometer odometer = new Odometer(robot);
        OdometryDisplay display = new OdometryDisplay(odometer);
        Navigation navigation = new Navigation(robot, odometer, Mapper.MapID.LAB4);
        Orienteering orienteering = new Orienteering(robot, navigation);
        Grabber grabber = new Grabber(robot);

        MissionPlanner missionPlanner = new MissionPlanner(navigation, grabber, orienteering, odometer, display);

        //missionPlanner.odometryTest();
        //missionPlanner.localizationTest();
//        missionPlanner.navigationTest(robot);
        missionPlanner.localizationAndNavigationTest();

        System.exit(0);
    }

    public static void virtualNavigationTest(Robot robot, Navigation nav) {
        robot.setPositionOnGrid(new Coordinate(1,0));
        nav.travelTo(new Coordinate(0,3));
    }

}//end Main