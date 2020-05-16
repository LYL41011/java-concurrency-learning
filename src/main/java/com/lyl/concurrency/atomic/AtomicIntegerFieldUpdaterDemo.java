package com.lyl.concurrency.atomic;

import com.lyl.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
@ThreadSafe
/**
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/10
 */
public class AtomicIntegerFieldUpdaterDemo {

    private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterDemo> updater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterDemo.class, "count");

    @Getter
    public volatile int count = 100;

    public static void main(String[] args) {

        AtomicIntegerFieldUpdaterDemo example5 = new AtomicIntegerFieldUpdaterDemo();

        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 1, {}", example5.getCount());
        }

        if (updater.compareAndSet(example5, 100, 120)) {
            log.info("update success 2, {}", example5.getCount());
        } else {
            log.info("update failed, {}", example5.getCount());
        }
    }
}
