package edu.neu.coe.info6205.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class CollatorHelperTest {

    private CollatorHelper collatorHelper = new CollatorHelper("test");

    @Test
    public void instrumented() {
        assertTrue(collatorHelper.instrumented());
    }

    @Test
    public void less() {
        assertTrue(collatorHelper.less("啊", "中"));
    }

    @Test
    public void compare() {
        String[] xs = new String[]{"啊", "中"};
        assertEquals(-1, collatorHelper.compare(xs, 0, 1));
        assertEquals(0, collatorHelper.compare(xs, 0, 0));
        assertEquals(1, collatorHelper.compare(xs, 1, 0));
    }

    @Test
    public void swap() {
        String[] xs = new String[]{"啊", "中"};
        collatorHelper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"中", "啊"}, xs);
        collatorHelper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"啊", "中"}, xs);
    }

    @Test
    public void sorted() {
        String[] xs = new String[]{"啊", "中"};
        assertTrue(collatorHelper.sorted(xs));
        collatorHelper.swap(xs, 0, 1);
        assertFalse(collatorHelper.sorted(xs));
    }

    @Test
    public void inversions() {
        String[] xs = new String[]{"啊", "中"};
        assertEquals(0, collatorHelper.inversions(xs));
        collatorHelper.swap(xs, 0, 1);
        assertEquals(1, collatorHelper.inversions(xs));
    }

    @Test
    public void postProcess() {
        String[] xs = new String[]{"啊", "中"};
        collatorHelper.postProcess(xs);
    }

    @Test
    public void random() {
        String[] words = new String[]{"啊", "中"};
        final Helper<String> helper = new CollatorHelper<>("test", 3, 0L);
        final String[] strings = helper.random(String.class, r -> words[r.nextInt(2)]);
        assertArrayEquals(new String[]{"中", "中", "啊"}, strings);
    }

    @Test
    public void testToString() {
        final Helper<String> helper = new CollatorHelper<>("test", 3);
        assertEquals("Helper for test with 3 elements", helper.toString());
    }

    @Test
    public void getDescription() {
        final Helper<String> helper = new CollatorHelper<>("test", 3);
        assertEquals("test", helper.getDescription());
    }

    @Test(expected = RuntimeException.class)
    public void getSetN() {
        final Helper<String> helper = new CollatorHelper<>("test", 3);
        assertEquals(3, helper.getN());
        helper.init(4);
        assertEquals(4, helper.getN());
    }

    @Test
    public void close() {
        final Helper<String> helper = new CollatorHelper<>("test");
        helper.close();
    }

    @Test
    public void swapStable() {
        String[] xs = new String[]{"啊", "中"};
        collatorHelper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"中", "啊"}, xs);
        collatorHelper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"啊", "中"}, xs);
    }

    @Test
    public void swapConditional() {
        String[] xs = new String[]{"中", "啊"};
        collatorHelper.swapConditional(xs, 0, 1);
        assertArrayEquals(new String[]{"啊", "中"}, xs);
    }

    @Test
    public void swapStableConditional() {
        String[] xs = new String[]{"中", "啊"};
        collatorHelper.swapStableConditional(xs, 1);
        assertArrayEquals(new String[]{"啊", "中"}, xs);
    }


}
