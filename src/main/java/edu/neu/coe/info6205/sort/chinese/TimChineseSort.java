package edu.neu.coe.info6205.sort.chinese;

import com.ibm.icu.text.Collator;
import com.ibm.icu.util.ULocale;
import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.simple.TimSort;

import java.util.Arrays;
import java.util.Comparator;

public class TimChineseSort {


    public static void sort(String[] xs){
        Collator collator = Collator.getInstance(ULocale.CHINA);
        Arrays.sort(xs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return collator.compare(o1, o2);
            }
        });
    }
}
