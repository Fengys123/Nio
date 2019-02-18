package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.Recommend;
import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * 枚举模式：最安全
 * 原理:emun的构造方法只会被执行一次(jvm保证)
 */
@ThreadSafe
@Recommend
public class SingletonExample7 {

    // 私有构造函数
    private SingletonExample7() {

    }

    public static SingletonExample7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    /**
     * a
     */
    private enum Singleton {
        /**
         *
         */
        INSTANCE;

        /**
         *
         */
        private SingletonExample7 singleton;

        /**
          * JVM保证这个方法绝对只调用一次
          */
        Singleton() {
            singleton = new SingletonExample7();
        }

        /**
         *
         * @return
         */
        public SingletonExample7 getInstance() {
            return singleton;
        }
    }
}
