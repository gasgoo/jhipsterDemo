package com.demo.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @异步调用
 * @Date 2018/12/22 18:13
 */
@Service
public class AsyncTask extends AbstractTask{

    @Override
    @Async
    public void doTaskOne() throws InterruptedException {
        System.out.println("当前线程:"+Thread.currentThread().getName());
        super.doTaskOne();
    }
    @Override
    @Async
    public void doTaskTwo() throws InterruptedException {
        System.out.println("当前线程:"+Thread.currentThread().getName());

        super.doTaskTwo();
    }
    @Override
    @Async
    public void doTaskThree() throws InterruptedException {
        System.out.println("当前线程:"+Thread.currentThread().getName());

        super.doTaskThree();
    }
}
