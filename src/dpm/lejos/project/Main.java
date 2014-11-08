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
        Navigation navigation = new Navigation(robot, odometer);
        Orienteering orienteering = new Orienteering(robot, navigation);
        Grabber grabber = new Grabber(robot);

        MissionPlanner missionPlanner = new MissionPlanner(navigation, grabber, orienteering, odometer, display);

        missionPlanner.demoMission();

        System.exit(0);
    }

}//end Main