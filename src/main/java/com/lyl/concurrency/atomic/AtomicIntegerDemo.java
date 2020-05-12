package com.lyl.concurrency.atomic;

import com.lyl.concurrency.annoations.ThreadSafe;
import com.lyl.concurrency.threadPool.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class AtomicIntegerDemo {

    private static AtomicInteger count = new AtomicInteger(0);
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPool = ThreadPoolUtils.getThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool.submit(()->{
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                add10K();
            });
        }
        countDownLatch.await();
        log.info("count结果{}",count);
        threadPool.shutdown();
    }

    private static void add10K() {
        log.info("执行add10K 时间{}",System.currentTimeMillis());
        int idx = 0;
        while (idx++ < 10000) {
            count.incrementAndGet();
        }
        countDownLatch.countDown();
    }
}
