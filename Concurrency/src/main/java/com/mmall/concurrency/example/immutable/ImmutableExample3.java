package com.mmall.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

@ThreadSafe
@Slf4j
public class ImmutableExample3 {

    /**
     * 该测试方法是实现参数过多怎么办    后面的s4当做了数组处理
     * 该测试方法与此案例无关
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @return
     */
    public static String test(String s1,String s2,String s3,String... s4)
    {
        String info = "";
        info = s1 + s2 + s3;
        for(String s: s4)
        {
            info += s;
        }
        log.info("{}",info);
        return info;
    }

    private final static ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer, Integer> map = ImmutableMap.of(1, 2, 3, 4);

    private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder()
            .put(1, 2).put(3, 4).put(5, 6).build();


    public static void main(String[] args) {
        try{
            list.add(1);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(map2.get(3));
        test("a","b","c","d","e","f");
    }
}
