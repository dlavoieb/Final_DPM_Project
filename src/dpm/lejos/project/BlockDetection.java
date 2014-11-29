package dpm.lejos.project;

import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;

import java.util.Arrays;

/**
 * Created by danielmacario on 14-11-16.
 */
public class BlockDetection {

    private Odometer m_Odometer;
    private Robot m_robot;
    private Navigation navigation;

    /**
     * default constructor
     *
     * @param robot the robot object
     */
    public BlockDetection(Robot robot, Odometer odometer, Navigation navigation){
        this.m_robot = robot;
        this.m_Odometer = odometer;
        this.navigation = navigation;
    }

    public void lookForBlock(Grabber grabber) {
        grabber.deployArms();
        grabber.lowerClaw();
        RConsole.println("Init block pickup");
        navigation.moveForward();
        grabber.closeClaw();

        if (m_robot.clawTouch.isPressed()){
            RConsole.println("Picked the block!!!");
            grabber.riseClaw();
            navigation.rotate90ClockWise();
            navigation.rotate90ClockWise();
            navigation.moveForward();
        }

        RConsole.println("Pickup Failed!");
        grabber.openClaw();
        navigation.rotate90ClockWise();
        navigation.moveForward();
        grabber.closeClaw();
        grabber.riseClaw();


//        while (true) {
//            navigation.moveForward();
//            grabber.closeClaw();
//
//            if (m_robot.clawTouch.isPressed()){
//                RConsole.println("Picked the block!!!");
//                grabber.riseClaw();
//                break;
//            }
//
//            RConsole.println("Pickup Failed!");
//
//            grabber.openClaw();
//        }
//
//        int i = 0;
//        int distanceRecorded = 0;
//        boolean blockDetected = false;
//        while (i < 90) {
//            distanceRecorded = getFilteredData(m_robot.usFront);
//            RConsole.println("Distance to block = " + Integer.toString(distanceRecorded));
//            if (distanceRecorded >= 10 && distanceRecorded <= 15) {
//                blockDetected = true;
//                break;
//            }
//            i += 10;
//            navigation.rotate10ClockWise();
//        }
//
//        if (blockDetected) {
//            grabber.pickUpBlock();
//        }
//        lookForBlock(grabber);
    }

    /**
     * take five readings with the ultrasonic sensor
     * and return the median value
     * @return the filtered distance read with the us sensor
     * @param us
     */
    private int getFilteredData(UltrasonicSensor us) {
        int distance;
        int[] dist = new int[5];

        for (int i = 0; i < 5; i++) {

            us.ping();
            // wait for ping to complete
            //TODO: decide on a time for us readings
            sleep(25);
            // there will be a delay
            dist[i] = us.getDistance();

        }

        Arrays.sort(dist);
        distance = dist[2];

        //if (distance < DISTANCE_THRESHOLD)

        return distance;
    }

    public void sleep(int delay) {
        try { Thread.sleep(delay); } catch (InterruptedException e) {e.printStackTrace();}
    }

}
