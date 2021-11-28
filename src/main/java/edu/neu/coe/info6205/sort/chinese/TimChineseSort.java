package edu.neu.coe.info6205.sort.chinese;

import com.ibm.icu.text.Collator;

import java.util.Arrays;
import java.util.Locale;

public class TimChineseSort {

    public static void sort(String[] xs){
        Collator collator = Collator.getInstance(Locale.CHINA);
        Arrays.sort(xs, collator::compare);
    }

    public static String[] preProcess(String[] a) {
        return a;
    }
}
