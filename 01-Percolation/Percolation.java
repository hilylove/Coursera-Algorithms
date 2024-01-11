import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[][] grid;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF sites2;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        grid = new boolean[n][n];
        virtualTop = 0;
        virtualBottom = n * n + 1;
        sites = new WeightedQuickUnionUF(n * n + 2);
        sites2 = new WeightedQuickUnionUF(n * n + 1);
        numberOfOpenSites = 0;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;
        grid[row - 1][col - 1] = true;
        numberOfOpenSites++;

        if (row == 1) {
            sites.union(virtualTop, xyToNumber(row, col));
            sites2.union(virtualTop, xyToNumber(row, col));
        }

        if (row == n) {
            sites.union(virtualBottom, xyToNumber(row, col));
        }

        connectNeighbour(row, col, row - 1, col);
        connectNeighbour(row, col, row + 1, col);
        connectNeighbour(row, col, row, col - 1);
        connectNeighbour(row, col, row, col + 1);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) return false;
        return sites2.find(virtualTop) == sites2.find(xyToNumber(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.find(virtualTop) == sites.find(virtualBottom);
    }

    private int xyToNumber(int row, int col) {
        validate(row, col);
        return (row - 1) * n + col;
    }

    private void validate(int row, int col) {
        if (!inGrid(row, col)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean inGrid(int row, int col) {
        int actualRow = row - 1;
        int actualCol = col - 1;
        return (actualRow >= 0 && actualCol >= 0 && actualRow < n && actualCol < n);
    }

    private void connectNeighbour(int row, int col, int newRow, int newCol) {
        if (inGrid(newRow, newCol) && isOpen(newRow, newCol)) {
            sites.union(xyToNumber(row, col), xyToNumber(newRow, newCol));
            sites2.union(xyToNumber(row, col), xyToNumber(newRow, newCol));
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 1);
        perc.open(1, 3);
        perc.open(3, 2);

        // StdOut.println(perc.isOpen(2, 2));
        StdOut.println(perc.isFull(2, 2));

    }
}
