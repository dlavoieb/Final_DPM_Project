package dpm.lejos.project;

import dpm.lejos.orientation.Orienteering;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import lejos.util.ButtonCounter;

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
       // RConsole.openUSB(15000);

        int buttonChoice;
        int map = 0;
        int dropOffX = 0;
        int dropOffY = 0;
        LCD.clear();
        LCD.drawString("Right Selects \nMap", 0,3);
        ButtonCounter counter = new ButtonCounter();
        counter.count();
        map = counter.getRightCount();
        LCD.clear();
        LCD.drawString("dropX  dropY", 0,0);
        counter.count();
        dropOffX = counter.getRightCount();
        counter.count();
        dropOffY = counter.getRightCount();

        do {
            LCD.clear();
            LCD.drawString("MAP " + map, 0, 1);
            LCD.drawString("XP " + dropOffX, 0, 2);
            LCD.drawString("YP " + dropOffY, 0, 3);
            buttonChoice = Button.waitForAnyPress();
        } while (buttonChoice != Button.ID_ENTER);


        //Initializes the fundamental systems needed to accomplish the task.
        Robot robot = new Robot();
        Odometer odometer = new Odometer(robot);
        SystemDisplay display = new SystemDisplay(odometer);
        RConsole.println("Intiated odometer and display");
        Navigation navigation = new Navigation(robot, odometer, map);
        RConsole.println("Intiated navigation");
        Orienteering orienteering = new Orienteering(robot, navigation);
        RConsole.println("Intiated orienteering");
        Grabber grabber = new Grabber(robot);
        BlockDetection blockDetection = new BlockDetection(robot, odometer, navigation);
        RConsole.println("Intiated blockdetection");

        MissionPlanner missionPlanner = new MissionPlanner(navigation, grabber, orienteering, odometer, display, blockDetection, robot,
                dropOffX, dropOffY);

        missionPlanner.startMission();

        System.exit(0);
    }

}