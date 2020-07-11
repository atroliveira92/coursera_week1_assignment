import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[][] openArray;
    private int openSites = 0;
    private int upSync = 0;
    private int bottomSync;
    private int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be < 0");
        }
        this.n = n;
        uf = new WeightedQuickUnionUF((n * n) + 2);
        openArray = new boolean[n][n];

        bottomSync = (n * n) + 1;
    }

    // opens the site (row, col) if it is not open already
    // I could place the (row * n) - (n - col) to get the Id in a method. But the Assessment Report says
    // that the api must match the exacly API, no extra and no missing methods)
    public void open(int row, int col) {
        if (row <= 0 || row > openArray.length || col <= 0 || col > openArray.length) {
            throw new IllegalArgumentException("row and column does not match grid index " + n + " by " + n);
        }
        openArray[row - 1][col - 1] = true;
        int id = (row * n) - (n - col);

        if (row == 1) {
            uf.union(upSync, id);
        } else if (row == openArray.length) {
            uf.union(bottomSync, id);
        }
        int toUnion;
        //check up
        if (row > 1 && isOpen(row - 1, col)) {
            toUnion = ((row - 1) * n) - (n - col);
            uf.union(toUnion, id);
        }
        //check right
        if (col < openArray.length && isOpen(row, col + 1)) {
            toUnion = (row * n) - (n - (col + 1));
            uf.union(toUnion, id);
        }
        //check left
        if (col > 1 && isOpen(row, col - 1)) {
            toUnion = (row * n) - (n - (col - 1));
            uf.union(toUnion, id);
        }
        //check bottom
        if (row < openArray.length && isOpen(row + 1, col)) {
            toUnion = ((row + 1) * n) - (n - col);
            uf.union(toUnion, id);
        }
        openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > openArray.length || col <= 0 || col > openArray.length) {
            throw new IllegalArgumentException("row and column does not match grid index " + n + " by " + n);
        }
        return openArray[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > openArray.length || col <= 0 || col > openArray.length) {
            throw new IllegalArgumentException("row and column does not match grid index " + n + " by " + n);
        }
        if (!isOpen(row, col))
            return false;

        int id = (row * n) - (n - col);
        return uf.find(id) == uf.find(upSync);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(upSync) == uf.find(bottomSync);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
