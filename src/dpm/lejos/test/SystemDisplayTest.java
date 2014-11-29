package dpm.lejos.test;

import dpm.lejos.project.SystemDisplay;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemDisplayTest {

    @Test
    public void testFormattedDoubleToString() throws Exception {
        Assert.assertEquals("10.000", SystemDisplay.formattedDoubleToString(10, 3));
        Assert.assertEquals("10.00",  SystemDisplay.formattedDoubleToString(10, 2));
        Assert.assertEquals("10.0",   SystemDisplay.formattedDoubleToString(10, 1));
        Assert.assertEquals("10",     SystemDisplay.formattedDoubleToString(10, 0));
    }
}