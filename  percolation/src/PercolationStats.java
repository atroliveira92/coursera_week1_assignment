import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trial must be > 0");
        }
        double[] threshold = new double[trials];
        int grid = n * n;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n + 1);
                int col = StdRandom.uniform(n + 1);
                if (row <= 0) {
                    row = 1;
                }
                if (col <= 0) {
                    col = 1;
                }
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            threshold[i] = (double) percolation.numberOfOpenSites() / grid;
        }

        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);
        double aux = 1.96 * stddev / Math.sqrt(trials);
        confidenceLo = mean - aux;
        confidenceHi = mean + aux;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = 20;
        int trial = 1;

        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            trial = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStats = new PercolationStats(n, trial);

        System.out.println("mean " + percolationStats.mean());
        System.out.println("stddev " + percolationStats.stddev());
        System.out.println("95% confidence interval [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
