package com.lyl.concurrency.container.unsafe;

import com.lyl.concurrency.thread.threadPool.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/31
 */
@Slf4j
public class ArrayListTest {
    public static CountDownLatch countDownLatch = new CountDownLatch(200);
    public static List<String> list = new ArrayList();

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = ThreadPoolUtils.getThreadPool();
        for (int i = 0; i < 200; i++) {
            executor.execute(() -> {
                try {
                    add();
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        countDownLatch.await();
        // 用ArrayList有可能出现小于200的情况
        System.out.println("list.size()=" + list.size());
        executor.shutdown();
    }

    public static void add() {
        list.add(UUID.randomUUID().toString());
    }
}
