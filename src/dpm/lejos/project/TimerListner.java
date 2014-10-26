package project;

/**
 * @author david
 * @version 1.0
 * @created 24-oct.-2014 12:37:24
 */
public interface TimerListner extends ObstacleDetection, LineDetector {

	public void timedOut();

}