package com.ax.juc._11utils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * 辅助类
 * 1.减少计数
 * 6个同学,都离开,才关灯
 */
class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println("完成...");
    }

}

/**
 * 2.CyclicBarrier 循环栅栏
 * 集齐7个龙珠,才完成
 */
class CyclicBarrierDemo {


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("栅栏完成...");
        });

        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println("线程名称:: " + Thread.currentThread().getName());
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

    }


}

/**
 * 3. Semaphore 许可 信号量
 * <p>
 * 6个汽车,抢占3个停车位
 */
class SemaphoreDemo {
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);

        // 模拟6辆汽车占车位
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                // 抢占
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢占到车位");

                    // 设置随机停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println(Thread.currentThread().getName() + "离开车位车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }


    }
}
