package com.lyl.concurrency.finaldemo;

/**
 * 1.3 修饰变量
 * final成员变量表示常量，只能被赋值一次，赋值后其值不再改变。即你可以读取使用该参数，但是无法改变该参数的值。
 * 当final修饰一个基本数据类型时，表示该基本数据类型的值一旦在初始化后便不能发生变化；如果final修饰一个引用类型时，则在对其初始化之后便不能再让其指向其他对象了，但该引用所指向的对象的内容是可以发生变化的。本质上是一回事，因为引用的值是一个地址，final要求值，即地址的值不发生变化。　
 * final修饰一个成员变量（属性），必须要显示初始化。这里有两种初始化方式，一种是在变量声明的时候初始化；第二种方法是在声明变量的时候不赋初值，但是要在这个变量所在的类的所有的构造函数中对这个变量赋初值。
 */
public class FinalDemo {
    private static final String name="liuyanling";
    private static final Integer age=24;

    public static void main(String[] args) {

    }
}
