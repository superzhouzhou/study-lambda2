package com.example.lambda;

/**
 * Created by Mr.Zhou on 2018/8/25.
 */

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * Date：2017/3/10 17:12
 */
public class TestForkJoin {
    Long num = 10000000000L;
    /**
     * ForkJoin
     */
    @Test
    public void test1(){
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0,num);
        Long sum = pool.invoke(task);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start,end).toMillis());//3904
    }

    /**
     * 普通for循环
     */
    @Test
    public void test2(){
        Instant start = Instant.now();
        Long sum = 0L;
        for (int i = 0; i < num; i++) {
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start,end).toMillis());//因为时间太长。。。。不等了
    }

    /**
     * 并行流
     */
    @Test
    public void test3(){
        Instant start = Instant.now();
        LongStream.rangeClosed(0, num)
                .parallel()
                .reduce(0, Long:: sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start,end).toMillis());//3887
    }

    /**
     * 串行流
     */
    @Test
    public void test4(){
        Instant start = Instant.now();
        LongStream.rangeClosed(0, num)
                .sequential()
                .reduce(0, Long:: sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start,end).toMillis());//7398
    }

    /**
     * ?流
     */
    @Test
    public void test5(){
        Instant start = Instant.now();
        LongStream.rangeClosed(0, num)

                .reduce(0, Long:: sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(start,end).toMillis());//7398
    }
}