package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.chinese.LSDChineseSort;
import edu.neu.coe.info6205.sort.chinese.MSDChineseSort;
import edu.neu.coe.info6205.sort.chinese.PureHuskyChineseSort;
import edu.neu.coe.info6205.sort.chinese.TimChineseSort;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.info6205.sort.simple.QuickSort;
import edu.neu.coe.info6205.sort.simple.QuickSort_DualPivot;

import java.util.Arrays;

public class BenchmarkResult {

    public static void main(String[] args) {
        sortChinese();
    }

    public static void sortChinese() {
        for (int i = 250000; i <= 4000000; i *= 2) {
            Helper<String> helper = new CollatorHelper<>("Chinese Helper", i);
            QuickSort<String> sorter = new QuickSort_DualPivot<>(helper);
            String[] words = GetWordsUtil.getWords("/shuffledChinese.txt",
                    GetWordsUtil::lineAsList, BenchmarkResult.class);
            String[] xs;
            if (i < 1000000) {
                xs = Arrays.copyOfRange(words, 0, i);
            } else if (i == 1000000) {
                xs = words;
            } else {
                xs = extendArray(words, i / 1000000);
            }
            System.out.println("------------array size is " + i + "---------------");
            Benchmark<String[]> bm = new Benchmark<>(
                    "Chinese dual pivot quick sort", sorter::preProcess,
                    sorter::sort);
            double times = bm.run(xs, 5);
            System.out.println("when n is " + 5 + ", run time is " + times);

            Benchmark<String[]> bm1 = new Benchmark<>(
                    "MSD Chinese sort", MSDChineseSort::preProcess,
                    MSDChineseSort::sort);
            times = bm1.run(xs, 5);
            System.out.println("when n is " + 5 + ", run time is " + times);

            LSDChineseSort lsd = new LSDChineseSort();
            Benchmark<String[]> bm2 = new Benchmark<>(
                    "LSD Chinese sort", lsd::preProcess,
                    lsd::sort);
            times = bm2.run(xs, 5);
            System.out.println("when n is " + 5 + ", run time is " + times);

            Benchmark<String[]> bm3 = new Benchmark<>(
                    "Chinese Tim sort", TimChineseSort::preProcess,
                    TimChineseSort::sort);
            times = bm3.run(xs, 5);
            System.out.println("when n is " + 5 + ", run time is " + times);

            PureHuskyChineseSort<String> huskyChineseSort = new PureHuskyChineseSort<>(HuskyCoderFactory.unicodeCoder,
                    false, false);
            Benchmark<String[]> bm4 = new Benchmark<>(
                    "Chinese husky sort", huskyChineseSort::preProcess,
                    huskyChineseSort::sort);
            times = bm4.run(xs, 5);
            System.out.println("when n is " + 5 + ", run time is " + times);
        }
    }

    public static String[] extendArray(String[] a, int size) {
        String[] b = new String[a.length * size];
        int j = 0;
        while (j < size) {
            System.arraycopy(a, 0, b, a.length * j, a.length);
            j++;
        }
        return b;
    }


}
