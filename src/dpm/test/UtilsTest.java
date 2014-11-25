package dpm.test;

import dpm.lejos.project.Utils;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void testMedianList() throws Exception {
        LinkedList<Integer> list = new LinkedList<Integer>();

        for (int i = 0; i < 10; i++){
            list.add(i);
        }
        System.out.println(Utils.medianList(list));
    }
}