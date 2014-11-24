package dpm.lejos.project;

import lejos.nxt.ColorSensor;
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
    private boolean isLine;
    private ColorSensor colorSensor;
    private int LIGHT_THRESHOLD;

    private int pastLightValue;

    /**
     * default constructor
     * @param colorSensor requires the robot object containing the light sensor
     * @param lightThreshold the normalised light value to use as a threshold
     * @param start boolean to start the timer right away
     */
    public LineDetector(ColorSensor colorSensor, int lightThreshold, boolean start){
        this(colorSensor, lightThreshold, DEFAULT_PERIOD, start);
    }

    /**
     * constructor with added period adjustment capability
     * @param colorSensor requires the object light sensor
     * @param period the period of the timer
     * @param start boolean to start the timer right away
     */
     public LineDetector(ColorSensor colorSensor, int lightThreshold, int period, boolean start) {
         LIGHT_THRESHOLD = lightThreshold;
         timer = new Timer(period, this);
         this.colorSensor = colorSensor;
         if(start) timer.start();
         pastLightValue = colorSensor.getNormalizedLightValue();
    }

    /**
     * callback for the timer timeout
     *
     * checks the change in the value of the read light
     * and compares with the set threshold
     */
    public void timedOut(){

        int currentLightValue = this.colorSensor.getNormalizedLightValue();
        int diff = pastLightValue - currentLightValue;
        if (Math.abs(diff) > LIGHT_THRESHOLD){
            //we detected a significant change
            isLine = diff <= 0;
        }

        pastLightValue = currentLightValue;
	}

    /**
     * get if the robot is over a line
     *
     * @return return true if sensor currently detecting line
     */
    public boolean isLine(){
        return isLine;
    }

    /**
     * manual start of the timer
     */
    public void startTimer(){
        timer.start();
    }
}//end LineDetector