package dpm.lejos.project;

/**
 * Class managing the grabbing mechanism
 *
 * @author David Lavoie-Boutin
 * @version 1.0
 */
public class Grabber {
    private Robot robot;
	public Grabber(Robot robot){
        this.robot = robot;
	}

    /**
     * Lowers the claw a specified amount of degrees calculated
     * from testing.
     */
    public void lowerClaw(){
        robot.clawLift.setSpeed(Robot.CLAW_SPEED);
        robot.clawLift.rotate(-Robot.clawLowerDistance);
    }

    /**
     * Raises the claw a specified amount of degrees calculated
     * from testing.
     */
    public void riseClaw(){
        robot.clawLift.setSpeed(Robot.CLAW_SPEED - 50);
        robot.clawLift.rotate(Robot.clawLowerDistance);
    }

    /**
     * Opens the arms of the claw enough to span the length of a tile
     */
	public void deployArms() {
        robot.clawClose.setSpeed(Robot.CLAW_SPEED);
        robot.clawClose.rotate(Robot.clawPrepare - 75);
    }

    /**
     * Closes the arms of the claw enough so that the touch sensor would be
     * pressed by an obstacle.
     */
    public void closeClaw() {
        robot.clawClose.setSpeed(Robot.CLAW_SPEED);
        robot.clawClose.rotate(Robot.clawCloseAngle + 50);
    }

    /**
     * Opens the claw. Regularly used after it has been closed.
     */
    public void openClaw() {
        robot.clawClose.setSpeed(Robot.CLAW_SPEED);
        robot.clawClose.rotate(-Robot.clawCloseAngle - 50);

    }

    /**
	 * close claw,
	 * check for touch contact
	 * release perform again with repositionning
	 */
	public void pickUpBlock(){
        deployArms();
        lowerClaw();
        closeClaw();
        riseClaw();
	}

}//end Grabber