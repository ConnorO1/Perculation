/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int opSites = 0;
    private final int nn;
    private final WeightedQuickUnionUF wquOb;
    private boolean[][] table1;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        wquOb = new WeightedQuickUnionUF(n * n + 2);
        nn = n;

        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero...");
        }

        // initialise table to all zero's (representing blocked)

        else {
            table1 = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    table1[i][j] = false;
                }
            }
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        helper(row, col);

        if (!isOpen(row, col)) {
            table1[row - 1][col - 1] = true;
            opSites++;
            if (row == 1) {
                // connect virtual node to first row and last row of percolation
                wquOb.union(nn * nn, xyTo1d(row, col));
            }

            if (row == nn) {
                wquOb.union(nn * nn + 1, xyTo1d(row, col));
            }
        }

        upper(row, col);
        lower(row, col);
        left(row, col);
        right(row, col);

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        helper(row, col);
        return table1[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        helper(row, col);
        return wquOb.find(nn * nn) == wquOb.find(xyTo1d(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opSites;
    }


    // does the system percolate?
    public boolean percolates() {
        // create a false node each side and connect to top and bottom row
        return wquOb.find(nn * nn) == wquOb.find(nn * nn + 1);
    }

    // map 2d coordinates to 1d array

    private int xyTo1d(int row, int col) {
        int cxx = (row - 1) * (nn) + (col - 1);
        return cxx;

    }

    // private helper function to validate indices

    private void helper(int row, int col) {
        if ((row > nn) || (row < 1) || (col > nn) || (col < 1)) {
            throw new IllegalArgumentException("row and col not in bounds");
        }
    }

    private boolean helper2(int row, int col) {
        if ((row > nn) || (row < 1) || (col > nn) || (col < 1)) {
            return false;
        }
        return true;
    }

    // join adjacent sites if connected

    private void upper(int row, int col) {
        if (helper2(row - 1, col) && isOpen(row - 1, col)) {
            wquOb.union(xyTo1d(row - 1, col), xyTo1d(row, col));
        }
    }

    private void lower(int row, int col) {
        if (helper2(row + 1, col) && isOpen(row + 1, col)) {
            wquOb.union(xyTo1d(row + 1, col), xyTo1d(row, col));
        }
    }

    private void left(int row, int col) {
        if (helper2(row, col - 1) && isOpen(row, col - 1)) {
            wquOb.union(xyTo1d(row, col - 1), xyTo1d(row, col));
        }
    }

    private void right(int row, int col) {
        if (helper2(row, col + 1) && isOpen(row, col + 1)) {
            wquOb.union(xyTo1d(row, col + 1), xyTo1d(row, col));
        }
    }


}

