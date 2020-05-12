package com.lyl.concurrency.happensbefore;

/**
 * 六、线程终结规则
 * 主线程 A 等待子线程 B 完成（主线程 A 通过调用子线程 B 的 join() 方法实现），
 * 如果在线程 A 中，调用线程 B 的 join() 并成功返回，那么主线程能够看到子线程的操作（指共享变量的操作），
 * 换句话说就是线程 B 中的任意操作 Happens-Before 于该 join() 操作的返回。 */
public class HappensBefore6 {
    private static long count = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread B = new Thread(() -> {
            count = 10;
        });

        // 主线程启动子线程
        B.start();
        // 主线程等待子线程完成
        B.join();
        // 子线程所有对共享变量的修改 在主线程调用 B.join() 之后皆可见
        System.out.println(count);//count必然为10
    }
}

