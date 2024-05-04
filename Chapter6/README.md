# java核心技术 12th 卷1  第六章笔记 接口，匿名表达式，内部类

## 6.1 接口
1. 接口只描述做了什么，不涉及具体的实现过程。即只有方法名称，没有方法实现。
2. 一个类可以实现多个接口。但是一个类不能有多个基类。
3. 接口中的方法默认都是public。接口中主要有三种类型的方法：
   1. 静态方法
   2. 抽象方法
   3. 默认方法

    如果一个类没有全部实现接口中的抽象方法，那么这个类需要是抽象类.
4. 实现接口的方法与重写虚函数的条件一样, return的类不一定需要严格一致，例如下面代码是可行的：
   ```
   class Parent{
    }
   interface InterfaceTest {
       public Parent Get();
   }
   public class Test extends  Parent implements InterfaceTest{
        public Test Get(){
            return this;
        }
   }
   ```
5. <font color = "red">重要！对同一个方法，优先级如下：派生类，基类，接口。 具体方法执行方案如下：</font>
   1. 基类，派生类没有实现接口方法 。
      1. 接口有默认实现，按照接口默认实现方法。
      2. 如果一个类实现多个接口，多个接口有相同的方法。只要有一个接口有默认实现，那么编译器会报错，不知道用哪种方法。
      3. 接口没有默认实现。要么基类和派生类都是抽象类，要么就报错。
    2. 基类实现接口方法，但是派生类没有实现接口方法。基类，派生类使用基类的实现。
    3. 基类没有实现接口方法，但是派生类实现接口方法。<font color = "red">这种时候基类是个抽象类</font>
    4. 基类，派生类都有实现接口方法。基类使用基类方法，派生类使用派生类方法。
6. <font color = "red">如果一个类实现了多个接口，如果存在同名现象，可以通过&lt;接口名+super&gt;指定是哪个接口的成员。如果同时该类的基类也有同名的成员，可以通过super指定基类的成员。例如</font>
   ```
   interface A{
        void show(){out.println("A");}
   }
   interface B{
        void show(){out.println("B");}
   }
   class Base{
        void show(){out.println("Base");}
   }
   class Derived extends Base implements A, B{
        void show(){out.println("Derived");}
        void Test(){
            A.super.show();
            B.super.show();
            super.show();
            this.show();
        }
   }
   ```
7. 可以声明接口引用，指向实现该接口的类的对象。`instanceof`也可以用来判断这个对象所处的类是否实现了这个接口。
8. 接口也可以继承接口，派生接口可以为基类接口提供抽象方法的默认实现.类似的，接口也可以被声明为`sealed`，指定继承的接口或者实现的类。
9. 接口中可以有`public static final`类型的字段   
10. 接口中可以有私有方法，私有方法必须是静态的，作为辅助方法使用，只能被接口中方法调用，实现类不能调用。
11. 接口中的默认方法可以让实现的类不用进行改动即可编译、调用新的方法。
12. <font color = "red">Java通过实现接口方法的对象实现回调</font>。例如`Timer`，通过`ActionListener`接口中的`actionPerformed`方法来实现每次计时结束后执行的操作。
    ```
    class TimerPrint implements ActionListener
    @Override
    public void actionPerformed(ActionEvent event){
        ...
    }
    var listener = TimerPrint(); 
    var timer = new Timer(1000,listener);
    ```
    上述例子中将`listener`对象进行回调。
13. <font color = "red">Comparable&lt;T&gt;和Comparator&lt;T&gt;类似C++中sort重载<和声明cmp函数。Arrays.sort()使用了Comparator&lt;T&gt;的对象话，会按照对象中实现的compare进行排序。否则使用Comparable&lt;T&gt;中定义的比较进行排序。</font>`Comparator.naturalOrder()`是按照`Comparable.compareTo`进行比较的
    [_6_1_13.java](_6_1_13.java)
14. `clone`是`Object`中的`protected`方法，任何对象可以访问。<font color = "red">但是Object.clone()声明了CloneNotSupportedException。当类没有实现`Cloneable`接口调用Object.clone()，会抛出该异常。因此一个类不实现`Cloneable`，无法调用`clone`。`Cloneable`接口本身不提供clone方法，只是作为一个标志，标记这个类可以进行clone。`clone`实际是重写`Object.clone()`方法。</font>`Cloneable`实际上是个`tagging interface`，用于进行判断。`if (obj instance of Cloneable)`
15. `clone`默认行为同C++中的`=`，为浅复制。这会导致如果一个对象中有子对象的引用，经过clone后，两个对象中的子对象实际上指向同一块内存。要实现自己的clone()，类实现`Cloneable`方法，然后重写`clone()`方法。


