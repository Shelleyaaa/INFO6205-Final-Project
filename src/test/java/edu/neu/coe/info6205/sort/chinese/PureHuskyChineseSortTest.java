package edu.neu.coe.info6205.sort.chinese;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.info6205.util.GetWordsUtil;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PureHuskyChineseSortTest {

    String[] input = new String[]{"刘持平", "洪文胜", "樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏",
            "舒冬梅", "杨腊香", "许凤山", "王广风", "黄锡鸿", "罗庆富", "顾芳芳", "宋雪光", "张三", "张四", "张安"};
    String[] expected = new String[]{"曹玉德", "樊辉辉", "高民政", "顾芳芳", "洪文胜", "黄锡鸿", "刘持平",
            "罗庆富", "舒冬梅", "宋雪光", "苏会敏", "王广风", "许凤山", "杨腊香", "袁继鹏", "张安", "张三", "张四"};

    @Test
    public void testSortString() {
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder,
                false, false);
        String[] result = sorter.sort(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testSort() {
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder,
                false, false);
        String[] chinese = new String[]{"张安安", "张安三", "张安安安", "张安安三", "张三", "张四", "张安"};
        String[] after = new String[]{"张安", "张安安", "张安安安", "张安安三", "张安三", "张三", "张四"};
        String[] result = sorter.sort(chinese);
        assertArrayEquals(after, result);
    }

    @Test
    public void sort() {
        String[] words = GetWordsUtil.getWords("/shuffledChinese.txt", GetWordsUtil::lineAsList,
                PureHuskyChineseSortTest.class);
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder,
                false, true);
        String[] result = sorter.sort(words);
        assertEquals("阿安", result[0]);
    }

    @Test
    public void testWithInsertionSort() {
        int n = 200;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L);
        helper.init(n);
        String[] words = GetWordsUtil.getWords("/chinese-words-test.txt", GetWordsUtil::lineAsList,
                PureHuskyChineseSortTest.class);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder,
                false, true);
        String[] result = sorter.sort(xs);
        assertEquals("曹玉德", result[0]);
        assertEquals("张四", result[199]);
    }

    @Test
    public void preProcessTest() {
        PureHuskyChineseSort<String> sorter = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeChineseCoder,
                false, false);
        String[] res = sorter.preProcess(input);
        assertArrayEquals(res, input);
    }
}
