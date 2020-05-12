package com.lyl.concurrency.volatiledemo;

/**
 * 以下代码示例，理想情况下应该输出count=10,但是极小概率会出现count=0。
 * 原因是th1把静态变量 s=0 从主内存读到工作内存，再赋值，然后 s=3会先到工作内存，然后同步到主内存当中。重点：并不会立即同步到主内存
 * 所以th2去主内存中读取的时候，可能得到的是0而不是10
 * <p>
 * #########解决方案:volatile或者synchronized##########
 * Java利用volatile来提供可见性的。可见性是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。
 * 当一个变量被volatile修饰时，那么对它的修改会立刻刷新到主存，当其它线程需要读取该变量时，会去内存中读取新值。普通变量则不能保证这一点。
 * <p>
 * #########关于volatile的原理：重点理解 两个核心，三大性质#########
 * 两大核心：JMM内存模型（主内存和工作内存）以及happens-before；三条性质：原子性，可见性，有序性
 * <p>
 * Volatile 是轻量级的 synchronized。其实通过synchronized和Lock也能够保证可见性，线程在释放锁之前，会把共享变量值都刷回主存，但是synchronized和Lock的开销都更大。
 * 但是Volatile绝对不能代替synchronized 因为它的使用有限制：
 * 1.运行结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值。见TestVolatile2.java
 * 2.变量不需要与其他的状态变量共同参与不变约束。见TestVolatile3.java
 * <p>
 * 另外Volatile和Happens-before息息相关，请再阅读下HappensBefore1.java
 */
public class TestVolatile1 {
    private volatile static int count = 0;
    public static void main(String[] args) throws Exception {
        Thread th1 = new Thread(() -> {
            count = 10;
        });
        Thread th2 = new Thread(() -> {
            //没有volatile修饰count的话极小概率会出现等于0的情况
            System.out.println("count=" + count);
        });
        // 启动两个线程
        th1.start();
        th2.start();
    }
}

