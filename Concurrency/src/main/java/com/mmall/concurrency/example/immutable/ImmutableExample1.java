package com.mmall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * final修饰的类引用的值可以改变,但指向的位置不可以改变
 * 这样的话 , hashmap存在线程安全问题
 */
@Slf4j
@NotThreadSafe
public class ImmutableExample1 {

    private final static Integer a = 1;
    private final static String b = "2";
    /**
     * 调用了Maps?????????????????? 回头查一下
     */
    private final static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public static void main(String[] args) {
//        a = 2;
//        b = "3";
//        map = Maps.newHashMap();
        map.put(1, 3);
        log.info("{}", map.get(1));
    }

    private void test(final int a) {
//        a = 1;
    }
}
