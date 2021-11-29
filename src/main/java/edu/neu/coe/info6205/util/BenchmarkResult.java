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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BenchmarkResult {

    public static void main(String[] args) {
        sortChinese();
    }

    public static void sortChinese() {
        for (int i = 250000; i <= 4000000; i *= 2) {
            Helper<String> helper = new CollatorHelper<>("Chinese Helper", i);
            QuickSort<String> sorter = new QuickSort_DualPivot<>(helper);
            String[] words = getWords("shuffledChinese.txt", BenchmarkResult::lineAsList);
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

    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        try {
            final File file = new File(getPathname(resource, BenchmarkResult.class));
            final String[] result = getWordArray(file, stringListFunction, 2);
            System.out.println("getWords: testing with " + formatWhole(result.length) + " unique words: from " + file);
            return result;
        } catch (final FileNotFoundException e) {
            System.out.println("Cannot find resource: " + resource);
            return new String[0];
        }
    }

    private static List<String> getWordList(final FileReader fr, final Function<String,
            List<String>> stringListFunction, final int minLength) {
        final List<String> words = new ArrayList<>();
        for (final Object line : new BufferedReader(fr).lines().toArray())
            words.addAll(stringListFunction.apply((String) line));
        return words.stream().distinct().filter(s -> s.length() >= minLength).collect(Collectors.toList());
    }


    /**
     * Method to read given file and return a String[] of its content.
     *
     * @param file               the file to read.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @param minLength          the minimum acceptable length for a word.
     * @return an array of Strings.
     */
    static String[] getWordArray(final File file, final Function<String, List<String>> stringListFunction,
                                 final int minLength) {
        try (final FileReader fr = new FileReader(file)) {
            return getWordList(fr, stringListFunction, minLength).toArray(new String[0]);
        } catch (final IOException e) {
            System.out.println("Cannot open file: " + file);
            return new String[0];
        }
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }

    private static String getPathname(final String resource, @SuppressWarnings("SameParameterValue") final Class<?> clazz)
            throws FileNotFoundException {
        final URL url = clazz.getClassLoader().getResource(resource);
        if (url != null) return url.getPath();
        throw new FileNotFoundException(resource + " in " + clazz);
    }

    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    private static String[] extendArray(String[] a, int size) {
        String[] b = new String[a.length * size];
        int j = 0;
        while (j < size) {
            System.arraycopy(a, 0, b, a.length * j, a.length);
            j++;
        }
        return b;
    }


}
