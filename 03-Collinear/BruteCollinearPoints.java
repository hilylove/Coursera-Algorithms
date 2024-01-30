/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
        Point[] copy = points.clone();
        Arrays.sort(copy);
        if (hasDuplicate(copy)) throw new IllegalArgumentException();
        for (int p = 0; p < copy.length - 3; p++) {
            for (int q = p + 1; q < copy.length - 2; q++) {
                double slopePQ = copy[p].slopeTo(copy[q]);
                for (int r = q + 1; r < copy.length - 1; r++) {
                    double slopePR = copy[p].slopeTo(copy[r]);
                    if (slopePQ == slopePR) {
                        for (int s = r + 1; s < copy.length; s++) {
                            double slopePS = copy[p].slopeTo(copy[s]);
                            if (slopePQ == slopePS) {
                                segments.add(new LineSegment(copy[p], copy[s]));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }


    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
