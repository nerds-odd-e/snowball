package com.odde.massivemailer.model;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by wenjie on 27/9/16.
 */
public class GameTest {
    @Test
    public void testCreateGameObject()
    {
        GameForTest t_GameObj = new GameForTest();
        assertNotNull(t_GameObj);
        assertEquals(0, t_GameObj.getDistance());
    }

    @Test
    public void testSetDistance()
    {
        GameForTest t_GameObj = new GameForTest();
        int t_Dist = 2;

        t_GameObj.setDistance(t_Dist);
        assertEquals(t_Dist, t_GameObj.getDistance());

        t_GameObj.setDistance(-t_Dist);
        assertEquals(t_Dist, t_GameObj.getDistance());

        t_GameObj.setDistance(0);
        assertEquals(0, t_GameObj.getDistance());
    }
}
