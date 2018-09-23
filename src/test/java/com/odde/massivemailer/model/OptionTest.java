package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(TestWithDB.class)
public class OptionTest {

    @Test
    public void shouldBeAbleToPersistOption(){
        Option option = Option.createIt("","","","");
        assertThat(option.getLongId(), is(not(nullValue())));
    }

}
