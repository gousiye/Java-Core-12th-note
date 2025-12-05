# java核心技术 12th 卷1  第八章笔记 泛型

## 8.1 为何需要泛型
1. 泛型之前，使用的是`Object`
2. `var Obj = new MyClass<T>();`等价于`MyClass<T> Obj = new MyClass<>();`
3. `var Obj = new MyClass<>`<font color = "red">实际中不要这样写</font>。`new MyClass<>`在编译时会根据上下文自动推断模板类型；这里又使用了var，也没有其它上下文，因此`MyClass<>`会被推断为`MyClass<?>`,表示一个未知的类型。编译器为了安全等原因，<font color = "red">生成的字节码中会擦除泛型类型，用原生类型替换。</font>对于`<?>`，如果没有指定上限的泛型类型，会用`<Object>`代替，即在运行中`MyClass<?>`会表现出`MyClass<Object>`的性质。<font color = "red">如果有其它上下文，即使是`var Obj = new MyClass<>`也是可以推断为具体类型的，例如：</font>
   ```
   class MyClass<T> {
        public MyClass(T value) {
            System.out.println(value.getClass());
        }
    }
    public class Test{
        public static void main(String[] rgs) {
            var myClass = new MyClass<>(1);
        }
    }   
   ```
   其中<T>被推导为了Integer。
4. `<>`和`<?>`是不同的。`<>`根据上下文推断类型，`<?>`等价于`<? extends Object>`，没有指定类型限制。
5. 类型擦除和声明对象无关。类型擦除后的类型只取决于<font color = "red">泛型类定义中</font>`<? extends xxx>`中的xxx类型，`Box<String> b1 = new Box<>("hello");`和``Box<? extends xxx> b1 = new Box<>("hello");``不影响类擦除后的类型。
6. `Box<String> b1 = new Box<>("hello");` 中的`String`是告诉编译器让Object强转为`String`。

## 8.2 简易实现泛型类
1. 定义一个最简单的泛型类
   ```
   class MyClass<T, U>{}
   ```

## 8.3 泛型方法
1. 定义模板方法
   ```
   public static <T> T Func(T value){}
   ```
2. 调用模板方法
   ```
   MyClass.<String>.Func("SD");
   ```
   `<String>`大部分情况可以省略，但是可能有多种类型同时满足，例如
   ```
   Func(1.4,1.2,0);
   ```
   T可以被解释为`double`或者`Comparable`。这种时候需要通过`<double>`显示指出T的类型。
3. Java中<>在方法名前，`<a>f()`C++中<>在方法名后`f<a>()`

## 8.4 泛型类型限定
1. **某些泛型方法需要泛型变量具有某些性质，例如sort需要泛型变量实现Comparable接口**。限制泛型变量的方法：
   ```
   public static <T extends Class/Interface> T min(T[] a)
   ```
   **限制了T类型的变量必须是给定类的子类（或者实现给定的接口）。**可以同时指定泛型变量的多个限制，使用`&`连接。<font color = "red">限制只有`and`逻辑，没有`or`逻辑</font>
   ```
   public static <T extends classA & interfaceB> void show(){} 
   ```
    <font color = "red">同继承一样，限制中最多只能有一个类，而且类要放到第一个</font>
   <font color = "orange">to add</font>

## 8.5 泛型代码和虚拟机
1. Java中的泛型在编译中会被擦除，用具体的类代替。这个具体的类是所有**泛型限定类型的第一个**。如果没有泛型类型限定，则使用`Object`代替泛型。例如
   ```
        interface Base
        class A implements Base{}
        interface B extends Base{}
        interface C extends Base{}

        public class GenericClass<T extends A & B & C> {
        }
   ```
   擦除后会用`A`进行替代泛型。因此建议将`tagging interface`放到限制的后面。<font color = "red">类型擦除后，泛型类实际上是个普通类，所有泛型参数由擦除后的类型代替。`GenericClass<T>`在虚拟机中是`GenericClass`。</font>
