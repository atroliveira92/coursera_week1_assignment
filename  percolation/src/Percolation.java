import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final boolean[][] openArray;
    private int openSites = 0;
    private final int virtualRootUpSync = 0;
    private final int virtualRootBottomSync;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be < 0");
        }
        this.n = n;
        //Initialize quick-find union with the grid size and +2 for up virtual root and bottom virtual root
        uf = new WeightedQuickUnionUF((n * n) + 2);
        //Matrix to represent the open sites
        openArray = new boolean[n][n];
        //initialize virtual root bottom with n by n + 1
        virtualRootBottomSync = (n * n) + 1;
    }

    // opens the site (row, col) if it is not open already
    // I could place the (row * n) - (n - col) to get the Id in a method. But the Assessment Report says
    // that the api must match the exactly API, no extra and no missing methods)
    public void open(int row, int col) {
        if (row <= 0 || row > openArray.length || col <= 0 || col > openArray.length) {
            throw new IllegalArgumentException("row and column does not match grid index " + n + " by " + n);
        }
        // the row,col from method starts with 1, so -1 to open the right sites on array
        openArray[row - 1][col - 1] = true;
        // get the id of the open site
        int id = (row * n) - (n - col);

        // if open any site from first row, so automatically connect with up virtual root
        // if open any site from last row, so automatically connect with bottom virtual root
        if (row == 1) {
            uf.union(virtualRootUpSync, id);
        } else if (row == openArray.length) {
            uf.union(virtualRootBottomSync, id);
        }
        int toUnion;

        //check if above site is open to connect
        if (row > 1 && isOpen(row - 1, col)) {
            toUnion = ((row - 1) * n) - (n - col);
            uf.union(toUnion, id);
        }
        //check if right site is open to connect
        if (col < openArray.length && isOpen(row, col + 1)) {
            toUnion = (row * n) - (n - (col + 1));
            uf.union(toUnion, id);
        }
        //check if left site is open to connect
        if (col > 1 && isOpen(row, col - 1)) {
            toUnion = (row * n) - (n - (col - 1));
            uf.union(toUnion, id);
        }
        //check if below site is open to connect
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

        // Get the row, col id
        int id = (row * n) - (n - col);
        // check if the id is connect with the virtual upper root to say it is full
        return uf.find(id) == uf.find(virtualRootUpSync);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        //With virtual roots is easy, just check if bottom and upper root match. If so, the system percolates
        return uf.find(virtualRootUpSync) == uf.find(virtualRootBottomSync);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
