@FunctionalInterface
interface FuncInterface {
    void show(int a);
}

public class _6_2_10 {
    public static void ConsumeFuncInterface(int num, FuncInterface f) {
        f.show(num);
    }

    public static void Show(int num) {
        System.out.println(num);
    }

    public static void main(String[] args) {
        ConsumeFuncInterface(1, _6_2_10::Show);
    }
}