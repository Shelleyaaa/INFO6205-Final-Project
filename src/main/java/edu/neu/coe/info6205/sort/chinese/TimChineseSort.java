package edu.neu.coe.info6205.sort.chinese;

import com.ibm.icu.text.Collator;

import java.util.Arrays;
import java.util.Locale;

public class TimChineseSort {

    public static String[] sort(String[] xs){
        Collator collator = Collator.getInstance(Locale.CHINA);
        String[] copy = preProcess(xs);
        Arrays.sort(copy, collator::compare);
        postProcess(xs);
        return copy;
    }

    public static String[] preProcess(String[] xs) {
        return Arrays.copyOf(xs, xs.length);
    }

    public static String[] postProcess(String[] xs){
        return xs;
    }
}
