package com.oocl.jenkinsdemo;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Xuesong Mei on 23/09/2017.
 */
public class SampleUnitTest {
    @Test
    public void should_equals() throws Exception {
        SampleUnit unit = new SampleUnit();

        assertNull(unit.getLastValue());

    }
}
