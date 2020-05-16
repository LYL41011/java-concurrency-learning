package com.lyl.concurrency.lock;

import com.lyl.concurrency.thread.threadPool.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁 适合于读多写少
 * 可以同时
 *
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/13
 */
@Slf4j
public class ReadWriteLockDemo1 {

    Object data;

    volatile boolean cacheValid;

    final ReadWriteLock rwl =

            new ReentrantReadWriteLock();

    // 读锁

    final Lock r = rwl.readLock();

    // 写锁

    final Lock w = rwl.writeLock();


    void processCachedData() {
        // 获取读锁
        r.lock();
        if (!cacheValid) {
            // 释放读锁 因为不允许读锁的升级 可以注释掉该行代码 整个程序会阻塞
            r.unlock();
            // 获取写锁
            w.lock();
            try {
                // 再次检查状态
                if (!cacheValid) {
                    data = "胖滚猪学编程";
                    cacheValid = true;
                }

                // 释放写锁前 降级为读锁 降级是可以的
                r.lock();
            } finally {
                // 释放写锁
                w.unlock();

            }

        }
        // 此处仍然持有读锁
        try {
            System.out.println(data);
        } finally {
            r.unlock();
        }

    }

}
