# java核心技术 12th 卷1  第三章笔记




## String 
1. `String`不能被更改，通过构造一个新的String来改变。这样能使多个值一样的String引用同一块内存空间
2. `==`判断引用位置是否相等， `equals`判断两个值相等
3. 一般来说，1个字符是1个代码点。不过有些特殊字符可能由2个代码点组成。1个代码单元是存储字符的最小单位。<font color = 'red'>这条不是很确定</font>。 charAt 返回的是代码单元, codePointAt返回的是代码点


## Scanner 
1. Scanner in = new Scanner(System.in);
2. in.nextInt()，如果后面输入的是字符的话，会直接报错，不会说等到下一次输入数字的时候再执行这个。一般来说是确定下一个输入是Int才会用in.nextInt()。
3. Scanner可以用于读取文件，PrintWrier用于写文件


## Switch
1. case -> 不会自动下流。 case: 会自动下流
2. `yield`返回，相当于switch表达式的值
3. 在之前有yield的情况下， case -> 也有yield的作用