2. C++中的泛型会为每种类型生成具体的方法或者类，会导致代码臃肿。<font color = "red">Java中泛型生成的字节码中只有一份。例如`<int>Func`, `<double>Func`，实际的字节码中都只有一份`Func`。</font>
3. 上述的擦除规则会导致<font color = "red">子类继承模板类，无法进行虚函数的重写。</font>例如：
   ```
    class MyClass<T> {
        public T value;
        public void Show(T value){
            System.out.println("Parent");
        }
    }
    class SubClass extends MyClass<String>{
        public void Show(String value){
            System.out.println("Child");
        }
    }

    public class Test{
        public static void main(String[] rgs) {
            MyClass<String> test = new SubClass();
            test.Show("SD");
        }
    }
   ```
   在`MyClass`擦除后，方法变为了`public void Show(Object value)`,与子类中Show接受的参数的类型不同，因此子类并没有重写父类的虚函数。
   <font color = "red">但实际上，上述代码能正常实现多态。原因在于编译器会在`SubClass`自动生成`Bridge Method`
   ```
   public void Show(Object value){
    Show((String)value)
   }
   ```
   `test`调用`SubClass`中的函数`Show(Object value)`,再通过`Bridge Method`实现多态。
   </font>
   <br>
   `Bridge Method`可能会导致如下情况：
   ```
    class MyClass<T> {
        public T value;
        public T Show(){
            System.out.println("Parent");
            return value;
        }

    }
    class SubClass extends MyClass<String>{
        public String Show(){
            System.out.println("Child");
            return value;
        }
    }

    public class Test{
        public static void main(String[] rgs) {
            MyClass<String> test = new SubClass();
            test.Show();
        }
    }
   ```
   `SubClass`中有继承来的`public Object Show()`，也有重写的函数`public String Show()`。这种情况正常是不应该出现的，但是在上述情况下可以的。
4. `Bridge Method`也使得子类重写父类虚函数的时，返回类型不用严格一致，可以是父类返回类型的派生类。

## 8.6 限制
1. 泛型不能使用基本类型，要进行装箱，例如`T<Integer>` 
2. 泛型类型比较只能比较泛型类本身，不能比较带有参数的类型。例如`class Pair<T>`只能比较`Pair`类，无法进行`Pair<T>`的比较。**因为Javax虚拟机中式没有泛型的。**
3. 不能简单声明一个泛型对象数组，例如
   ```
   var table = new Pair<String>[10]; //Error
   ```
   因为擦除后，`table = new Pair<Integer>`能通过编译，但实际上类型却不符合。使用`ArrayList<Pair<String>>`生成泛型对象数组。
4. **虽然本质上是数组，但是可以使用泛型可变参数**。通过`@SafeVarargs`来消除警告。<font color = "red">方法对泛型参数只读</font>
5. 在泛型类中，想要声明T类型的实例，不能直接`var t = new T()`可以使用`Supplier<T>`方法接口来实现。
    ```
    Pair<String> p = Pair.makePair<Stirng::new>;
    public static <T> Pair<T> (makePair<Supplier<T> constr){
        return new Pair<>(constr.get(), constr.get());
    }
    ```
    这样，就在p中声明了两个T(String)类型的变量。
6. 泛型中数组，<font color = "orange">有点没看懂。</font>
7. 由于擦除，实际的模板类只有一份，因此<font color = "red">泛型类型不能出现在静态字段静态方法中。</font>
8. 通过泛型`throw T`，可以自己手动抛出一个`RunTimeExcepton`等的`unchecked exception`。这能将一个`checked exception`转为一个`unchecked exception`，在多进程/多线程可能有用。
9. 在Java中，一个泛型类型不能同时实现（或继承）两个具有不同泛型参数的相同接口。即
    ```
    // Error
    class A implements Comparable<Integer>, Comparable<String>
    ```
    因为`Bridge Method`可能会冲突。

## 8.7 泛型继承结构
1. `A extends B`, 但是`T<A>`不是`T<B>`的子类。
2. 所有的泛型特化类型都可以转为擦除后的原生类型。

## 8.8 通配符类型
1. `Pair<? extends Employee>`表示泛型类型是Employee的子类即可，没有强制规定一种明确的泛型类型。<font color = "red">能够get，不能set。</font>不知道具体的Employee的哪种程度的子类，set相当于`child = parent`。
2. `Pair<? super Manager>`表示泛型类型是Manager的超类。<font color = "red">能够set，不能get(get返回为Object可以)。</font>
3. `public static <T extends Comparable<? super T>> Pair<T> minmax(T[] a)`是一个比较好限制泛型类型的例子。
4. `pair<?>`。<font color = "red">不能进行`set`。`get`返回的是`Object`类型。</font>一般用于简单的操作。例如`public static boolean hasNulls(Pair<?> p)`，比使用`public static <T> boolean hasNulls(Pair<T> p)`要简洁很多。
5. `<T extends ClassA>`用于泛型类的上下边界限定，`<? extends ClassA>`用于一个方法（**可以不是泛型方法**）参数的限定。
## *8.9 反射与泛型
1. 反射就是获取`class`, `field`, `constructor`, `method`，动态获得一个类的全部信息，包括私有的
2. 反射是动态代理和`AOP`的基础