/**
 * jcombinatorics:
 * Java Combinatorics Library
 *
 * Copyright (c) 2009 by Alistair A. Israel.
 *
 * This software is made available under the terms of the MIT License.
 * See LICENSE.txt.
 *
 * Created Sep 2, 2009
 */
package jcombinatorics.combinations;

import java.util.Iterator;

import jcombinatorics.Generator;
import jcombinatorics.util.ReadOnlyIterator;

/**
 * A combinations generator based on combinadics. Capable of computing the
 * <i>i</i>-th combination directly using {@link #get(long)}.
 *
 * @author Alistair A. Israel
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Combinadic">http://en.wikipedia.org/wiki/Combinadic</a>
 * @see <a
 *      href="http://msdn.microsoft.com/en-us/library/aa289166%28VS.71%29.aspx">Generating
 *      the mth Lexicographical Element of a Mathematical Combination, James
 *      McCaffrey</a>
 */
public class CombinadicCombinationsGenerator2 implements Generator<int[]> {

    private final int n;

    private final int k;

    private final long count;

    /**
     * @param n
     *        the number of elements to choose from
     * @param k
     *        taken <code>k</code> at a time
     */
    public CombinadicCombinationsGenerator2(final int n, final int k) {
        this.n = n;
        this.k = k;
        this.count = Combinations.count(n, k);
    }

    /**
     * Retrieve the <i>l</i>-th combination.
     *
     * @param l
     *        long
     * @return int[]
     */
    public final int[] get(final long l) {
        final int[] a = new int[k];
        computeInto(a, l);
        return a;
    }

    /**
     * Computes the <i>l</i>-th combination into the given array
     *
     * @param a
     *        an array
     * @param l
     *        long
     */
    private void computeInto(final int[] a, final long l) {
        int nn = n;
        long m = count - l - 1;

        for (int i = k; i > 0; --i) {
            // in-lined find largest v
            int v = nn;
            long c = count(v, i);
            while (c > m) {
                c = c * (v - i) / v;
                --v;
            }
            m -= c;
            a[k - i] = (n - 1) - v;
            nn = v;
        }
    }

    /**
     * @param n
     *        n
     * @param k
     *        k
     * @return C(n,k)
     */
    private static int count(final int n, final int k) {
        int count = 1;
        for (int i = 0; i < k; ++i) {
            count = count * (n - i) / (i + 1);
        }
        return count;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Iterable#iterator()
     */
    public final Iterator<int[]> iterator() {
        return new IteratorImpl();
    }

    /**
     * @author Alistair A. Israel
     */
    private class IteratorImpl extends ReadOnlyIterator<int[]> {

        private long l = count;

        private final int[] a = new int[k];

        /**
         * {@inheritDoc}
         *
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            return l > 0;
        }

        /**
         * {@inheritDoc}
         *
         * @see java.util.Iterator#next()
         */
        public int[] next() {
            int nn = n;
            long m = l - 1;
            for (int i = k; i > 0; --i) {
                // find largest v
                int v = nn;
                long c = count(v, i);
                while (c > m) {
                    c = c * (v - i) / v;
                    --v;
                }
                m -= c;
                a[k - i] = (n - 1) - v;
                nn = v;
            }
            --l;
            return a;
        }

    }

}
