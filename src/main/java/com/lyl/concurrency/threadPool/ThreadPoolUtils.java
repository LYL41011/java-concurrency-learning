package com.lyl.concurrency.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/10
 */
public class ThreadPoolUtils {
    public static ThreadPoolExecutor getThreadPool(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 50,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        return executor;
    }
}
