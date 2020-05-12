package com.lyl.concurrency.atomic;

import com.lyl.concurrency.annoations.NotThreadSafe;
import com.lyl.concurrency.thread.threadPool.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 先演示一遍不使用Atomic的效果
 *
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/10
 */
@Slf4j
@NotThreadSafe
public class ErrorTest {
    private static int count = 0;
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
            count++;
        }
        countDownLatch.countDown();
    }
}
