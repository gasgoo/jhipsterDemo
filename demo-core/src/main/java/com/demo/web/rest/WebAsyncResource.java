package com.demo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.demo.thread.AsyncCallBackTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Date 2018/12/24 14:04
 */
@RestController
@RequestMapping("/api")
public class WebAsyncResource {

    @Autowired
    private AsyncCallBackTask asyncCallBackTask;


    @GetMapping("/completion")
    @Timed
    public WebAsyncTask<String> taskCompletion(){
        WebAsyncTask<String> asyncTask=null;
        try {
            System.out.println("请求处理线程:" + Thread.currentThread().getName());

            //开启一个异步任务
             asyncTask = new WebAsyncTask<>(10000L, () -> {
                System.out.println("异步任务线程：" + Thread.currentThread().getName());
                Thread.sleep(5000L);
                String id = asyncCallBackTask.generId();
                 System.out.println("异步任务的结果:"+id);
                return id;
            });
            //任务执行完成调用
            asyncTask.onCompletion(() -> System.out.println("任务执行完成"));
            System.out.println("继续处理其他事情----------");
            return asyncTask;
        }catch (Exception e){
            System.out.println("线程异常:"+e);
        }
        return asyncTask;
    }


}
