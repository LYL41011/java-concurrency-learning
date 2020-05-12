package com.lyl.concurrency.happensbefore;

import java.util.concurrent.*;

/**
 * 三、传递性规则
 * (1)和(2)在同一个线程中，根据规则1，(1)Happens-Before于(2)
 * (3)和(4)在同一个线程中，同理，(3)Happens-Before于(4)
 * 根据规则2，由于v用了volatile修饰，那么(2)必然 Happens-Before于(3)。那么根据传递性规则可得：(1)Happens-Before于(4)，因此x必然为42。所以即使x没有用volatile，它也是可以保证可见性的！
 */
public class HappensBefore3 {
    private static ExecutorService esExecutorService = new ThreadPoolExecutor(500,
            1000, 60, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(20000), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    });
    private int x = 0;
    private int y = 0;
    private volatile boolean flag = false;
    //private boolean flag = false;

    public void writer() {
        x = 42;//（1）
        flag = true; //（2）
    }

    public void reader() {
        if (flag) {//（3）
            y = x;//（4）
        }
        System.out.println(y);
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        HappensBefore3 happensBefore3 = new HappensBefore3();
        for (int i = 0; i < 500; i++) {

            esExecutorService.submit(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                happensBefore3.writer();
                happensBefore3.reader();
            });

        }

        countDownLatch.countDown();
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

