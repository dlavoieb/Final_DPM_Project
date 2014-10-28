package dpm.lejos.project;

import lejos.nxt.UltrasonicSensor;
import lejos.util.TimerListener;
import lejos.util.Timer;

/**
 * Obstacle detection class monitors
 * all the ultrasonic sensors to
 * detect the presence of obstacles
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class ObstacleDetection implements TimerListener{

	private UltrasonicSensor frontUS;
    private int PERIOD = 10;
    private final Timer timer;
    private Robot m_robot;

    /**
     * default constructor
     * @param robot Robot object
     */
	public ObstacleDetection(Robot robot){
        timer = new Timer(PERIOD, this);
        m_robot = robot;
	}

	public void finalize() {

	}

	private void detectObstacles(){

	}

    /**
     * callback for the timer
     *
     * does the polling of the 3 ultrasonic sensors
     */
	public void timedOut(){

	}
}//end ObstacleDetection