package dpm.lejos.project;

import dpm.lejos.orientation.Orienteering;
import lejos.nxt.Button;

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
    private final OdometryDisplay display;
    private final Navigation m_Navigation;
    private final Grabber m_Grabber;
    private final Orienteering orienteering;

	public MissionPlanner(Navigation navigation, Grabber grabber, Orienteering orienteering, Odometer odometer, OdometryDisplay display) {

        this.m_Navigation = navigation;
        this.m_Grabber = grabber;
        this.orienteering = orienteering;
        this.odometer = odometer;
        this.display = display;
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
        while (true){
            orienteering.deterministicPositioning();
            m_Navigation.travelTo(2,2);
        }
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

    public void odometryTest(){
        m_Navigation.floatMotors();
        odometer.start();
        display.start();

        Button.waitForAnyPress();
        System.exit(0);
    }


}//end MissionPlanner