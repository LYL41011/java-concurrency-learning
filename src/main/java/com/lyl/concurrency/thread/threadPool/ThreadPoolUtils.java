package com.lyl.concurrency.thread.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/10
 */
public class ThreadPoolUtils {
    public static ThreadPoolExecutor getThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5,
                1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5)
                , new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

    public static void main(String[] args) {
        getThreadPool();
    }
}
