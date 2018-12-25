package com.demo.service.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @redis 生成全局唯一主键
 * @Date 2018/12/25 19:48
 */
@Component
public class RedisKeyGenerate {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    //线程安全格式类
    private static final FastDateFormat seqDateFormat = FastDateFormat.getInstance("yyMMddHHmmssSSS");

    private String generateSeq() {
        String seqDate = seqDateFormat.format(System.currentTimeMillis());
        String candidateSeq = new StringBuilder(17).append(seqDate).append(RandomStringUtils.randomNumeric(2)).toString();
        return candidateSeq;
    }


    /**
     * @return java.lang.Long
     * @Description 生成全局唯一ID
     * @Date 2018/12/25 12:02
     * @Param [key, hashKey, delta]  delta 增加量（不传采用1）
     **/
    public String getID(String key, Long delta) {
        try {
            if (delta == null) {
                delta = 1L;
            }
            String value = generateSeq();
            return value + redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            //redisd宕机后生成
            return getUUId();
        }
    }

    public String getUUId() {
        return System.currentTimeMillis()*10000+new Random().nextInt(100)+"";
    }


    public class Worker implements  Runnable{

        @Autowired
        private RedisKeyGenerate redisKeyGenerate;

        private final String key="testThreadDemo";
        private final CountDownLatch begin;
        private final List seqSet;
        public Worker(  List seqSet, CountDownLatch begin) {
            this.seqSet = seqSet;
            this.begin = begin;
        }

        @Override
        public void run() {
            try {
                begin.await();
                for (int i = 0; i < 10000; i++) {
                    String value = redisKeyGenerate.getID(key, 1L);
                    if (!seqSet.add(value)) {
                        System.out.println(value);
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                begin.countDown();
            }
        }
    }
}
