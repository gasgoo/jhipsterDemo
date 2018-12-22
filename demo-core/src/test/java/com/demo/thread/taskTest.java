package com.demo.thread;

import com.demo.JhipsterDemoApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * @Date 2018/12/22 17:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class taskTest {

    @Autowired
    private task task;

    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private AsyncCallBackTask asyncCallBackTask;

    @Test
    public void testSyncCall() throws InterruptedException {
        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();
    }
    @Test
    public void testAsyncCall() throws InterruptedException {
        System.out.println("测试异步调用");
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
        System.out.println("三个任务未必都会完成 main结束了其他的线程还没结束");
    }
    @Test
    public void testAsyncCallThree() throws InterruptedException {
        System.out.println("测试异步回调，所有异步任务执行完成。");
        long start=System.currentTimeMillis();
        Future<String> t1 =asyncCallBackTask.doCallTaskOne();
        Future<String> t2 = asyncCallBackTask.doCallTaskTwo();
        Future<String> t3 = asyncCallBackTask.doCallThree();
        while(!t1.isDone()||!t2.isDone()||!t3.isDone()){
            Thread.sleep(1000L);
        }
        long end=System.currentTimeMillis();
        System.out.println("三个任务总耗时:"+(end-start)+"毫秒");

    }

}
