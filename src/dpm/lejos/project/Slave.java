package dpm.lejos.project;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.LCPResponder;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.comm.RS485;

/**
 * Slave class will allow us to remotely operate
 * the sensors and motors of the salve brick
 * from the master
 *
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class Slave {
    static class Responder extends LCPResponder
    {
        Responder(NXTCommConnector con)
        {
            super(con);
        }

        protected void disconnect()
        {
            super.disconnect();
        }
    }

    public static void main(String[] args) throws Exception
    {
        Sound.setVolume(Sound.VOL_MAX);
        Sound.systemSound(false, 3);
        LCD.clear();
        LCD.drawString("Running...", 0, 1);
        Responder resp = new Responder(RS485.getConnector());
        resp.start();
        resp.join();
        LCD.drawString("Closing...  ", 0, 1);
    }
}
