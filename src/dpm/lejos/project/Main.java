package dpm.lejos.project;

import dpm.lejos.orientation.Mapper;
import dpm.lejos.orientation.Orienteering;
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
        Robot robot = new Robot();
        Odometer odometer = new Odometer(robot);
        SystemDisplay display = new SystemDisplay(odometer);
        Navigation navigation = new Navigation(robot, odometer, Mapper.MapID.Lab5);
        Orienteering orienteering = new Orienteering(robot, navigation, Mapper.MapID.Lab5);
        Grabber grabber = new Grabber(robot);
        BlockDetection blockDetection = new BlockDetection(robot, odometer, navigation);

        MissionPlanner missionPlanner = new MissionPlanner(navigation, grabber, orienteering, odometer, display, blockDetection, robot);

        missionPlanner.startMission();

        System.exit(0);
    }

}//end Main