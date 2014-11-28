package dpm.lejos.test;

import dpm.lejos.project.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Vector type
 */
public class VectorTest {

    @Test
    public void testGetMagnitude() throws Exception {
        Vector vector = new Vector(10, Math.PI);
        Assert.assertEquals(10.0, vector.getMagnitude(), 0.001);
    }

    @Test
    public void testGetOrientation() throws Exception {
        Vector vector = new Vector(10, Math.PI);
        Assert.assertEquals(Math.PI, vector.getOrientation(), 0.001);
    }

    @Test
    public void testSetMagnitude() throws Exception {
        Vector vector = new Vector();
        vector.setMagnitude(10);
        Assert.assertEquals(10.0, vector.getMagnitude(), 0.001);
    }

    @Test
    public void testSetOrientation() throws Exception {
        Vector vector = new Vector();
        vector.setOrientation(Math.PI);
        Assert.assertEquals(Math.PI, vector.getOrientation(), 0.001);
    }

    @Test
    public void testToCartesian() throws Exception {
        Vector vector = new Vector(Math.sqrt(2),Math.PI/4);
        double[] coordinates = vector.toCartesian();
        Assert.assertEquals(coordinates[0], 1.0, 0.001);
        Assert.assertEquals(coordinates[1], 1.0, 0.001);
    }
}