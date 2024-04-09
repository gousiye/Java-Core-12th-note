# java核心技术 12th 卷1  第三章笔记




## String 
1. `String`<font color = "red">不能被更改</font>，通过构造一个新的String来改变。这样能使多个值一样的String引用同一块内存空间
2. 可以用`StringBuilder`来动态构造一个字符串。
3. `==`判断引用位置是否相等， `equals`判断两个值相等
4. 一般来说，1个字符是1个代码点。不过有些特殊字符可能由2个代码点组成。1个代码单元是存储字符的最小单位。<font color = 'red'>这条不是很确定</font>。 charAt 返回的是代码单元, codePointAt返回的是代码点


## Scanner 
1. Scanner in = new Scanner(System.in);
2. in.nextInt()，如果后面输入的是字符的话，会直接报错，不会说等到下一次输入数字的时候再执行这个。一般来说是确定下一个输入是Int才会用in.nextInt()。
3. Scanner可以用于读取文件，PrintWrier用于写文件


## Switch
1. case -> 不会自动下流。 case: 会自动下流
2. `yield`返回，相当于switch表达式的值
3. 在之前有yield的情况下， case -> 也有yield的作用


## Break && Continue
1. 没有<font color = 'blue'>goto</font>，通过`标签break`来实现代码跳跃，主要用于一下跳出多重循环。goto是放在跳出语句块的下方；break是放在跳出语句块的上方。
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
5. `Arrays.toString(())`可以将一个数组转换为String的形式
6. 遍历二维/多维Array的形式
```
for(int[] row: a){
    for(int item: a){
    }
} 
```
7.`int [][]a = new int[]`在C++中等价于`int **a = new int* []`    

## for each
1. 对于基本类型，`for(iter: varList)`, iter改变不会导致Array中的元素改变
2. 对于`String[] strs`，由于String不可以被更改，当执行`iter += "str"`时候，iter实际上被赋值为了一个新的子串，因此hascode被更改了，不再与strs[0]共用内存了，因此不会有影响。
3. 对于`integer`,`for(iter: varList)`,iter和varList[i]中的hasCode()就是数值本身。虽然相同，但是iter的改变<font color = "red">不改变varList</font>
4. 对于`Object`类型，iter和objList[i]指向同一块内存，iter的更改会导致objList的改变。

## args
1. Java中args不包含程序名；C++中args包含程序名。