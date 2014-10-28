package dpm.lejos.project;

/**
 * Provides all the necessary navigation capabilities
 *
 * All motion is managed by this class
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Navigator {

	private boolean noObstacle = true;
	private Odometer odometer;
	private Odometer m_Odometer;
	private ObstacleDetection m_ObstacleDetection;

    private Robot m_robot;

    /**
     * default constructor
     *
     * @param robot the robot object
     */
	public Navigator(Robot robot){
        m_robot = robot;
	}

	/**
	 * method used to rotate the
     * robot to an absolute angle
     *
	 * @param angle the angle to rotate to
	 */
	public void rotateTo(double angle){

	}

	/**
	 * method used to send the robot to a
     * predetermined absolute location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void travelTo(double x, double y){

	}
}//end Navigator