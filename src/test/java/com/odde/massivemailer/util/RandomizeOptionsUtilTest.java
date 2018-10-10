package com.odde.massivemailer.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomizeOptionsUtilTest {

    @Test
    public void testIfListIsNull(){
        List<String> list= null;
        List<String> newRandomizedList = RandomizeOptionsUtil.randomize(list);
        assertTrue(newRandomizedList.isEmpty());
    }

    @Test
    public void testIfListIsEmpty(){
        List<String> list= Arrays.asList();
        List<String> newRandomizedList = RandomizeOptionsUtil.randomize(list);
        assertTrue(newRandomizedList.isEmpty());
    }

    @Test
    public void testIfListContainsOnlyOneElement(){
        List<String> list= Arrays.asList("Single Option");
        List<String> newRandomizedList = RandomizeOptionsUtil.randomize(list);
        assertEquals(list.size(),newRandomizedList.size());
        assertEquals(list.toString(), newRandomizedList.toString());
    }

    @Test
    public void testIfListContainsNoneOfTheAbove(){
        List<String> list= Arrays.asList("o1", "o2","o3","o4","None of the above");
        List<String> newRandomizedList = RandomizeOptionsUtil.randomize(list);
        assertEquals(list.size(),newRandomizedList.size());
        assertEquals("None of the above", newRandomizedList.get(newRandomizedList.size()-1));
        Collections.sort(list);
        Collections.sort(newRandomizedList);
        assertEquals(list.toString(), newRandomizedList.toString());
    }

    @Test
    public void testIfListContainsNoneOfTheAboveAtFirst(){
        List<String> list= Arrays.asList("None of the above","o1", "o2","o3","o4");
        List<String> newRandomizedList = RandomizeOptionsUtil.randomize(list);
        assertEquals(list.size(),newRandomizedList.size());
        assertEquals("None of the above", newRandomizedList.get(newRandomizedList.size()-1));
        Collections.sort(list);
        Collections.sort(newRandomizedList);
        assertEquals(list.toString(), newRandomizedList.toString());
    }

    @Test
    public void testPerfectlyRandomizedValues(){
        List<String> list= Arrays.asList("" +
                "o1","o2","o3","o6","o5","o4");
        List<String> newRandomizedList = RandomizeOptionsUtil.randomize(list);
        assertFalse(list.toString().equals(newRandomizedList.toString()));
        Collections.sort(list);
        Collections.sort(newRandomizedList);
        assertEquals(list.toString(), newRandomizedList.toString());
    }
}
