package dpm.lejos.test;

import dpm.lejos.orientation.Mapper;
import dpm.lejos.project.Navigation;
import dpm.lejos.project.Odometer;
import dpm.lejos.project.Robot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NavigationTest {

    Navigation navigation;

    @Before
    public void setUp() {
        System.out.println("@Before - setUp");
        Odometer odometer = null;
        Robot robot = null;

        this.navigation = new Navigation(robot,odometer, Mapper.MapID.MAP1);
    }

    @After
    public void tearDown() {

        System.out.println("@After - tearDown");
    }

    @Test
    public void testPerformMoves() throws Exception {
        ArrayList<Mapper.Node> moveList = new ArrayList<Mapper.Node>(20);

        moveList.add(0, navigation.mapper.graphPlane[11][11]);
        moveList.add(0, navigation.mapper.graphPlane[10][11]);
        moveList.add(0, navigation.mapper.graphPlane[9][11]);
        moveList.add(0, navigation.mapper.graphPlane[8][11]);
        moveList.add(0, navigation.mapper.graphPlane[7][11]);
        moveList.add(0, navigation.mapper.graphPlane[6][11]);
        moveList.add(0, navigation.mapper.graphPlane[6][10]);
        moveList.add(0, navigation.mapper.graphPlane[6][9]);
        moveList.add(0, navigation.mapper.graphPlane[6][8]);
        moveList.add(0, navigation.mapper.graphPlane[6][7]);
        moveList.add(0, navigation.mapper.graphPlane[6][6]);
        moveList.add(0, navigation.mapper.graphPlane[6][5]);
        moveList.add(0, navigation.mapper.graphPlane[6][4]);
        moveList.add(0, navigation.mapper.graphPlane[5][4]);
        moveList.add(0, navigation.mapper.graphPlane[4][4]);
        moveList.add(0, navigation.mapper.graphPlane[4][5]);
        moveList.add(0, navigation.mapper.graphPlane[4][6]);
        moveList.add(0, navigation.mapper.graphPlane[3][6]);
        moveList.add(0, navigation.mapper.graphPlane[2][6]);
        moveList.add(0, navigation.mapper.graphPlane[2][5]);
        moveList.add(0, navigation.mapper.graphPlane[2][4]);
        moveList.add(0, navigation.mapper.graphPlane[2][3]);
        moveList.add(0, navigation.mapper.graphPlane[2][2]);
        moveList.add(0, navigation.mapper.graphPlane[2][1]);
        moveList.add(0, navigation.mapper.graphPlane[2][0]);

        navigation.performMoves(moveList);
    }
}