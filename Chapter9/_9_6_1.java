package Chapter9;

import java.util.*;

class MyClass implements Comparable<MyClass> {
    public int a = 0;
    public int b = 0;

    public MyClass(int _a, int _b) {
        this.a = _a;
        this.b = _b;
    }

    @Override
    public int compareTo(MyClass other) {
        return Integer.compare(this.a, other.a);
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ") ";
    }

};

public class _9_6_1 {
    public static void main(String[] args) {
        var memo = new ArrayList<MyClass>();
        memo.add(new MyClass(1, 2));
        memo.add(new MyClass(-1, 2));
        memo.add((new MyClass(-123, 34)));
        // memo.sort((first, second)->{return first.a - second.a;});
        // memo.sort(Comparator.naturalOrder());
        Collections.sort(memo, (first, second) -> {
            return first.a - second.a;
        });
        System.out.println(memo.toString());
    }
}