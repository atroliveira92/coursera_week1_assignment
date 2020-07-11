import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int grid;
    private int trials;
    private int openSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.grid = n * n;
        this.trials = trials;

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
                System.out.println("row " + row + " column " + col);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    percolation.isFull(row, col);
                    openSites++;
                }

            }
        }

        System.out.println("openSites " + openSites + " grid " + grid);
    }

    // sample mean of percolation threshold
    public double mean() {
        return (double) openSites / grid;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }


    public static void main(String[] args) {
        int n = 20;
        int trial = 1;

//        if (args.length == 2) {
//            n = Integer.parseInt(args[0]);
//            trial = Integer.parseInt(args[1]);
//        }
        PercolationStats percolationStats = new PercolationStats(n, trial);

        System.out.println("n = " + n + " trial = " + trial);

        System.out.println("mean " + percolationStats.mean());

    }
}
