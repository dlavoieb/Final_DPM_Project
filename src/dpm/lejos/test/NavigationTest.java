package dpm.lejos.test;

import dpm.lejos.project.Navigation;
import dpm.lejos.project.Robot;
import dpm.lejos.project.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

public class NavigationTest {
    @Test
    public void testPolarDisplacement() throws Exception {
        double [] currentPosition;
        double [] destination;



        currentPosition = new double[] {0, 0, 90};
        destination = new double[] {0, 60};
        Vector vector = Navigation.vectorDisplacement(currentPosition, destination);
        assertEquals(60.0, vector.getMagnitude(), 0.1);
        assertEquals(Math.PI/2, vector.getOrientation(), 0.1);

        currentPosition = new double[] {0, 60, 90};
        destination = new double[] {60, 0};
        vector = Navigation.vectorDisplacement(currentPosition, destination);
        assertEquals(Math.sqrt((60*60)+(60*60)), vector.getMagnitude(), 0.1);
        assertEquals(-Math.PI/4, vector.getOrientation(), 0.1);

        currentPosition = new double[] {1, 1, 90};
        destination = new double[] {0, 0};
        vector = Navigation.vectorDisplacement(currentPosition, destination);
        assertEquals(Math.sqrt(2), vector.getMagnitude(), 0.1);
        assertEquals(-3*Math.PI/4.0, vector.getOrientation(), 0.1);

        currentPosition = new double[] {0, 1, 90};
        destination = new double[] {1, 0};
        vector = Navigation.vectorDisplacement(currentPosition, destination);
        assertEquals(Math.sqrt(2), vector.getMagnitude(), 0.1);
        assertEquals(-Math.PI/4.0, vector.getOrientation(), 0.1);

        currentPosition = new double[] {1, 0, 90};
        destination = new double[] {0, 1};
        vector = Navigation.vectorDisplacement(currentPosition, destination);
        assertEquals(Math.sqrt(2), vector.getMagnitude(), 0.1);
        assertEquals(3*Math.PI/4.0, vector.getOrientation(), 0.1);

    }

    @Test
    public void testComputeOptimalRotationAngle() throws Exception {
        assertEquals(-30, Navigation.computeOptimalRotationAngle(30, 0),0.001);
        assertEquals(60, Navigation.computeOptimalRotationAngle(5*60, 0), 0.001);
        assertEquals(-60, Navigation.computeOptimalRotationAngle(0, 300), 0.001);
        assertEquals(60, Navigation.computeOptimalRotationAngle(0, 60), 0.001);
        assertEquals(0.0, Navigation.computeOptimalRotationAngle(90, 90), 0.001);
        assertEquals(2.0, Navigation.computeOptimalRotationAngle(359, 1), 0.001);
        assertEquals(90, Navigation.computeOptimalRotationAngle(0, 90), 0.001);
        assertEquals(-90, Navigation.computeOptimalRotationAngle(90, 0), 0.001);
        assertEquals(-20, Navigation.computeOptimalRotationAngle(30, 10), 0.001);
    }
}