package dpm.lejos.project;

import lejos.util.Timer;
import lejos.util.TimerListener;

/**
 * Class that polls the down facing light sensor to detect floor lines
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class LineDetector implements TimerListener {

    private static final int DEFAULT_PERIOD = 25;
    private Timer timer;

    private Robot m_robot;

    private boolean isLine;
    /**
     * default constructor
     * @param robot requires the robot object containing the light sensor
     */
    public LineDetector(Robot robot){
        timer = new Timer(DEFAULT_PERIOD, this);
        m_robot = robot;
    }

    /**
     * constructor with added period adjustment capability
     * @param robot requires the robot object containing the light sensor
     * @param period the period of the timer
     */
     public LineDetector(Robot robot, int period) {
        timer = new Timer(period, this);
    }

    /**
     * callback for the timer timeout
     */
    public void timedOut(){

	}

    /**
     * get if the robot is over a line
     *
     * @return return true if sensor currently detecting line
     */
    public boolean isLine(){
        return isLine;
    }

    public void finalize(){}
}//end LineDetector