/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 *  Reference: https://github.com/MolinDeng/Princeton-algs4/tree/main/04Lab-8Puzzle
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static int[][] DIR = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    private int n;
    private int[][] tiles;
    private int hammingD;
    private int manhattanD;
    private int emptyPosX;
    private int emptyPosY;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        this.tiles = new int[tiles.length][tiles[0].length];
        this.n = tiles.length;
        for (int i = 0; i < n; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], n);
        }

        // calculate hamming distance and manhattan distance
        int h = 0, m = 0, px = 0, py = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    px = i;
                    py = j;
                    continue;
                }
                int val = tiles[i][j] - 1;
                int man = Math.abs(val / n - i) + Math.abs(val % n - j);
                if (man > 0) h++;
                m += man;
            }
        }
        manhattanD = m;
        hammingD = h;
        emptyPosX = px;
        emptyPosY = py;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingD;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanD;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hammingD == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    private boolean isSwapable(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    private int[][] copyTiles() {
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> p = new ArrayList<>();
        int swapi, swapj;
        for (int[] dir : DIR) {
            swapi = emptyPosX + dir[0];
            swapj = emptyPosY + dir[1];
            if (isSwapable(swapi, swapj)) {
                int[][] newTiles = copyTiles();
                newTiles[emptyPosX][emptyPosY] = newTiles[swapi][swapj];
                newTiles[swapi][swapj] = 0;
                p.add(new Board(newTiles));
            }
        }
        return p;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = copyTiles();
        int[] indices = new int[4];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != emptyPosX || j != emptyPosY) {
                    indices[k++] = i;
                    indices[k++] = j;
                    if (k == indices.length) break;
                }
            }
            if (k == indices.length) break;
        }
        int t = newTiles[indices[0]][indices[1]];
        newTiles[indices[0]][indices[1]] = newTiles[indices[2]][indices[3]];
        newTiles[indices[2]][indices[3]] = t;
        return new Board(newTiles);

    }


    public static void main(String[] args) {
        int[][] tiles = { { 1, 0 }, { 2, 3 } };
        Board b = new Board(tiles);
        StdOut.println(b.hamming());
    }
}
