package com.lyl.concurrency.happensbefore;

/**
 * 七、线程中断规则
 * 对线程interrupt()方法的调用happen—before于被中断线程的代码检测到中断事件的发生
 * 线程A调用线程B的interrupt()方法，happens-before于线程A发现B被A中断（通过Thread.interrupted()方法检测到是否有中断发生）。
 */
public class HappensBefore7 {
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread B = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread B被中断后count=" + count);
                    return;
                }
            }
        });
        B.start();
        Thread A = new Thread(() -> {
            count = 10;
            B.interrupt();
        });
        A.start();
    }
}

