/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private int trials;
    private double[] openSiteFractions;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.openSiteFractions = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            boolean[][] pickedSites = new boolean[n][n];
            while (!p.percolates()) {
                int row;
                int col;
                do {
                    row = StdRandom.uniformInt(n);
                    col = StdRandom.uniformInt(n);
                } while (pickedSites[row][col]);
                p.open(row + 1, col + 1);
                pickedSites[row][col] = true;
            }
            openSiteFractions[i] = (double) p.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSiteFractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSiteFractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java PercolationStats <gridSize> <trials>");
            return;
        }

        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(gridSize, trials);

        System.out.printf("mean = %.5f%n", percolationStats.mean());
        System.out.printf("stddev = %.17f%n", percolationStats.stddev());
        System.out.printf("95%% confidence interval = [%.15f, %.15f]%n",
                          percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }

}
