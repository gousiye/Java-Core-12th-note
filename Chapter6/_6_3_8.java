interface Func {
    void show();
}

public class _6_3_8 {
    public static void main(String[] rgs) {
        Func test = new Func() {
            public void show() {
                System.out.println("s");
            }
        };
        var obj = new Object() {
            void display(Func func) {
                func.show();
            }
        };
        obj.display(test::show);
    }
}