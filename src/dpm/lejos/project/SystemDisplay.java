package dpm.lejos.project;

/*
 * OdometryDisplay.java
 */
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;

/**
 * Class displaying the current telemetry to the LCD screen
 */
public class SystemDisplay extends Thread {
	private static final long DISPLAY_PERIOD = 250;
	private Odometer odometer;

    public SystemDisplay(Odometer odometer) {
        this.odometer = odometer;
    }

	// run method (required for Thread)

    /**
     * Display the current telemetry to the lcd
     */
	public void run() {
		long displayStart, displayEnd;
		double[] position;

		// clear the display once
		LCD.clearDisplay();

		while (true) {
			displayStart = System.currentTimeMillis();

			// clear the lines for displaying odometry information
			LCD.drawString("X:              ", 0, 0);
			LCD.drawString("Y:              ", 0, 1);
			LCD.drawString("T:              ", 0, 2);

			// get the odometry information
			position = odometer.getPosition();

            // display odometry information
			for (int i = 0; i < 2; i++) {
				LCD.drawString(formattedDoubleToString(position[i], 2), 3, i);
			}
            LCD.drawString(formattedDoubleToString(Math.toDegrees(position[2]), 2), 3, 2);

            LCD.drawString("L: " + String.valueOf(odometer.getLeftLight()) + "   R: " + String.valueOf(odometer.getRightLight()),0,6);
            if (odometer.isLeftLine()) LCD.drawString("LEFT", 0,7); else LCD.drawString("    ", 0,7);
            if (odometer.isRightLine()) LCD.drawString("RIGHT", 6,7); else LCD.drawString("     ", 6,7);


            // throttle the OdometryDisplay
			displayEnd = System.currentTimeMillis();
			if (displayEnd - displayStart < DISPLAY_PERIOD) {
				try {
					Thread.sleep(DISPLAY_PERIOD - (displayEnd - displayStart));
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that OdometryDisplay will be interrupted
					// by another thread
				}
			}
		}
	}

    /**
     * Formats a double to a nice string
     * @param x the double to format
     * @param places the number of trailling digits
     * @return the double as a string with the correct number of digits
     */
	public static String formattedDoubleToString(double x, int places) {
		String result = "";
		String stack = "";
		long t;
		
		// put in a minus sign as needed
		if (x < 0.0)
			result += "-";
		
		// put in a leading 0
		if (-1.0 < x && x < 1.0)
			result += "0";
		else {
			t = (long)x;
			if (t < 0)
				t = -t;
			
			while (t > 0) {
				stack = Long.toString(t % 10) + stack;
				t /= 10;
			}
			
			result += stack;
		}
		
		// put the decimal, if needed
		if (places > 0) {
			result += ".";
		
			// put the appropriate number of decimals
			for (int i = 0; i < places; i++) {
				x = Math.abs(x);
				x = x - Math.floor(x);
				x *= 10.0;
				result += Long.toString((long)x);
			}
		}
		
		return result;
	}

}
