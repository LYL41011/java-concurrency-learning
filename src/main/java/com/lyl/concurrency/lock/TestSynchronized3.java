package com.lyl.concurrency.lock;

/**
 * 深入分析锁定的对象和受保护资源的关系，综合考虑受保护资源的访问路径，多方面考量才能用好互斥锁
 * 受保护资源和锁之间的关联关系是 N:1 的关系
 * 如果一个资源用N个锁，那肯定出问题的，就好像一个厕所坑位，你有10把钥匙，那不是可以10个人同时进了？
 */
public class TestSynchronized3 {

    /**
     * 此种代码会出问题
     * 因为我们说过synchronized修饰普通方法 锁定的是当前实例对象 this 而修饰静态方法 锁定的是当前类的 Class 对象
     * 所以这里有两把锁 分别是 this 和 TestSynchronized3.class
     * 由于临界区 get() 和 addOne() 是用两个锁保护的，因此这两个临界区没有互斥关系，临界区 addOne() 对 value 的修改对临界区 get() 也没有可见性保证，这就导致并发问题了。
     * 正确代码应该是把static去掉，这样他们就是同一个锁。
     */
    static long value1 = 0L;

    synchronized long get1() {
        return value1;
    }

    synchronized static void addOne1() {
        value1 += 1;
    }


    /**
     * 此种代码也会出问题
     * 加锁本质就是在锁对象的对象头中写入当前线程id，但是synchronized (new Object())每次在内存中都是新对象，所以加锁无效。
     */
    private final Object lock1 = new Object();//不要用non-final的field来作为锁，non final的对象可能会随时被改变，而导致两个线程synchronize on different object
    long value = 0L;

    long get() {
        //错误代码
//        synchronized (new Object()) {
//            return value;
//        }
        synchronized (lock1) {
            return value;
        }
    }


    void add() {
//        synchronized (new Object()) {
//            value += 1;
//        }
        synchronized (lock1) {
            value += 1;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestSynchronized3 test = new TestSynchronized3();
        // 创建两个线程，执行 add() 操作
        Thread th1 = new Thread(() -> {
            test.add();
        });

        Thread th2 = new Thread(() -> {
            System.out.println(test.get());
        });
        // 启动两个线程
        th1.start();
        th2.start();
        // 等待两个线程执行结束
        th1.join();
        th2.join();
    }
}

