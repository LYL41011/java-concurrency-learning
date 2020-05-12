package com.lyl.concurrency.happensbefore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 八、对象规则
 * 一个对象的初始化完成（构造函数执行结束）happen—before它的finalize（）方法的开始。对象调用finalize()方法时，对象初始化完成的任意操作，必然会同步到主存
 * finalize()是在java.lang.Object里定义的，即每一个对象都有这么个方法。这个方法在该对象被回收的时候被调用
 * 该条原则强调的是多线程情况下对象初始化的结果必须对发生于其后的对象销毁方法可见。
 */
public class HappensBefore8 {
    public HappensBefore8(){
        System.out.println("构造方法");
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("对象销毁");
    }

    public static void main(String[] args){
        new HappensBefore8();
        System.gc();
    }
}

