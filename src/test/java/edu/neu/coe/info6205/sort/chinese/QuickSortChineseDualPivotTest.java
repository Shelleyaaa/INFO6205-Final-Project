package edu.neu.coe.info6205.sort.chinese;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.simple.Partition;
import edu.neu.coe.info6205.sort.simple.Partitioner;
import edu.neu.coe.info6205.sort.simple.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.GetWordsUtil;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class QuickSortChineseDualPivotTest {

    String[] input = new String[]{"刘持平", "洪文胜", "樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏",
            "舒冬梅", "杨腊香", "许凤山", "王广风", "黄锡鸿", "罗庆富", "顾芳芳", "宋雪光", "张三", "张四", "张安"};
    String[] expected = new String[]{"曹玉德", "樊辉辉", "高民政", "顾芳芳", "洪文胜", "黄锡鸿", "刘持平",
            "罗庆富", "舒冬梅", "宋雪光", "苏会敏", "王广风", "许凤山", "杨腊香", "袁继鹏", "张安", "张三", "张四"};

    @Test
    public void sort() {
        String[] res = QuickSortChinese_DualPivot.sort(input);
        assertArrayEquals(expected, res);
    }

    @Test
    public void sort1() {
        int n = 200;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L);
        helper.init(n);
        String[] words = GetWordsUtil.getWords("/chinese-words-test.txt", GetWordsUtil::lineAsList,
                QuickSortChineseDualPivotTest.class);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        String[] res = QuickSortChinese_DualPivot.sort(xs);
        assertEquals("曹玉德", res[0]);
        assertEquals("张四", res[199]);
    }

    @Test
    public void testSortWithChinesePartition() {
        int n = input.length;
        final CollatorHelper<String> helper = new CollatorHelper<>("test");
        QuickSort_DualPivot<String> sorter = new QuickSort_DualPivot<>(helper);
        Partitioner<String> partitioner = sorter.createPartitioner();
        List<Partition<String>> partitions = partitioner.partition(new Partition<>(input, 0, input.length));
        Partition<String> p0 = partitions.get(0);
        sorter.sort(input, 0, p0.to, 0);
        Partition<String> p1 = partitions.get(1);
        sorter.sort(input, p1.from, p1.to, 0);
        Partition<String> p2 = partitions.get(2);
        sorter.sort(input, p2.from, n, 0);
        assertArrayEquals(expected, input);
    }

}
