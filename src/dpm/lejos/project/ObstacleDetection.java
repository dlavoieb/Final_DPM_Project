package dpm.lejos.project;

import lejos.nxt.UltrasonicSensor;
import lejos.util.TimerListener;
import lejos.util.Timer;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public class ObstacleDetection implements TimerListener{

	private UltrasonicSensor frontUS;
    private int PERIOD = 10;
    private final Timer timer;

	public ObstacleDetection(){
        timer = new Timer(PERIOD, this);
	}

	public void finalize() {

	}
	public void detectObstacles(){

	}

	public void timedOut(){

	}
}//end ObstacleDetection