package edu.neu.coe.info6205.sort.chinese;

import edu.neu.coe.info6205.sort.CollatorHelper;
import edu.neu.coe.info6205.sort.simple.Partition;
import edu.neu.coe.info6205.sort.simple.Partitioner;
import edu.neu.coe.info6205.sort.simple.QuickSort_DualPivot;

import java.util.List;

public class QuickSortChinese_DualPivot {

    public static void sort(String[] xs){
        int n = xs.length;
        // use original dual pivot quick sort and add a new Collator helper
        final CollatorHelper<String> helper = new CollatorHelper<>("Chinese Helper");
        QuickSort_DualPivot<String> sorter = new QuickSort_DualPivot<>(helper);
        Partitioner<String> partitioner = sorter.createPartitioner();
        List<Partition<String>> partitions = partitioner.partition(new Partition<>(xs, 0, xs.length));
        Partition<String> p0 = partitions.get(0);
        sorter.sort(xs, 0, p0.to, 0);
        Partition<String> p1 = partitions.get(1);
        sorter.sort(xs, p1.from, p1.to, 0);
        Partition<String> p2 = partitions.get(2);
        sorter.sort(xs, p2.from, n, 0);
    }
}
