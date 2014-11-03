package dpm.lejos.project;

import dpm.lejos.orientation.Orienteering.*;

/**
 * Provides all the necessary navigation capabilities
 *
 * All motion is managed by this class
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Navigation {

	private boolean noObstacle = true;
	private Odometer m_Odometer;
	private ObstacleDetection m_ObstacleDetection;

    private Robot m_robot;

    /**
     * default constructor
     *
     * @param robot the robot object
     */
	public Navigation(Robot robot){
        m_robot = robot;
	}

	/**
	 * method used to rotateCCW the
     * robot to an absolute angle
     *
	 * @param angle the angle to rotateCCW to
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

    public void floatMotors(){
        m_robot.motorStrb.flt(true);
        m_robot.motorPort.flt(true);
    }

    /**
     * move the robot one tile forward
     */
    public void moveForward() {
        m_robot.motorPort.setSpeed(m_robot.CRUISE_SPEED);
        m_robot.motorStrb.setSpeed(m_robot.CRUISE_SPEED);
        m_robot.motorPort.rotate(Utils.robotDistanceToMotorAngle(m_robot.tileLength, m_robot), true);
        m_robot.motorStrb.rotate(Utils.robotDistanceToMotorAngle(m_robot.tileLength, m_robot), false);
    }

    /**
     * rotate the physical robot 90 degrees counterclockwise
     */
    public void rotate90CounterClock() {
        m_robot.motorPort.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorStrb.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorPort.rotate(Utils.robotRotationToMotorAngle(-90, m_robot), true);
        m_robot.motorStrb.rotate(Utils.robotRotationToMotorAngle(90, m_robot), false);
    }

    /**
     * rotate the physical robot 90 degrees clockwise
     */
    public void rotate90ClockWise() {
        m_robot.motorPort.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorStrb.setSpeed(m_robot.ROTATE_SPEED);
        m_robot.motorPort.rotate(Utils.robotRotationToMotorAngle(90, m_robot), true);
        m_robot.motorStrb.rotate(Utils.robotRotationToMotorAngle(-90, m_robot), false);
    }

/**
 * position the robot facing north
 * @param destinationDirection the current heading
 */
	public void rotateToDirection(Direction destinationDirection) {

        Direction robotDirection = m_robot.getDirection();

        if (robotDirection == Direction.NORTH) {
            switch (destinationDirection) {
                case SOUTH:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
                case EAST:
                    rotate90CounterClock();
                    break;
                case WEST:
                    rotate90ClockWise();
                    break;
            }
        } else if (robotDirection == Direction.SOUTH) {
            switch (destinationDirection) {
                case NORTH:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
                case EAST:
                    rotate90ClockWise();
                    break;
                case WEST:
                    rotate90CounterClock();
                    break;
            }
        } else if (robotDirection == Direction.EAST) {
            switch (destinationDirection) {
                case NORTH:
                    rotate90ClockWise();
                    break;
                case SOUTH:
                    rotate90CounterClock();
                    break;
                case WEST:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
            }
        } else {
            switch (destinationDirection) {
                case NORTH:
                    rotate90CounterClock();
                    break;
                case SOUTH:
                    rotate90ClockWise();
                    break;
                case EAST:
                    rotate90CounterClock();
                    rotate90CounterClock();
                    break;
            }
        }

        m_robot.setDirection(destinationDirection);

    }
    
}//end Navigator