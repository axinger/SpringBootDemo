package com.axing.future;

import com.axing.future.model.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class Demo6ApplicationTest {

    @Autowired
    private Executor executor;


    @Test
    void test1() {
        /**
         * 用对象属性赋值,不用AtomicReference 原子性
         */
        PersonEntity entity = new PersonEntity();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();


        List<CompletableFuture> resulFutureList = new ArrayList<>();
        resulFutureList.add(CompletableFuture.runAsync(() -> {
            log.info("任务4: entity = {}", entity);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            entity.setSex("男");
        }, executor));


        List<CompletableFuture> futureList = new ArrayList<>();

        // 任务1,任务2完成,再执行任务3
        log.info("任务1,任务2完成,再执行任务3================");
        futureList.add(CompletableFuture.runAsync(() -> {
            log.info("任务1: entity = {}", entity);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            entity.setName("jim");
        }, executor));


        futureList.add(CompletableFuture.runAsync(() -> {
            log.info("任务2: entity = {}", entity);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            entity.setAge(10);

        }, executor));

        CompletableFuture<Void> future1 = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));

        CompletableFuture<Void> future2 = future1.whenComplete((val, err) -> {
            log.info("任务3: entity = {}", entity);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            entity.setId(entity.getName() + "-" + entity.getAge());
            log.info("任务3:完成: entity = {}", entity);
        });

        resulFutureList.add(future2);

        future2.whenComplete((val, err) -> {
            log.info("whenComplete = {}", val);
        });


        CompletableFuture<Void> resulFuture = CompletableFuture.allOf(resulFutureList.toArray(new CompletableFuture[resulFutureList.size()]));

        resulFuture.join();
        stopWatch.stop();
        log.info("结果: 耗时 = {},entity = {}", stopWatch.getTotalTimeSeconds(), entity);


    }

    @Test
    void test2() {
        /**
         * 用对象属性赋值,不用AtomicReference 原子性
         */
        PersonEntity entity = new PersonEntity();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 任务1,任务2完成,再执行任务3
        log.info("任务1,任务2完成,再执行任务3================");
        CompletableFuture<PersonEntity> future = CompletableFuture.runAsync(() -> {


            log.info("任务1: entity = {}", entity);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            entity.setName("jim");


        }, executor).thenCombine(

                CompletableFuture.runAsync(() -> {
                    log.info("任务2: entity = {}", entity);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    entity.setAge(10);

                }, executor)

                , (a, b) -> {
                    log.info("任务3: entity = {}", entity);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    entity.setId(entity.getName() + "-" + entity.getAge());
                    log.info("任务3:完成: entity = {}", entity);
                    return entity;
                });

        future.whenComplete((val, err) -> {
            log.info("whenComplete = {}", val);
        });

        PersonEntity entity1 = future.join();
        stopWatch.stop();
        log.info("结果: 耗时 = {},线程 = {},entity = {},entity1 = {}", stopWatch.getTotalTimeSeconds(), Thread.currentThread().getName(), entity, entity1);


    }

    @Test
    void test3() {
        List<CompletableFuture> futures = new ArrayList<>(3);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("A==========================超时了,最后还会走,但不执行返回数据");
                    return "A";
                }, executor)
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally((e) -> {
                    System.out.println("e = " + e);
                    return "-A";
                });
        futures.add(future);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("B===================");
            return "B";
        }, executor);
        futures.add(future1);


        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("C===================");
            if (true) {
                throw new RuntimeException("-C");
            }
            return "-C";
        }, executor).exceptionally((e) -> {
            System.out.println("e = " + e);
            return "-C";
        });

        futures.add(future2);

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .exceptionally(e -> {
                    System.out.println("allOf e = " + e);
                    return null;
                }).join();
        stopWatch.stop();
        System.out.println("future.join() = " + future.join());
        System.out.println("future1.join() = " + future1.join());
        System.out.println("future2.join() = " + future2.join());
        System.out.println("stopWatch.getTotalTimeSeconds() = " + stopWatch.getTotalTimeSeconds());

        System.out.println("结束=========================================");
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
