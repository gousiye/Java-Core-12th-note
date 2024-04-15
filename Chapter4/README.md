# java核心技术 12th 卷1  第四章笔记

## 4.1 OOP基本介绍
1. OOP的编程顺序：确定类 --> 为类添加方法。
   通过“名词”确定类。通过“动词”确定方法。
2. 依赖，聚合，继承关系，UML类图。

## 4.2 预设类
1. Object Variable 相当于对象引用。Object是一个对象实例。
2. Java中，没有copy constructor和重载=， 通过clone进行拷贝  
3. Date类，和LocalDate类

## 4.3 自己设计类
1. 一个.java源文件中只能有一个public类，并且public类和.java的名字要相同
2. <font color = "red">`.class`是`.java`经过编译后产生的文件。</font>  
3. `.java`是utf-8类型的文件，可以直接打开，编辑，是源码文件。`.class`文件是二进制文件，不能被打开，编辑。是JVM虚拟机能够理解的中间形式的代码。
4. Java中有四种访问修饰符。`public`,`protected`,`default(默认不屑)`，`private`。其中`default`为默认，类成员只能被同一个包内的其它类访问。
5. Java中<font color = "red">所有对象都声明在堆上</font>。因此只能使用`new`创造对象。
6. `var`类似C++中的`auto`, 但是`auto`可以在形参列表中，而`var`不可以。
7. 在C++中，在类内定义的方法会被认为是inline的。在Java中，所有方法都只能定义在类内。由JVM来判断方法是否为inline。
8. <font color = "red">getter方法不要返回一个具有setter对象的引用。</font>因为通过引用的setter进行更改，破坏封装性。例如：
   ```
      Class a = new Class();
      b = a.getInnerClass();
      b.setInnerClass();
   ```
   通过b可以更改a中内部对象，破坏了封装性质。
   应该是通过.clone()返回一个副本。
9. method 可以访问这个类所有对象的私有成员。C++同理。

## 4.4 静态字段和方法
1. `native method`，一半由C/C++编写的本地方法，可以绕过`final`进行修改。
2. <font color = "red">工厂方法</font>。工厂方法是静态方法，用于生成实例。使用工厂方法的理由如下：
      1. 需要通过两种方式进行构造，对应不同构造方法名字。但是构造方法只能和类名一致。
      2. 构造方法不能更改构造对象的类型，工厂方法可以。工厂方法可以返回一个基类，便于多态。  
   
3. 每个类都可以有`main`方法。


## 4.5 方法参数
1. <font color = "red">Java中所有参数都是按值来传递的。对于对象类型，传递的是对象引用的值。</font>因此传递基本类型不会改变实参的值，传递对象类型会改变对象的值。
   ```
   var a = new Class("1");
   var b = new Class("2");
   swap(a, b);
   ```
   最终还是`a`指向`1`, `b`指向`2`。

## 4.6 对象构造
1. 同C++一样，仅靠返回值类型不能构成重载
2. 方法内的本地变量需要赋初始值。在对象中的字段会有默认值，可以不用赋初始值。
3. 构造函数的赋值会覆盖掉变量声明时给的默认值例如`private int a =12`
4. 显示变量初始化可以使用方法进行赋值，例如`private int id = Class.GetId();`
5. `this()`可以在一个构造方法中使用另一个构造方法。
6. `Initialization Blocks`提供初始化代码块，不常用。和`field initializes`按照代码顺序觉得覆盖顺序。
7. 对于static filed，可通过initialization blocks进行赋值
   ```
   static{
      static_var = ...
   }
   ```
8. 虽然Java有gc机制，<font color = "red">但是对象申请的系统资源仍然是需要释放的。可以使用`close`方法。</font>`finialize`在gc回收前执行，但是不确定什么时候回收，已经被弃用了。


## 4.7 Records
1. record是一种状态无法被更改的类。record中字段本质上为`private final`类型。
2. record的`getter`一般和字段同名，便于使用。
3. record可以添加静态字段，方法，普通方法。<font color = "red">但是不能添加新的字段。</font>
4. 会自动生成Canonical Constructor，将所有字段设置成形参的值
5. 使用compact方法，先处理形参的值，然后再传递给Canonical Constructor。例如:\
   ```
   record Range(int from, int to){
      public Range{
         if(from < 0) form = 0
         if(to < 0) to = 0
      }
   }
   ```
   在执行过程中，会先让from, to非负，然后再执行Range的Canonical Constructor

