package dpm.lejos.project;

import dpm.lejos.orientation.Mapper;
import dpm.lejos.orientation.Orienteering;
import lejos.nxt.Button;
import lejos.nxt.LCD;
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
        int i = 0;
        LCD.drawString("Choose your map", 0,0);

        while(true){
            LCD.drawString(String.valueOf(Mapper.MapID.values()[i% Mapper.MapID.values().length]), 0, 3);
            int option = Button.waitForAnyPress();
            if (option == Button.ID_LEFT){
                i+=1;
            } else if (option == Button.ID_RIGHT){
                i-=1;
            } else if (option == Button.ID_ENTER){
                break;
            }
        }
        Mapper.MapID mapID = Mapper.MapID.values()[i% Mapper.MapID.values().length];

        RConsole.openUSB(15000);
        Robot robot = new Robot();
        Odometer odometer = new Odometer(robot);
        SystemDisplay display = new SystemDisplay(odometer);
        Navigation navigation = new Navigation(robot, odometer, mapID);
        Orienteering orienteering = new Orienteering(robot, navigation, mapID);
        Grabber grabber = new Grabber(robot);
        BlockDetection blockDetection = new BlockDetection(robot, odometer, navigation);

        MissionPlanner missionPlanner = new MissionPlanner(navigation, grabber, orienteering, odometer, display, blockDetection, robot);

        missionPlanner.startMission();

        System.exit(0);
    }

}//end Main