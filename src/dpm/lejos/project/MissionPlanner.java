package dpm.lejos.project;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Orienteering;
import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;

/**
 * High level mission planning.
 *
 * Implements the over all strategy of the application. This class
 * Manages the high level switching between the subsystems needed to
 * accomplish the task.
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class MissionPlanner {

    private final Odometer odometer;
    private final SystemDisplay display;
    private final Navigation m_Navigation;
    private final Grabber m_Grabber;
    private final Orienteering orienteering;
    private final BlockDetection blockDetection;
    private final Robot robot;
    private int dropOffX;
    private int dropOffY;

    /**
     * Initializes a MissionPlanner instance, used to complete the search and rescue task.
     * @param navigation Navigation instance used to travel between tiles.
     * @param grabber Grabber instance used to locate and pick-up blocks
     * @param orienteering Orienteering instance used to localize the robot on the grid.
     * @param odometer Odometer instance used to keep track of the position of the robot
     *                 on the coordinate system.
     * @param display Display instance used to print to the LCD screen of the master brick.
     * @param blockDetection Block detection instance used to determine the location of
     *                       blocks on the pick-up area.
     * @param robot Robot instance used to control the movement of the robot.
     * @param dropOffX X coordinate where the grabbed blocks should be dropped.
     * @param dropOffY Y coordinate where the grabbed blocks should be dropped.
     */
    public MissionPlanner(Navigation navigation, Grabber grabber, Orienteering orienteering, Odometer odometer, SystemDisplay display,
                          BlockDetection blockDetection, Robot robot, int dropOffX, int dropOffY) {

        this.m_Navigation = navigation;
        this.m_Grabber = grabber;
        this.orienteering = orienteering;
        this.odometer = odometer;
        this.display = display;
        this.blockDetection = blockDetection;
        this.robot = robot;
        this.dropOffX = dropOffX;
        this.dropOffY = dropOffY;
    }

    /**
     * perform the mission
     *
     * steps:
     *
     * 1- Localise within the playground
     * 2- Navigate to pickup zone
     * 3- Pickup blocks
     * 4- Navigate to drop off area
     * 5- Drop blocks
     */
    public void startMission(){
        completeTask();
    }

    public void odoCorrectionTest(){
        odometer.startCorrection();
        odometer.start();
        display.start();
        m_Navigation.travelTo(60,0);
        Button.waitForAnyPress();
    }

    public void demoMission(){
        display.start();
        odometer.start();

        int option = 0;
        while (option == 0)
            option = Button.waitForAnyPress();
        switch(option) {
            case Button.ID_LEFT:
                m_Navigation.moveForward();
                m_Navigation.rotate90ClockWise();
                m_Navigation.moveForward();
                m_Navigation.rotate90ClockWise();
                m_Navigation.moveForward();
                m_Navigation.rotate90ClockWise();
                m_Navigation.moveForward();
                m_Navigation.rotate90ClockWise();
            case Button.ID_RIGHT:
                m_Grabber.lowerClaw();
                Button.waitForAnyPress();
                m_Grabber.riseClaw();
            default:
                System.out.println("Error - invalid button");
                System.exit(-1);
                break;
        }
    }

    /**
     * Protocol used to calibrate the radius of the wheels for the best
     * possible turning.
     */
    public void calibrateRadius(){
        odometer.start();
        display.start();
        m_Navigation.travelTo(2 * Robot.tileLength, 0);
        System.exit(0);
    }

    /**
     * Protocol used to calibrate the size of the wheelbase for the best
     * possible navigation.
     */
    public void calibrateBase(){
        odometer.start();
        display.start();
        m_Navigation.rotateTo(180);
        m_Navigation.moveForward();
        m_Navigation.moveForward();
        m_Navigation.rotateTo(180);
        Button.waitForAnyPress();
        System.exit(0);
    }

    /**
     * Odometer test to verify that the subsystem is working properly.
     */
    public void odometryTest(){
        odometer.start();
        display.start();
        RConsole.println("I made it this far!");
        m_Navigation.rotateTo(2);
        RConsole.println("I made it this far 2!");

        Button.waitForAnyPress();
        System.exit(0);
    }

    /**
     * Subroutine to test that the vehicle is capable of
     * localizing on a specified map
     */
    public void localizationTest() {
        Button.waitForAnyPress();
        odometer.start();
        RConsole.println("Initiated ODO and ODO Display");
        orienteering.deterministicPositioning(odometer);
    }

    /**
     * Subroutine to test that the vehicle is capable of
     * navigating on a specified map
     * @param robot Robot instance used to control the movement of the robot.
     */
    public void navigationTest(Robot robot) {
        odometer.start();
        display.start();
        RConsole.println("Initiated ODO and ODO Display");
        robot.setPositionOnGrid(new Coordinate(0, 1));
        robot.setDirection(Orienteering.Direction.SOUTH);
        odometer.setX(45);
        odometer.setY(15);
        odometer.setThetaInDegrees(0);
        m_Navigation.navigate(new Coordinate(3,3));
    }

    /**
     * Integration test of the localization and navigation
     * subroutines.
     */
    public void localizationAndNavigationTest() {
        odometer.start();
        display.start();
        RConsole.println("Initiated ODO and ODO Display");
        orienteering.deterministicPositioning(odometer);
        Sound.beep();
        m_Navigation.navigate(new Coordinate(5,1));
    }

    /**
     * Main subroutine for the entire search and rescue task.
     */
    public void completeTask() {
        odometer.start();

        //localize
        orienteering.deterministicPositioning(odometer);
        Sound.beep();
        Sound.beep();

        //navigate to the north-east corner of the pick-up location
        m_Navigation.navigate(new Coordinate(9,1));

        //Look south towards the blocks
        m_Navigation.rotateTo(0);

        //Initiate blockpicking algorithm
        blockDetection.lookForBlock(m_Grabber);
        robot.setPositionOnGrid(new Coordinate(9,1));
        robot.setDirection(Orienteering.Direction.NORTH);

        //Navigate towards the drop-off location
        m_Navigation.navigate(new Coordinate(dropOffY, dropOffX));
        m_Navigation.moveBackwardHalfATile();

        //Drop the block
        m_Grabber.lowerClaw();
        m_Grabber.openClaw();
        Button.waitForAnyPress();

    }

    /**
     * Test used to determine the effectiveness of the robot
     * rotation.
     */
    public void rotationTest() {
        odometer.start();
        display.start();
        m_Navigation.rotate90ClockWise();
        m_Navigation.rotate90ClockWise();
        m_Navigation.rotate90CounterClock();
        m_Navigation.rotate90CounterClock();
    }

    /**
     * Test used to verify the functionality of the claw.
     */
    public void clawTest() {
        display.start();
        odometer.start();
        m_Grabber.deployArms();
        m_Grabber.lowerClaw();
        m_Grabber.closeClaw();
        m_Grabber.riseClaw();
        Button.waitForAnyPress();
    }

    /**
     * Test to verify the functionality of the block-picking
     * algorithm.
     */
    public void pickBlockTest() {
        odometer.start();
        display.start();
        blockDetection.lookForBlock(m_Grabber);
    }

    /**
     * Second test for the block-picking algorithm with a variation
     * in the starting position. Instead of standing at the north-east corner
     * we stand at the north-west corner.
     */
    public void pickBlockTestWithDifferentStartingPosition() {
        display.start();
        odometer.start();
        m_Grabber.deployArms();
        m_Grabber.lowerClaw();
        Button.waitForAnyPress();
        m_Grabber.closeClaw();
        Button.waitForAnyPress();
        m_Grabber.riseClaw();
        Button.waitForAnyPress();
    }

    /**
     * Test the straight driving capabilities of the robot.
     */
    public void straightDriverTest() {
        odometer.start();
        display.start();
        blockDetection.lookForBlock(m_Grabber);
    }

    /**
     * Test the functionality of the touch sensor on the left
     * arm of the claw.
     */
    public void touchSensorTest() {
        while (true) {
            if (robot.clawTouch.isPressed()) {
                RConsole.println("Touch sensor pressed!");
            }
        }
    }

}//end MissionPlanner