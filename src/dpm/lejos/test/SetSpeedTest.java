import lejos.nxt.Button;
import lejos.nxt.Motor;

/**
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class SetSpeedTest {
    public static void main(String [] argv){
        Motor.A.setSpeed(200);

        Motor.A.rotate(2 * 360, true);

        Button.waitForAnyPress();
        Motor.A.setSpeed(1);
        Button.waitForAnyPress();
        Motor.A.setSpeed(200);
        Button.waitForAnyPress();


    }
}
