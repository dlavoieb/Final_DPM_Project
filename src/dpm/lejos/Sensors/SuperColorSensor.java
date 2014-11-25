package dpm.lejos.Sensors;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;

/**
 * @author David Lavoie-Boutin
 * @version v1.0
 */
public class SuperColorSensor extends ColorSensor{

    /**
     * Create a new Color Sensor instance and bind it to a port.
     *
     * @param port Port to use for the sensor.
     */
    public SuperColorSensor(SensorPort port) {
        super(port);
    }

    public SensorPort getSensorPort () {
        return this.port;
    }
}
