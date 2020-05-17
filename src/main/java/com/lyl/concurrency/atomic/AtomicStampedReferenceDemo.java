package com.lyl.concurrency.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/17
 */
@Slf4j
public class AtomicStampedReferenceDemo {

//    // 参数依次为：期望值 写入新值 期望版本号 新版本号
//    public boolean compareAndSet(V expectedReference, V
//            newReference, int expectedStamp, int newStamp);
//
//    //获得当前对象引用
//    public V getReference();
//
//    //获得当前版本号
//    public int getStamp();
//
//    //设置当前对象引用和版本号
//    public void set(V newReference, int newStamp);

    private static AtomicStampedReference<Integer> count = new AtomicStampedReference<>(10, 0);

    public static void main(String[] args) {
        Thread main = new Thread(() -> {
            int stamp = count.getStamp(); //获取当前版本

            log.info("线程{} 当前版本{}",Thread.currentThread(),stamp);
            try {
                Thread.sleep(1000); //等待1秒 ，以便让干扰线程执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean isCASSuccess = count.compareAndSet(10, 12, stamp, stamp + 1);  //此时expectedReference未发生改变，但是stamp已经被修改了,所以CAS失败
            log.info("CAS是否成功={}",isCASSuccess);
        }, "主操作线程");

        Thread other = new Thread(() -> {
            int stamp = count.getStamp(); //获取当前版本
            log.info("线程{} 当前版本{}",Thread.currentThread(),stamp);
            count.compareAndSet(10, 12, stamp, stamp + 1);
            log.info("线程{} 增加后版本{}",Thread.currentThread(),count.getStamp());

            // 模拟ABA问题 先更新成12 又更新回10
            int stamp1 = count.getStamp(); //获取当前版本
            count.compareAndSet(12, 10, stamp1, stamp1 + 1);
            log.info("线程{} 减少后版本{}",Thread.currentThread(),count.getStamp());
        }, "干扰线程");

        main.start();
        other.start();
    }
}
