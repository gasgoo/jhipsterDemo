package com.demo.service.util;

import com.demo.JhipsterDemoApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * redis 测试
 * @Date 2018/12/24 19:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class RedisComponentTest {

    @Autowired
    private RedisComponent redisComponent;

    @Autowired
    private RedisKeyGenerate redisKeyGenerate;
    @Test
    public void set() {
        String key="jhipsterDemotest";
        String value="jhipsterDemotest";
        if(!redisComponent.exists(key)){
            boolean res = redisComponent.set(key, value);
            System.out.println("缓存结果:"+res);
        }else{
            System.out.println("value:"+redisComponent.get(key));
        }

    }

    @Test
    public void getId(){
        String key="test99999";
        String res=redisKeyGenerate.getID(key,1L);
        //181225195714902746
        //181225200339189538
        redisComponent.del(key);
        System.out.println("==========="+res);
        System.out.println("==========="+res.length());

    }

    @Test
    public void testThread() throws InterruptedException {
        int cpus=Runtime.getRuntime().availableProcessors();
        System.out.println("可用cpu数:"+cpus);
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(cpus);
         Set seqSet = new ConcurrentSkipListSet<String>();
        ExecutorService executorService = Executors.newFixedThreadPool(cpus);
        for(int i=0;i<cpus;i++){
            executorService.execute(new Worker(seqSet,begin,end));
        }
        begin.countDown();
        end.await();
        System.out.println("====over");

    }

    @Test
    public void testID2(){
        System.out.println("-------"+redisKeyGenerate.getUUId());
        System.out.println("-------"+redisKeyGenerate.getUUId().length());
    }

    public class Worker implements  Runnable{

        private final String key="testThreadDemo";
        private final CountDownLatch begin;
        private final CountDownLatch end;
        private final Set seqSet;
        public Worker(  Set seqSet, CountDownLatch begin, CountDownLatch end) {
            this.seqSet = seqSet;
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                begin.await();
                int count=0;
                Long start=System.currentTimeMillis();
                for (int i = 0; i < 10000; i++) {
                    String value = redisKeyGenerate.getID(key, 1L);
                    count++;
                    if (!seqSet.add(value)) {
                        System.out.println(value);
                    }

                }
                long end=System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName()+"总耗时:"+(end-start)+"毫秒 总个数:"+seqSet.size());
                System.out.println("count:"+count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                end.countDown();

            }
        }
    }

}
