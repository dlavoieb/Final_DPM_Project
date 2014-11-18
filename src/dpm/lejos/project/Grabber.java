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
        robot.clawLift.setSpeed(robot.CLAW_SPEED);
        robot.clawLift.rotate(-robot.clawLowerDistance);
    }

    public void riseClaw(){
        robot.clawLift.setSpeed(robot.CLAW_SPEED);
        robot.clawLift.rotate(robot.clawLowerDistance);
    }

	public void deployArms() {
        robot.clawClose.setSpeed(robot.CLAW_SPEED);
        robot.clawClose.rotate(robot.clawPrepare);
    }

    public void closeClaw() {
        robot.clawClose.setSpeed(robot.CLAW_SPEED);
        robot.clawClose.rotate(robot.clawCloseAngle);
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