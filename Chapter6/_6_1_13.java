import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.System.out;

class Point implements Comparable<Point> {
    public int x = 0;
    public int y = 0;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point other) {
        return x - other.x;
    }

    @Override
    public String toString() {
        return "(x = " + x + ", y = " + y + ")";
    }

}

class Cmp implements Comparator<Point> {
    @Override
    public int compare(Point first, Point second) {
        return first.y - second.y;
    }
}

public class _6_1_13 {
    public static void main(String[] args) {
        var array = new ArrayList<Point>();
        array.add(new Point(-1, 2));
        array.add(new Point(-2, 4));
        array.add(new Point(13, 34));
        array.add(new Point(-234, 5));
        Point[] arr = array.toArray(new Point[0]);
        Arrays.sort(arr, new Cmp());
        out.println(Arrays.toString(arr));
    }
}