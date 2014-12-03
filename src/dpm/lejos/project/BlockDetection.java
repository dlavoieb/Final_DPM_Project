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
     * Initializes a BlockDetection instance used to detect and pickup
     * blocks.
     * @param robot the robot object
     * @param navigation the navigation reference
     * @param odometer  the odometer reference
     */
    public BlockDetection(Robot robot, Odometer odometer, Navigation navigation){
        this.m_robot = robot;
        this.m_Odometer = odometer;
        this.navigation = navigation;
    }

    /**
     * Algorithm used to locate and pick up blocks. It swipes the 4x4
     * pick up area at a set of arbitrary position with the claw deployed,
     * if the touch sensor closes then we have detected an object and can initate
     * the navigation procedure to the drop-off area.
     * @param grabber
     */
    public void lookForBlock(Grabber grabber) {
        int thirdOfTileMovementCount = 0;


        // Special instructions for the final map 1 where there is an
        // obstacle blocking the default procedure of the algorithm.
        if (navigation.mapper.mapID == Mapper.MapID.Final1) {
            grabber.deployArms();
            grabber.lowerClaw();
        } else {
            // If we are not in map1, then we move back before lowering
            // the claw/
            navigation.moveBackwardHalfATile();
            grabber.deployArms();
            grabber.lowerClaw();
            navigation.moveForwardHalfATile();
        }
        RConsole.println("Init block pickup");

        // We move forward a third of a tile, and then close
        // the claw, if we pickup a block then we break, otherwise
        // we open the claw and move forward again. This simple algorithm
        // reported a success rate of 80% during our testing trials.
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

        // Upon grabbing a block, we move back to the
        // starting position where the pick-up algorithm
        // was initiated
        while (thirdOfTileMovementCount > 0) {
            navigation.moveBackwardThirdOfATile();
            thirdOfTileMovementCount--;
        }
    }

    public void sleep(int delay) {
        try { Thread.sleep(delay); } catch (InterruptedException e) {e.printStackTrace();}
    }

}
