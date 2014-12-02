package dpm.lejos.project;

import dpm.lejos.orientation.Mapper;
import lejos.nxt.Button;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;

import java.util.Arrays;

/**
 *
 * @author Daniel Macario
 * @version v1.1
 */
public class BlockDetection {

    private Odometer m_Odometer;
    private Robot m_robot;
    private Navigation navigation;

    /**
     * default constructor
     *
     * @param robot the robot object
     * @param navigation the navigation reference
     * @param odometer  the odometer reference
     */
    public BlockDetection(Robot robot, Odometer odometer, Navigation navigation){
        this.m_robot = robot;
        this.m_Odometer = odometer;
        this.navigation = navigation;
    }

    public void lookForBlock(Grabber grabber) {
        int thirdOfTileMovementCount = 0;

        if (navigation.mapper.mapID == Mapper.MapID.Final1) {
            grabber.deployArms();
            grabber.lowerClaw();
        } else {
            navigation.moveBackwardHalfATile();
            grabber.deployArms();
            grabber.lowerClaw();
            navigation.moveForwardHalfATile();
        }
        RConsole.println("Init block pickup");

        while (true) {
            navigation.moveForwardThirdOfATile();
            thirdOfTileMovementCount++;
            grabber.closeClaw();

            if (m_robot.clawTouch.isPressed()){
                RConsole.println("Picked the block!!!");
                grabber.riseClaw();
                break;
            }

            grabber.openClaw();
            grabber.closeClaw();

            if (m_robot.clawTouch.isPressed()){
                RConsole.println("Picked the block!!!");
                grabber.riseClaw();
                break;
            }

            RConsole.println("Pickup Failed!");
            grabber.openClaw();
        }

        while (thirdOfTileMovementCount > 0) {
            navigation.moveBackwardThirdOfATile();
            thirdOfTileMovementCount--;
        }

//        navigation.moveForwardThirdOfATile();
//        thirdOfTileMovementCount++;
//        grabber.closeClaw();
//        grabber.openClaw();
//        grabber.closeClaw();
//
//
//        RConsole.println("Pickup Failed!");
//        grabber.openClaw();
//        navigation.moveForwardThirdOfATile();
//        grabber.closeClaw();
//        grabber.openClaw();
//        grabber.closeClaw();
//
//        if (m_robot.clawTouch.isPressed()){
//            RConsole.println("Picked the block!!!");
//            grabber.riseClaw();
//            navigation.rotate90ClockWise();
//            navigation.rotate90ClockWise();
//            navigation.moveForwardHalfATile();
//            navigation.rotate90ClockWise();
//            navigation.moveForward();
//            Button.waitForAnyPress();
//        }
//
//        RConsole.println("Pickup Failed!");
//        grabber.openClaw();
//        navigation.moveForwardThirdOfATile();
//        grabber.closeClaw();
//        grabber.openClaw();
//        grabber.closeClaw();
//
//        if (m_robot.clawTouch.isPressed()){
//            RConsole.println("Picked the block!!!");
//            grabber.riseClaw();
//            navigation.rotate90ClockWise();
//            navigation.rotate90ClockWise();
//            navigation.moveForwardHalfATile();
//            navigation.rotate90ClockWise();
//            navigation.moveForward();
//            Button.waitForAnyPress();
//        }
//
//        if (m_robot.clawTouch.isPressed()){
//            RConsole.println("Picked the block!!!");
//            grabber.riseClaw();
//            navigation.rotate90ClockWise();
//            navigation.rotate90ClockWise();
//            navigation.moveForwardHalfATile();
//            navigation.rotate90ClockWise();
//            navigation.moveForward();
//            Button.waitForAnyPress();
//        }
//
//
//        navigation.moveBackwardHalfATile();
//        RConsole.println("Pickup Failed!");
//        grabber.openClaw();
//        navigation.moveForwardThirdOfATile();
//        grabber.closeClaw();
//        grabber.openClaw();
//        grabber.closeClaw();


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
