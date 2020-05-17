package com.lyl.concurrency.lock;


import java.util.concurrent.locks.StampedLock;

/**
 * @Author 公众号【胖滚猪学编程】
 * @Date 2020/5/12 10:17
 * @Descripiton
 */
public class StampedLockDemo1 {
    // 锁实例
    private final StampedLock sl = new StampedLock();

    // 排它锁-写锁（writeLock）
    void writeLock() {
        long stamp = sl.writeLock();
        try {
          // 业务逻辑
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // 悲观读锁
    void readLock() {
        long stamp = sl.readLock();
        try {
          // 业务逻辑
        } finally {
            sl.unlockRead(stamp);
        }
    }

    // 乐观读
    void optimisticRead() {
        long stamp = sl.tryOptimisticRead();//(1)
        // 检查在(1)获取到读锁票据后，锁有没被其他写线程排它性抢占
        if (!sl.validate(stamp)) {
            // 如果被抢占则获取一个共享读锁（悲观获取）
            stamp = sl.readLock();
            try {
                // 业务逻辑
            } finally {
                sl.unlockRead(stamp);
            }
        }
    }


}



