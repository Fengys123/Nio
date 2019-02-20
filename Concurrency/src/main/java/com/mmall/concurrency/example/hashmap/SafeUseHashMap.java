package com.mmall.concurrency.example.hashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 三种方式:
 */
public class SafeUseHashMap
{
    public final static int THREAD_POOL_SIZE = 5;
    public static Map<String, Integer> hashtable = null;
    public static Map<String, Integer> synhashmap = null;
    public static Map<String, Integer> concurrenthashmap = null;

    public static void main(String[] args) throws InterruptedException
    {
        hashtable = new Hashtable<>();
        crunchifyPerformTest(hashtable);
        synhashmap = Collections.synchronizedMap(new HashMap<>());
        crunchifyPerformTest(synhashmap);
        concurrenthashmap = new ConcurrentHashMap<>();
        crunchifyPerformTest(concurrenthashmap);
    }

    public static void crunchifyPerformTest(final Map<String, Integer> map) throws InterruptedException
    {
        System.out.println("Test started for: " + map.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++)
        {
            long startTime = System.nanoTime();
            ExecutorService exServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
            for (int j = 0; j < THREAD_POOL_SIZE; j++)
            {
                exServer.execute(new Runnable()
                {
                    @SuppressWarnings("unused")
                    @Override
                    public void run()
                    {
                        for (int i = 0; i < 500000; i++)
                        {
                            Integer crunchifyRandomNumber = (int) Math.ceil(Math.random() * 550000);
                            // Retrieve value. We are not using it anywhere
                            Integer crunchifyValue = map.get(String.valueOf(crunchifyRandomNumber));
                            // Put value
                            map.put(String.valueOf(crunchifyRandomNumber), crunchifyRandomNumber);
                        }
                    }
                });
            }
            // Make sure executor stops
            exServer.shutdown();
            // Blocks until all tasks have completed execution after a shutdown request
            exServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("2500K entried added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + map.getClass() + " the average time is " + averageTime / 5 + " ms\n");
    }
}

