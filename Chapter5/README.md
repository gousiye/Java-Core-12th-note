# java核心技术 12th 卷1  第五章笔记 继承

## 5.1 类，超类，子类
1. `class child extends class parent`。child继承了parent。<font color = "red">Java中只有公共继承</font>
2. record不能被继承，也不能继承其它类
3. <font color = "red">super指向基类。但并不是一个引用，不能将super的值赋值给其它对象。</font>通过super可以访问基类的成员，方法。
4. this()在一个函数中调用自己的构造函数，super()在一个函数中调用父类的构造函数。<font color = "red">super()和this()其它构造函数必须在类中构造函数中的第一句</font>
5. Java中没有`virtual`关键词，不过有`@Override`，重写函数就能实现多态。一般虚函数写在`Interface`中。C++中没有`Interface`，要实现多态必须要有`virtual`。
6. <font color = "red">C++，Java重写虚函数访问权限可以更加开放。</font>下面列子是可以的。
    ```
    class Base{
        protected show(){}
    } 
    class Derived extends Base{
        public show(){}
    }
    ```
    C++虚函数基类方法可以是`private`，而Java不可以。
7. Java不支持三角继承。作为补偿，Java中一个类可以同时实现多个接口。
8. <font color = "red">形参的名称不作为函数签名，因此无论Java还是C++，重写虚函数的形参名称可以不同</font>
9. 如果基类虚函数返回基类类型，派生类的虚函数可返回派生类型。虚函数的返回类型不是严格相等，是`compatible`。这里的本质是编译器自动在子类上生成了`Bridge Method`，参见[第8_5_3条](../Chapter8/README.md)
10. `函数重载`是静态绑定，`虚函数`是动态绑定。动态绑定需要this这个implict parameter。
11. `final class`不能被继承，且所有的方法是`final method`。`final method`不能被派生类重写。`final field`，定义后值不能被更改。对于`final object`的field字段来说，相当于是C++中的`Type * const p`，可以更改引用的值，但是不能引用其它对象。
12. Java中通过`instanceof`实现类似C++中的`dynamic_cast`：基类向下转换为派生类。
    ```
    if base instanceof Derived{
        var obj = (Derived) base
    }
    ```
13. 从Java16开始可以使用：
    ```
    if (obj instanceof Base base)
    ```
    如果能够转换，那(Base)obj会自动绑定到base上

## 5.2 Object类
1. 基本类型不是对象，但是基本类型的数组是对象，例如`int[]`是对象类型。
2. Object.equals()是比较引用是否相等。
3. records中自动重写了`equals`方法，默认就是比较所有字段是否相同。
4. 重写`equals`时，有`getClass`和`instanceof`两种判断类别的方式。
    ```
    otherObj instanceof this.getClass()
    ```
    容易违反equals的对称性。
    如果派生类有自己的相等方式，那么应该使用`getClass`；如果派生类和基类的相等方式相同，那么使用`instanceof`。
5. `Objects.equals(a,b)`处理了a, b可能为null的情况。a, b都没有null，则调用`a.equals(b)`
6. `object + <str>`会自动调用object.toString()方法
   
## 5.3 泛型数组ArrayList
1. `ArrayList`类似于C++中的`vector`
2. <font color = "red">var array = new ArrayList<>()会返回一个ArrayList&lt;Object&gt;</font>，具体原因参考[第8_1_3条](../Chapter8/README.md)
3. ArrayList中没有`[]`,使用`get`和`set`
4. ArrayList默认也是每次扩容变为原来的1.5倍
5. 编译器会将所有ArrayList&lt;Type&gt;转为ArrayList&lt;&gt;。在虚拟机运行期间，所有都是无类型的ArrayList&lt;&gt;。因此`(ArrayLlist<Typename>object)`会有警告。

## 5.4 包装器和自动装箱
1. Integer, Double, Boolean, ..., 是包装器(wrapper)。<font color = "red">包装器对象是immutable的。</font>
    ```
    Integer a = 12;
    a = 24;
    ```
    这里的本质其实是a指向了一个新的引用，并没有更改引用的值是
2. ArrayList&lt;Integer&gt;效率低于int[]，因为每个元素都一个Integer的包装器
3. <font color = "red">重要！</font>包装器的`==`默认也是比较两个对象引用是否在同一个内存上。使用`equals`来判断是否相等。例如
   ```
   Integer a = 1000;
   Integer b = 1000;
   a == b // false
   ```
   <font color = "red">不使用wrapper对象的相等性，不要将其当作锁</font>
4. 装箱，拆箱是编译器执行的，与虚拟机无关。
5. <font color = "red">int 和 Integer 类型不能构成函数重写, 例如</font>
   ```
   class Base{
    public int a(){return 1;}
   }
   class Derived extends Base{
    @Override
    public Integer a(){return 2;}
   }
   ```
   @Override会报错，并非是重写

## 5.5 可变参数列表
1. `Function(Type... values)` 实现可变参数列表。本质上values是一个Type类型的数组。
2. 实参可以是`Type[] values`，会绑定到`Type... values`上
3. values是实参数组的一个引用


## 5.6 抽象类
1. 抽象类和抽象方法与C++中的类似，只要类中有一个方法是抽象方法，这个类就是抽象类。抽象类中也可以没有抽象方法。
2. 子类继承抽象类后，两种方案：
   1. 实现所有的抽象方法，子类可以不是抽象类
   2. 实现部分抽象方法， 子类仍是抽象类。
3. 抽象类不能被实例化，但是抽象类引用可以指向派生类的实例
4. <font color = "red">Interface的工作类似抽象类</font>

## 5.7 枚举类
1. 
    ```
    public enum Size{SMALL, MEDIUM, BIG}
   ```
   声明一个枚举类。<font color = "red">枚举类没有实例</font>
2. 构造方法总是为`private`
3. 赋值方法如下：
    ```
    Size s = Enum.valueOf(Size.class " Small")
    ```
4. `valueOf`返回枚举常量数组；`ordinal`返回枚举常量的索引。


## 5.8 密封类
1. Sealed Class只允许指定的子类继承它。
    ```
    public sealed class Shape permits Circle, Triangle
    ```
    可以没有`permit`，默认是permits相同文件下的类。
2. Sealed Class的派生类有三种类选择：
   1. 也是Sealed Class
   2. 是final
   3. `non-sealed`，为普通的类
3. <font color = "orange">List5.13结合了Interface, sealed, record, enumeration，可以借鉴</font> 

## *5.9 反射机制(先跳过)
1. 反射机制作用：
   1. 运行期间分析类
   2. 运行期间检查对象
   3. 执行通用数组操作代码
   4. 类似于C++中的函数指针
   
    反射机制撒主要用于构建工具

