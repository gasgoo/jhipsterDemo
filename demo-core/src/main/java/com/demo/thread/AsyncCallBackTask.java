package com.demo.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @异步回调
 * @Date 2018/12/22 18:27
 */
@Service
public class AsyncCallBackTask extends AbstractTask{
    @Async
    public Future<String> doCallTaskOne() throws InterruptedException {
        System.out.println("当前线程:"+Thread.currentThread().getName()+"执行tas1");
        super.doTaskOne();
        return new AsyncResult<>("任务一完成！");
    }
    @Async
    public Future<String> doCallTaskTwo() throws InterruptedException {
        System.out.println("当前线程:"+Thread.currentThread().getName()+"执行tas2");
        super.doTaskTwo();
        return new AsyncResult<>("任务二完成！");

    }
    @Async
    public Future<String> doCallThree() throws InterruptedException {
        System.out.println("当前线程:"+Thread.currentThread().getName()+"执行tas3");
        super.doTaskThree();
        return new AsyncResult<>("任务二完成！");
    }

    public String generId(){
        return UUID.randomUUID().toString();
    }

}
