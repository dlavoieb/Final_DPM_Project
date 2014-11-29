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

    public void lowerClaw(){
        robot.clawLift.setSpeed(Robot.CLAW_SPEED);
        robot.clawLift.rotate(-Robot.clawLowerDistance);
    }

    public void riseClaw(){
        robot.clawLift.setSpeed(Robot.CLAW_SPEED - 75);
        robot.clawLift.rotate(Robot.clawLowerDistance);
    }

	public void deployArms() {
        robot.clawClose.setSpeed(Robot.CLAW_SPEED);
        robot.clawClose.rotate(Robot.clawPrepare);
    }

    public void closeClaw() {
        robot.clawClose.setSpeed(Robot.CLAW_SPEED);
        robot.clawClose.rotate(Robot.clawCloseAngle);
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