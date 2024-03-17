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


## Break && Continue
1. 没有<font color = 'cyan'>goto</font>，通过`标签break`来实现代码跳跃，主要用于一下跳出多重循环。goto是放在跳出语句块的下方；break是放在跳出语句块的上方。
2. 也有类似的`标签continue`


## Big Numbers
1. 在`java.math`中
2. `BigDecimal`要用String来赋值，不要直接用小数，会有精度问题
3. 没有运算符重载


## Array
1. 数组声明的时候可以用变量，例如`int[] a = new int [100]`。本质上就是C++中的`new`，不过不用自己delete了。
2. 通过initializer_list进行赋值，例如`int[] arrayTest = {1,2,3,4}`
3. 数组中元素Java会自动进行初始化。数值类为0, boolean为False, 对象类为null。
4. `array1 = array2`，则两者共用同一块内存。`Array.copyOf()`实现深拷贝

## for each
1. 对于基本类型，`for(iter: varList)`, iter改变不会导致Array中的元素改变
2. 对于`String[] strs`，`for(iter: strs)`, iter的hashcode始终是strs[0]的hashcode。由于String不可以被更改，当执行`iter += "str"`时候，iter实际上被赋值为了一个新的子串，因此hascode被更改了，不再与strs[0]共用内存了，因此不会有影响。
3. 对于`integer`,`for(iter: varList)`iter和varList[i]中的hashCode()不同，两者不在一个内存区域，不会相互影响。 

## args
1. Java中args不包含程序名；C++中args包含程序名。