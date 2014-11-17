package dpm.lejos.orientation;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrienteeringTest {

    @Test
    public void testCreatePlaneFromGraph() throws Exception {

        Orienteering or = new Orienteering(Mapper.MapID.LAB4);
        or.printObstacles(or.getPlane());

        //or.printPlaneOptions(or.getPlane());

        assertEquals(1,1);

    }
}