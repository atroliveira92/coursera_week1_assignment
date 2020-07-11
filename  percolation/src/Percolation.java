import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[][] openArray;
    private int[][] ids;
    private int openSites = 0;
    private int upSync = 0;
    private int bottomSync;
    private boolean percolate = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        uf = new WeightedQuickUnionUF((n * n) + 2);
        openArray = new boolean[n][n];
        ids = new int[n][n];

        bottomSync = (n * n) + 1;
        int id = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ids[i][j] = id;
                id++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        openArray[row - 1][col - 1] = true;
        openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openArray[row - 1][col - 1];
    }

    private int getId(int row, int col) {
        return ids[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col))
            return false;

        boolean union = false;
        int id = getId(row, col);

        if (row == 1) {
            uf.union(upSync, id);
            union = true;
        } else if (row == openArray.length) {
            uf.union(bottomSync, id);
        }

        //check up
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(getId(row - 1, col), id);
            union = true;
        }
        //check right
        if (col < openArray.length && isOpen(row, col + 1)) {
            uf.union(getId(row, col + 1), id);
            union = true;
        }
        //check left
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(getId(row, col - 1), id);
            union = true;
        }
//        //check bottom
//        if (row < openArray.length && isOpen(row + 1, col)) {
//            uf.union(getId(row + 1, col), id);
//            union = true;
//        }

        if (union) {
            return uf.find(id) == uf.find(upSync);
        }
        return false;
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
