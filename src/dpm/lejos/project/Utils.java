package dpm.lejos.project;

import java.util.LinkedList;

/**
 * Conversion utilities
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Utils {

	/**
	 * converts a global robot travel distance to a motor rotation angle
	 * 
	 * @param distance the distance to travel
	 * @param robot the robot object
     * @return number of motor rotation needed
     */
	public static int robotDistanceToMotorAngle(double distance, Robot robot){
        return (int) ((180.0 * distance) / (Math.PI * Robot.wheelRadius));
	}

	/**
	 * converts a global robot rotation to a motor rotation angle
	 * 
	 * @param angle the angle to rotate
     * @param robot the robot object
     * @return number of motor rotation needed
	 */
	public static int robotRotationToMotorAngle(double angle, Robot robot){
        return robotDistanceToMotorAngle(Math.PI * Robot.wheelBase * angle / 360.0 + 0.1, robot);
	}

    /**
     * Apply filter to given list
     *
     * All filter basically perform a vector
     * dot product of an array of readings
     * and a coefficient array
     *
     * @param list the list of readings
     * @param coefs the list of coefficient
     * @return the filtered value
     */
    public static double filterList(LinkedList<Integer> list, double[] coefs){
        if (coefs.length < list.size()){
            throw new ArrayIndexOutOfBoundsException();
        }

        double sum = 0;
        for (int i = 0; i<list.size(); i++){
            sum += list.get(i)*coefs[i];
        }

        return sum;
    }

    /**
     * method the
     * @param list
     * @return the median value of the list
     */
    public static int medianList(LinkedList<Integer> list){
        return list.get(list.size()/2);
    }
}//end Utils