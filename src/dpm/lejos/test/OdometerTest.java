package dpm.lejos.test;

import dpm.lejos.project.Odometer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OdometerTest {

    private  Odometer odometer;

    @Before
    public void setUp() throws Exception {
        odometer = new Odometer();
    }

    @Test
    public void normalisationTest(){
        double pi = Math.PI;

        odometer.setTheta(2 * pi);
        Assert.assertEquals( 0, odometer.getThetaNormalized(), 0.000001);

        odometer.setTheta(-2 * pi);
        Assert.assertEquals(0, odometer.getThetaNormalized(), 0.000001);

        odometer.setTheta(-3 * pi);
        Assert.assertEquals(-pi, odometer.getThetaNormalized(), 0.000001);

        for (double i = -pi; i<= pi;i+=0.001){
            odometer.setTheta(i);
            Assert.assertEquals(i, odometer.getThetaNormalized(), 0.000001);
        }

        for (double i = -2* pi; i<= -pi;i+=0.001){
            odometer.setTheta(i);
            Assert.assertEquals(i+2* pi, odometer.getThetaNormalized(), 0.000001);
        }

        for (double i = pi + 0.1; i<= 2* pi;i+=0.001){
            odometer.setTheta(i);
            Assert.assertEquals(i-2* pi, odometer.getThetaNormalized(), 0.000001);
        }
    }

}