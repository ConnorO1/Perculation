/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONF95 = 1.96;
    private final double[] arr;
    private final int tt;
    private final double ttt;
    private final double mean;
    private final double stdev;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n must be greater than zero...");
        }

        arr = new double[trials];
        tt = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;


            int cnt = 0;
            while (!perc.percolates()) {
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    cnt++;
                }
                row = StdRandom.uniform(n) + 1;
                col = StdRandom.uniform(n) + 1;
            }
            arr[i] = (double) cnt / (n * n);
        }
        mean = mean();
        stdev = stddev();
        ttt = Math.sqrt(tt);

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arr);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - ((CONF95 * stdev) / (ttt));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + ((CONF95 * stdev) / (ttt));
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats pp = new PercolationStats(n, trails);
        System.out.printf("mean                     = %f\n", pp.mean());
        System.out.printf("stddev                   = %f\n", pp.stddev());
        System.out.printf("confidence interval      = %f, %f \n", pp.confidenceLo(),
                          pp.confidenceHi());
    }
}
