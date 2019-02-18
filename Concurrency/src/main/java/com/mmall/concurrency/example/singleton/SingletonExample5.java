package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.ThreadSafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 懒汉模式 -》 双重同步锁单例模式
 * 单例实例在第一次使用时进行创建
 */
@ThreadSafe
public class SingletonExample5 {

    // 私有构造函数
    private SingletonExample5() {

    }

    // 1、memory = allocate() 分配对象的内存空间
    // 2、ctorInstance() 初始化对象
    // 3、instance = memory 设置instance指向刚分配的内存

    // 单例对象 volatile + 双重检测机制 -> 禁止指令重排
    //volatitle 可见性  修饰的变量修改之后会被及时刷回主存中
    private volatile static SingletonExample5 instance = null;

    // 静态的工厂方法
    public static SingletonExample5 getInstance() {
        if (instance == null) { // 双重检测机制        // B
            synchronized (SingletonExample5.class) { // 同步锁
                if (instance == null) {
                    instance = new SingletonExample5(); // A - 3
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        //private constructor not safe
        SingletonExample5 singleton1 = SingletonExample5.getInstance();
        SingletonExample5 singleton2 = SingletonExample5.getInstance();
        //return true
        System.out.println(singleton1 == singleton2);


        Constructor<SingletonExample5> constructor = SingletonExample5.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingletonExample5 singletonReflect = constructor.newInstance();
        //return false
        //所以私有构造函数并不安全,可以通过反射进行对构造函数的访问
        System.out.println(singleton1 == singletonReflect);

    }
}
