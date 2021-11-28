package edu.neu.coe.info6205.sort;

import com.ibm.icu.text.Collator;
import edu.neu.coe.info6205.util.Utilities;

import java.util.Locale;
import java.util.Random;
import java.util.function.Function;

public class CollatorHelper<X extends Comparable<X>> implements Helper<X> {

    /**
     * @return false
     */
    public boolean instrumented() {
        return true;
    }

    /**
     * Method to determine if one X value is less than another.
     *
     * @param v the candidate element.
     * @param w the comparand element.
     * @return true only if v is less than w.
     */
    public boolean less(X v, X w) {
        return collator.compare(v, w) < 0;
    }

    /**
     * Compare elements i and j of xs within the subarray lo..hi
     *
     * @param xs the array.
     * @param i  one of the indices.
     * @param j  the other index.
     * @return the result of comparing xs[i] to xs[j]
     */
    public int compare(X[] xs, int i, int j) {
        // CONSIDER invoking the other compare signature
        return collator.compare(xs[i], xs[j]);
    }

    /**
     * Compare v and w
     *
     * @param v the first X.
     * @param w the second X.
     * @return the result of comparing v and w.
     */
    @Override
    public int compare(X v, X w) {
        return collator.compare(v, w);
    }

    /**
     * Swap the elements of array a at indices i and j.
     *
     * @param xs the array.
     * @param i  one of the indices.
     * @param j  the other index.
     */
    public void swap(X[] xs, int i, int j) {
        X temp = xs[i];
        xs[i] = xs[j];
        xs[j] = temp;
    }

    /**
     * Method to perform a stable swap using half-exchanges,
     * i.e. between xs[i] and xs[j] such that xs[j] is moved to index i,
     * and xs[i] thru xs[j-1] are all moved up one.
     * This type of swap is used by insertion sort.
     *
     * @param xs the array of Xs.
     * @param i  the index of the destination of xs[j].
     * @param j  the index of the right-most element to be involved in the swap.
     */
    public void swapInto(X[] xs, int i, int j) {
        if (j > i) {
            X x = xs[j];
            System.arraycopy(xs, i, xs, i + 1, j - i);
            xs[i] = x;
        }
    }

    /**
     * Copy the element at source[j] into target[i]
     *
     * @param source the source array.
     * @param i      the target index.
     * @param target the target array.
     * @param j      the source index.
     */
    @Override
    public void copy(X[] source, int i, X[] target, int j) {
        target[j] = source[i];
    }

    @Override
    public boolean swapConditional(final X[] xs, final int i, final int j) {
        final X v = xs[i];
        final X w = xs[j];
        boolean result = collator.compare(v, w) > 0;
        if (result) {
            // CONSIDER invoking swap
            xs[i] = w;
            xs[j] = v;
        }
        return result;
    }

    @Override
    public boolean swapStableConditional(final X[] xs, final int i) {
        final X v = xs[i];
        final X w = xs[i - 1];
        boolean result = collator.compare(v, w) < 0;
        if (result) {
            xs[i] = w;
            xs[i - 1] = v;
        }
        return result;
    }

    /**
     * Method to determine if the given array (xs) is sorted.
     *
     * @param xs an array of Xs.
     * @return false as soon as an inversion is found; otherwise return true.
     */
    public boolean sorted(X[] xs) {
        for (int i = 1; i < xs.length; i++) if (collator.compare(xs[i - 1], xs[i]) > 0) return false;
        return true;
    }

    /**
     * Method to count the total number of inversions in the given array (xs).
     *
     * @param xs an array of Xs.
     * @return the number of inversions.
     */
    public int inversions(X[] xs) {
        int result = 0;
        for (int i = 0; i < xs.length; i++)
            for (int j = i + 1; j < xs.length; j++)
                if (collator.compare(xs[i], xs[j]) > 0) result++;
        return result;
    }

    public X[] random(Class<X> clazz, Function<Random, X> f) {
        if (n <= 0) throw new BaseHelper.HelperException("Helper.random: not initialized");
        return Utilities.fillRandomArray(clazz, random, n, f);
    }

    /**
     * Method to post-process the array xs after sorting.
     * By default, this method does nothing.
     *
     * @param xs the array to be tested.
     */
    @Override
    public void postProcess(X[] xs) {
    }

    @Override
    public String toString() {
        return "Helper for " + description + " with " + n + " elements";
    }

    public String getDescription() {
        return description;
    }

    /**
     * @param n the size to be managed.
     * @throws BaseHelper.HelperException if n is inconsistent.
     */
    public void init(int n) {
        if (this.n == 0 || this.n == n) this.n = n;
        else throw new BaseHelper.HelperException("Helper: n is already set to a different value");
    }

    public int getN() {
        return n;
    }

    public void close() {
    }

    /**
     * Constructor for explicit random number generator.
     *
     * @param description the description of this Helper (for humans).
     * @param n           the number of elements expected to be sorted. The field n is mutable so can be set after the constructor.
     * @param random      a random number generator.
     */
    public CollatorHelper(String description, int n, Random random) {
        this.n = n;
        this.description = description;
        this.random = random;
    }

    /**
     * Constructor for explicit seed.
     *
     * @param description the description of this Helper (for humans).
     * @param n           the number of elements expected to be sorted. The field n is mutable so can be set after the constructor.
     * @param seed        the seed for the random number generator.
     */
    public CollatorHelper(String description, int n, long seed) {
        this(description, n, new Random(seed));
    }

    /**
     * Constructor to create a Helper with a random seed.
     *
     * @param description the description of this Helper (for humans).
     * @param n           the number of elements expected to be sorted. The field n is mutable so can be set after the constructor.
     */
    public CollatorHelper(String description, int n) {
        this(description, n, System.currentTimeMillis());
    }

    /**
     * Constructor to create a Helper with a random seed and an n value of 0.
     *
     * @param description the description of this Helper (for humans).
     */
    public CollatorHelper(String description) {
        this(description, 0);
    }

    protected final String description;
    protected final Random random;
    protected int n;

    protected Collator collator;

    {
        collator = Collator.getInstance(Locale.CHINA);
    }
}
