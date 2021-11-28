package edu.neu.coe.info6205.sort.chinese;

import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.simple.QuickSort;
import edu.neu.coe.info6205.sort.simple.QuickSort_DualPivot;

public class QuickSortChinese_DualPivot {

    /**
     * This is an example of how to use quicksort_dualpivot to sort Chinese with CollatorHelper
     * @param xs
     */
    public static void sort(String[] xs) {
        // use original dual pivot quick sort and add a new Collator helper
        final CollatorHelper<String> helper = new CollatorHelper<>("Chinese Helper");
        QuickSort<String> sorter = new QuickSort_DualPivot<>(helper);
        sorter.sort(xs);
    }
}
