package com.lyl.concurrency.lock;


import java.util.concurrent.locks.StampedLock;

/**
 * @Author 公众号【胖滚猪学编程】
 * @Date 2020/5/12 10:17
 * @Descripiton
 */
public class StampedLockDemo2 {

    private double x, y;
    // 锁实例
    private final StampedLock sl = new StampedLock();


    // 排它锁-写锁（writeLock）
    void move(double deltaX, double deltaY) { // an exclusively locked method
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // 乐观读
    double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();//(1)
        double currentX = x, currentY = y;

        // 检查在(1)获取到读锁票据后，锁有没被其他写线程排它性抢占
        if (!sl.validate(stamp)) {
            // 如果被抢占则获取一个共享读锁（悲观读锁）
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX*currentX + currentY*currentY);
    }

    // 读锁升级成写锁
    void moveIfAtOrigin(double newX, double newY) { // upgrade
        // Could instead start with optimistic, not read mode
        long stamp = sl.readLock();
        try {
            while (x == 0.0 && y == 0.0) {
                // 尝试将获取的读锁升级为写锁
                long ws = sl.tryConvertToWriteLock(stamp);
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 读锁升级写锁失败则释放读锁，显示获取独占写锁，然后循环重试
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }
}



