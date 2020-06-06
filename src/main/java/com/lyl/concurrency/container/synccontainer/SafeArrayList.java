package com.lyl.concurrency.container.synccontainer;

import java.util.*;

/**
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/30
 * 我们常用的 ArrayList、HashMap 就不是线程安全的。那么如何将非线程安全的容器变成线程安全的容器呢？
 * 只要把非线程安全的容器封装在对象内部，然后控制好访问路径就可以了。
 */
public class SafeArrayList<T> {
    // 封装 ArrayList
    List<T> c = new ArrayList<>();

    // 控制访问路径
    synchronized T get(int idx) {
        return c.get(idx);
    }

    synchronized void add(int idx, T t) {
        c.add(idx, t);
    }

}
