package edu.neu.coe.info6205.util;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class BenchmarkResultTest {

    @Test
    public void extendArrayTest() {
        String[] res = BenchmarkResult.extendArray(new String[]{"1", "2", "3"}, 2);
        assertArrayEquals(res, new String[]{"1", "2", "3", "1", "2", "3"});
    }
}
