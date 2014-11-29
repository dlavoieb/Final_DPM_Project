package dpm.lejos.project;

import dpm.lejos.orientation.Coordinate;
import dpm.lejos.orientation.Orienteering;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;

/**
 * High level mission planning.
 *
 * Implements the over all strategy
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

    public MissionPlanner(Navigation navigation, Grabber grabber, Orienteering orienteering, Odometer odometer, SystemDisplay display, BlockDetection blockDetection, Robot robot) {

        this.m_Navigation = navigation;
        this.m_Grabber = grabber;
        this.orienteering = orienteering;
        this.odometer = odometer;
        this.display = display;
        this.blockDetection = blockDetection;
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
     * 6- repeat from 2 onwards
     */
    public void startMission(){
        odoCorrectionTest();
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

    public void calibrateRadius(){
        odometer.start();
        display.start();
        m_Navigation.travelTo(2 * Robot.tileLength, 0);
        System.exit(0);
    }

    public void calibrateBase(){
        odometer.start();
        display.start();
        m_Navigation.rotateTo(180);
        m_Navigation.moveForward();
        m_Navigation.moveForward();
        m_Navigation.rotateTo(180);
//        m_Navigation.rotateTo(0);
//        m_Navigation.rotateTo(180);
//        m_Navigation.rotateTo(0);
        Button.waitForAnyPress();
        System.exit(0);
    }


    public void odometryTest(){
        //m_Navigation.floatMotors();
        odometer.start();
        display.start();
        RConsole.println("I made it this far!");
        m_Navigation.rotateTo(2);
        RConsole.println("I made it this far 2!");

        Button.waitForAnyPress();
        System.exit(0);
    }

    public void localizationTest() {
        Button.waitForAnyPress();
        odometer.start();
        display.start();
        RConsole.println("Initiated ODO and ODO Display");
        orienteering.deterministicPositioning(odometer);
    }

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

    public void localizationAndNavigationTest() {
        odometer.start();
        display.start();
        RConsole.println("Initiated ODO and ODO Display");
//        Button.waitForAnyPress();
        orienteering.deterministicPositioning(odometer);
        Sound.beep();
//        Button.waitForAnyPress();
        m_Navigation.navigate(new Coordinate(3,3));
    }

    public void fullTest() {
        odometer.start();
        orienteering.deterministicPositioning(odometer);
        Sound.beep();
        m_Navigation.navigate(new Coordinate(5,1));
        double x = 6 * 30 + 30 / 2.0;
        double y = 30 + 30 / 2.0;
        m_Navigation.rotateToCoordinate(x,y);
        blockDetection.lookForBlock(m_Grabber);

    }

    public void rotationTest() {
        odometer.start();
        display.start();
        m_Navigation.rotate90ClockWise();
        m_Navigation.rotate90ClockWise();
        m_Navigation.rotate90CounterClock();
        m_Navigation.rotate90CounterClock();
    }

    public void clawTest() {
        display.start();
        odometer.start();
        m_Grabber.deployArms();
        m_Grabber.lowerClaw();
        m_Grabber.closeClaw();
        m_Grabber.riseClaw();
        Button.waitForAnyPress();
    }

    public void pickBlockTest2() {
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

    public void pickBlockTest() {
        odometer.start();
        display.start();
        blockDetection.lookForBlock(m_Grabber);
    }

    public void straightDriverTest() {
        odometer.start();
        display.start();
        blockDetection.lookForBlock(m_Grabber);
    }

}//end MissionPlanner