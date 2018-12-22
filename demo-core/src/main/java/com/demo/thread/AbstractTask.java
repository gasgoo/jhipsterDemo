package com.demo.thread;

import java.util.Random;

import static java.lang.System.currentTimeMillis;

/**
 * @Date 2018/12/22 17:44
 */
public class AbstractTask {


    private static Random random = new Random();

    public void doTaskOne() throws InterruptedException {
        System.out.println("start task1===");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("end task1===耗时:" + (end - start) + "毫秒");

    }

    public void doTaskTwo() throws InterruptedException {
        System.out.println("start task2===");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("end task2===耗时:" + (end - start) + "毫秒");

    }

    public void doTaskThree() throws InterruptedException {
        System.out.println("start task3===");
        long start = currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("end task3===耗时:" + (end - start) + "毫秒");

    }


}