# 6.2 Lambda表达式
1. Lambda的基本形式`()->{}`，与Vue中的类似。<font color = "red">Lambda主要是用`函数接口`的一个临时匿名实现</font>
2. `函数接口(functional interface)`。<font color = "red">只有一个抽象方法的对象或者接口。</font>主要为了简洁性，避免歧义。函数接口主要是表示某一个行为，通过实现类或者lambda提供具体的实现。
3. Lambda本质上是针对于一个函数接口， 省略了实现类，对象的创建。正因如此，才要求函数接口只有一个抽象方法。
4. 使用Lambda可以声明"接口实例"。例如`Runnable`是一个接口，下述代码是可行的：
   ```
   Runnable run = () -> {}
   ```
   本质上`run`是一个实现`Runnable`匿名类的对象。
5. Lambda会捕获Lambda式外层代码的变量，要求该变量`从始至终没有被更改过`，但可以不用声明为final
6. Lambda中的`this`于其它方法中的`this`相同，并非是实现函数接口匿名类的对象。
7. `方法引用(method reference)`是Lambda表达式的一种特殊情况。当Lambda表达式的函数体是<font color = "red">调用一个现成方法的时候</font>，可以直接使用函数引用，避免Lambda的编写。方法引用会根据函数接口中的参数自动进行函数重载。有三种情况。
   1. *object::instanceMethod*: 等价于Lambda函数体中该对象根据函数接口参数调用重载的方法。`this:instanceMethod`,`super::instanceMethod`这些也都是可以的。
   2. *class::instanceMethod*，方法接口的第一个参数是`this`。相当于`this.instanceMethod()`。
   3. *class::staticMethod*，等价于Lambda函数体根据函数接口参数调用对应的静态方法。
8. `class::new`是构造函数的方法引用。<font color = "orange">在模板那块可能比较有用</font>
9. Lambda主要用于**延期执行**。使用Lambda多用于回调的情况。
10. 方法A使用Lambda函数：在A的参数列表中加入函数接口的实例，在A的方法体中通过实例调用方法。
11. 函数接口，Lambda方法的建立和使用，方法引用的使用参考文件[_6_2_10.java](_6_2_10.java)

## 6.3 内部类
1. Java中类的定义都存放在**方法区**中。对于内部类，**内部类的定义在方法区中只存在一份**。不会因为外部类对象的增加导致浪费。
2. Java中的内部类创建对象时，构造器会自动添加外部类的引用，内部类对可以直接访问外部类的`private`成员；而C++没有，需要在内部类中显示声明外部类的指针。
3. Java中内部类可以通过&lt;OuterClass&gt;.this来显示表示外部类。使用&lt;OuterObj&gt;.new &lt;InnerClass&gt;可以创建内部类对象。使用&lt;OuterClass&gt;.&lt;InnerClass&gt;来表示内部类。
4. 内部类也是默认4种访问权限，跟普通成员相同。
5. 每部类的**静态成员必须是常量**。否则不同外部类对象的内部类静态成员会有歧义。内部类没有静态方法。
6. 内部类还可以声明在一个方法中，称为`Local Inner Class`这样有更强的封装性。只有外部类的该方法可以访问内部类。`Local Inner Class`在可以访问外部类成员的同时，还可以访问该方法中的本地变量。**不过这些本地变量不能被更改，就像Lambda捕获外面的变量一样。**同样，当方法结束后，内部类仍然可以可以方法中的本地变量，因为内部类会将这些本地变量复制到自己的字段中。
7. 如果只需要一个对象，可以创建匿名类:
   ```
   var obj = new SuperClass/Interface(){
   }
   ```
   SuperClass：则匿名类继承自SuperClass
   Interface: 则匿名类实现该接口
8. `Lambda`和`内部类`的综合使用可参考[_6_3_8.java](_6_3_8.java)
9. `静态内部类`可以防止内部类获取外部类的引用。静态内部类可以有对象，静态方法。

## *6.4 Service Loaders
<font color = "orange>有点看不懂，先跳过，需要用到卷2的知识</font>"

## *6.5 Proxy
<font color = "orange">暂时对我overqualified了，先跳过</font>