package com.lyl.concurrency.atomic;


/**
 * 手写模仿CAS算法
 *
 * @Author: 微信公众号【胖滚猪学编程】
 * @Date 2020/5/16
 */
public class CASTest {

    // 其实是long型的 地址值 只是为了简单说明 采用int
    private static int expectCount;


    // count必须用volatile修饰 保证不同线程之间的可见性
    private volatile static int count;

    public void addOne() {
        int newValue;
        do {
            newValue = count++;
        } while (!compareAndSwapInt(expectCount, newValue)); //自旋 循环
    }

    public final boolean compareAndSwapInt(int expectCount, int newValue) {
        // 读目前 count 的值
        int curValue = count;
        // 比较目前 count 值是否 == 期望值
        if (curValue == expectCount) {
            // 如果是，则更新 count 的值
            count = newValue;
            return true;

        }
        //否则返回false 然后循环
        return false;
    }


}
