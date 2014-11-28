package dpm.lejos.test;

import dpm.lejos.project.Robot;
import dpm.lejos.project.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class UtilsTest {


    @Test
    public void testMedianList() throws Exception {
        LinkedList<Integer> list = new LinkedList<Integer>();

        for (int i = 0; i < 10; i++){
            list.add(i);
        }
        Assert.assertEquals(5, Utils.medianList(list));
    }

    @Test
    public void testConvertDistanceToMotorRotation() throws Exception {
        Robot robot = new Robot(true);
        Assert.assertEquals(850, Utils.robotDistanceToMotorAngle(30, robot));
        Assert.assertEquals(-850, Utils.robotDistanceToMotorAngle(-30, robot));
    }

    @Test
    public void testConvertAngleToMotorRotation() throws Exception {
        Robot robot = new Robot(true);
        Assert.assertEquals(281, Utils.robotRotationToMotorAngle(90, robot));
        Assert.assertEquals(-275, Utils.robotRotationToMotorAngle(-90, robot));
    }

    @Test
    public void testAverageList(){
        LinkedList<Integer>list = new LinkedList<Integer>();
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        double [] coefs = new double [] {1/2.0, -1/6.0, -1/6.0, -1/6.0};
        Assert.assertEquals(0, Utils.averageList(list, coefs), 0.001);
        list = new LinkedList<Integer>();
        list.add(20);
        list.add(10);
        list.add(9);
        list.add(10);


        Assert.assertEquals(5.16666, Utils.averageList(list, coefs), 0.001);
    }
}