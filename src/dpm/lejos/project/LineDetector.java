package dpm.lejos.project;

import dpm.lejos.Sensors.SuperColorSensor;
import lejos.nxt.comm.RConsole;
import lejos.util.Timer;
import lejos.util.TimerListener;

import java.util.LinkedList;

/**
 * Class that polls the down facing light sensor to detect floor lines
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class LineDetector implements TimerListener {

    private static final int DEFAULT_PERIOD = 50;
    private static final int DEFAULT_WINDOW = 10;
    private Timer timer;
    private boolean isLine;
    private SuperColorSensor colorSensor;
    private int LIGHT_THRESHOLD;

    private LinkedList<Integer> list = new LinkedList<Integer>();
    private int pastLightValue;

    /**
     * default constructor
     * @param colorSensor requires the robot object containing the light sensor
     * @param lightThreshold the normalised light value to use as a threshold
     * @param start boolean to start the timer right away
     */
    public LineDetector(SuperColorSensor colorSensor, int lightThreshold, boolean start){
        this(colorSensor, lightThreshold, DEFAULT_PERIOD, DEFAULT_WINDOW, start);
    }

    /**
     * constructor with added period adjustment capability
     * @param colorSensor requires the object light sensor
     * @param period the period of the timer
     * @param start boolean to start the timer right away
     */
     public LineDetector(SuperColorSensor colorSensor, int lightThreshold, int period, int window, boolean start) {
         LIGHT_THRESHOLD = lightThreshold;
         timer = new Timer(period, this);
         this.colorSensor = colorSensor;
         pastLightValue = colorSensor.getNormalizedLightValue();
         colorSensor.setFloodlight(true);

         for (int i = 0; i < window; i++){

             int value1 = colorSensor.getNormalizedLightValue();
             try {
                 Thread.sleep(2);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             int value2 = colorSensor.getNormalizedLightValue();

             list.add(Math.abs(value1-value2));
         }
         pastLightValue = Utils.medianList(list);

         if(start) timer.start();

     }

    /**
     * callback for the timer timeout
     *
     * checks the change in the value of the read light
     * and compares with the set threshold
     */
    public void timedOut(){

        int value1 = colorSensor.getNormalizedLightValue();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int value2 = colorSensor.getNormalizedLightValue();

        list.add(Math.abs(value1-value2));
        list.remove(0);

        float currentLightValue = Utils.medianList(list);

      //  float currentLightValue = colorSensor.getNormalizedLightValue();
        RConsole.println(String.valueOf(currentLightValue));
        float diff = pastLightValue - currentLightValue;
        if (Math.abs(diff) > LIGHT_THRESHOLD){
            RConsole.println("Detected a line edge on sensor " + String.valueOf(colorSensor.getSensorPort().getId()));
            //we detected a significant change
                isLine = diff >= 0;
        }

        pastLightValue = (int) currentLightValue;
	}

    /*
    get 2 value
    abs (1-2)
    store in array
    take median
    compare with threshold
     */

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

    public int getPastLightValue(){
        return pastLightValue;
    }
}//end LineDetector