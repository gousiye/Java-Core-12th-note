# java核心技术 12th 卷1  第七章笔记 异常，断言，日志

## 7.1 抛出异常
1. `Throwable`是所有异常的基类。
2. `Error`主要是Java运行系统内部或者资源错误，一般不抛出这种错误，而且也没办法处理这种错误。一般是由系统抛出这些错误。
3. `RuntimeException`主要是程序没写好，例如除以0，超索引；**一般这些都是自己代码的问题。**等价于C++中的`logic_error`。C++中`runtime_error`是Java中所有非`RuntimeException`的异常。
4. <font color = "red">`Error`和`RuntimeException`都是`unchecked exception`，应该尽力避免异常发生；其它是`checked exception`，应该进行捕获处理。</font>
5. 对于虚函数来说，子类抛出的异常应该小于等于父类的异常。
6. `throws`算作方法签名的部分，不构成重载，例如:
   ```
   void show ();
   void show() throws Exception
   ```
   这两者不构成重载，会报错
7. 通过继承，简单的构建自己的异常类：
   ```
   class MyException extends IOException{
    public MyException(){}
    public MyException(String gripe){
        super(gripe);
    }
   }
   ```

## 7.2 捕获异常
1. `try-catch-finally`块。其中`7.1`中所提及的`throws`应当在`try中`。
2. `catch`如果同时有异常类本身和异常类的基类，会报错。
3. `catch`可以对多个异常采取同样的处理措施:`catch(MyExceptionA | MyExceptionB)`
4. `e.message()`获取异常信息。
   `catch`可以嵌套，`catch`再处理异常的时候可能会出现新的异常。<font color = "red">一般是用于更改抛出的异常的种类</font>
5. `e.initCause(origin)`将origin设为原始异常；`e.getCause()`获取原始异常
6. `try-catch-finally`中即使有return,也会执行完finally中的语句再进行return。这会导致finally中的return会覆盖掉try和catch中return的值。<font color = "red">因此finally一般是关闭资源用的。</font>
7. 使用`try-with-resources`可以保证资源正常关闭，无论有无异发生。
    ```
    try (Resource res1 = ...;Resource res2){
    }
    Resource res3 = ...
    try(res)
    ```    
    有点类似于Python中的`with open file`
8. `throwable`类中有`printStackTree()`可以进行栈追踪。<font color = "red">不如断点来得实在</font>

## 7.3 Exception小寄巧
1. 对于`unchecked exception`，用`if`会比`try-catch`快很多。
2. throw early, catch late

## 断言
1. `assert`断言，更多用于测试，检查代码结果是否正确；`try-catch`更多用于异常处理。
2. 在开发，测试环境中，`assert`起到检查作用;<font color = "red">在生产环境中，`assert`应该被去掉</font>
3. 断言失败会抛出异常`AssertionError`, 为`Error`错误，因此asser的错误是**致命的**，不应该被try-catch处理。
4. 一般根据`javadoc`文档注释来判断是否需要assert
5. assert可以用来记录假设的条件
   
## 7.5 日志
1. 第三方的日志框架比Java原生的更好用。**原生的Java log直接跳了**
2. `Log4J 2`是比较主流的日志框架；`SLF4J`类似于Python中的Conda，便于在各个日志框架中切换。
3. 以下是SLF4J接入Log4J进行日志输出的过程：
    1. 构建`Maven`项目
    2. pom.xml中添加两者的依赖，以及SLF4J绑定Log4J 2的依赖
    3. 如果有多个日志框架，排除SLF4J和其它的日志框架
    4. 在`src/main/java/resources`下创建`log4j2.xml`，设定日志的格式
    5. .java中代码如下：
        ```
        import lombok.extern.slf4j.Slf4j;

        @Slf4j
        public class Test {
            public void DoSomething(){
                log.info("日志测试");
            }

            public static void main(String[] args){
                var test = new Test();
                test.DoSomething();
            }
        }
        ```
