package dpm.lejos.project;

import lejos.nxt.ColorSensor;
import lejos.nxt.comm.RConsole;

import java.util.LinkedList;

/**
 * Class that polls the down facing light sensor to detect floor lines
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class LineDetector extends Thread {

    private static final int DEFAULT_PERIOD = 25;
    private static final int DEFAULT_WINDOW = 4;
    private boolean isLine;
    private ColorSensor colorSensor;
    private int LIGHT_THRESHOLD;

    private double[] coeffs = new double[] {1/2.0,-1/6.0, -1/6.0, -1/6.0};

    private LinkedList<Integer> list = new LinkedList<Integer>();

    private double pastLight = 0;

    /**
     * default constructor
     * @param colorSensor requires the robot object containing the light sensor
     * @param lightThreshold the normalised light value to use as a threshold
     * @param start boolean to start the timer right away
     */
    public LineDetector(ColorSensor colorSensor, int lightThreshold, boolean start){
        this(colorSensor, lightThreshold, DEFAULT_WINDOW, start);
    }

    /**
     * constructor with added period adjustment capability
     * @param colorSensor requires the object light sensor
     * @param start boolean to start the timer right away
     * @param lightThreshold the threshold for line detection
     * @param window the size of the sampling window if different from default
     */
     public LineDetector(ColorSensor colorSensor, int lightThreshold, int window, boolean start) {
         LIGHT_THRESHOLD = lightThreshold;
         this.colorSensor = colorSensor;
         colorSensor.setFloodlight(true);
         for (int i = 0; i < window; i++){
             list.add(colorSensor.getNormalizedLightValue());
         }
         if(start) this.start();

     }

    /**
     * callback for the timer timeout
     *
     * checks the change in the value of the read light
     * and compares with the set threshold
     */
    public void run(){

        while(true) {

            list.add(colorSensor.getNormalizedLightValue());
            list.remove(0);
            double diff = Utils.averageList(list, coeffs);

            pastLight = diff;

            if (Math.abs(diff) > LIGHT_THRESHOLD) {
               // RConsole.println("Detected a line edge on sensor " + String.valueOf(colorSensor.getSensorPort().getId()));
                //we detected a significant change
                isLine = diff >= 0;
            }


            try {
                Thread.sleep(DEFAULT_PERIOD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

    /**
     * get if the robot is over a line
     *
     * @return return true if sensor currently detecting line
     */
    public boolean isLine(){
        return isLine;
    }

    public int getPastLightValue(){
        return (int) pastLight;
    }
}//end LineDetector