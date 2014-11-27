package dpm.lejos.test;

import dpm.lejos.project.Utils;
import org.junit.Assert;
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
}