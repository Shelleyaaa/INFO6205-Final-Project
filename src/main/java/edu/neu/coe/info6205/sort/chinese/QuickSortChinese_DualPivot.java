package edu.neu.coe.info6205.sort.chinese;

import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.simple.QuickSort;
import edu.neu.coe.info6205.sort.simple.QuickSort_DualPivot;

public class QuickSortChinese_DualPivot {

    public static String[] sort(String[] xs) {
        // use original dual pivot quick sort and add a new Collator helper
        final CollatorHelper<String> helper = new CollatorHelper<>("Chinese Helper");
        QuickSort<String> sorter = new QuickSort_DualPivot<>(helper);
        return sorter.sort(xs);
    }
}
