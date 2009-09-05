/**
 * jcombinatorics:
 * Java Combinatorics Library
 *
 * Copyright (c) 2009 by Alistair A. Israel.
 *
 * This software is made available under the terms of the MIT License.
 * See LICENSE.txt.
 *
 * Created Aug 31, 2009
 */
package jcombinatorics.combinations;

import jcombinatorics.util.ArrayUtils;
import jcombinatorics.util.ReadOnlyIterator;

/**
 * <p>
 * An iterator that generates combinations of <code>n</code> elements,
 * <code>C(n)</code>, in lexicographic order, based on Rosen's algorithm.
 * </p>
 *
 * @author Alistair A. Israel
 * @see "Kenneth H. Rosen, Discrete Mathematics and Its Applications, 2nd edition (NY: McGraw-Hill, 1991), pp. 284-286"
 */
public class RosenIterator extends ReadOnlyIterator<int[]> {

    private final int n;

    private final int k;

    private int[] a;

    private long count;

    /**
     * @param n
     *        the number of elements
     * @param k
     *        taken k at a time
     */
    public RosenIterator(final int n, final int k) {
        super();
        this.n = n;
        this.k = k;
        count = 1;
        for (int i = 0; i < k; ++i) {
            count = count * (n - i) / (i + 1);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see java.util.Iterator#hasNext()
     */
    public final boolean hasNext() {
        return count > 0;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.util.Iterator#next()
     */
    public final int[] next() {
        if (a == null) {
            initialize();
        } else {
            int i = k - 1;
            while (a[i] == n - k + i) {
                i--;
            }
            a[i] = a[i] + 1;
            for (int j = i + 1; j < k; j++) {
                a[j] = a[i] + j - i;
            }
        }
        --count;
        return a;
    }

    /**
     *
     */
    private void initialize() {
        this.a = ArrayUtils.identityPermutation(k);
    }

}
