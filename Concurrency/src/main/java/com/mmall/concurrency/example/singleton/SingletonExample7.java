package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annoations.Recommend;
import com.mmall.concurrency.annoations.ThreadSafe;

import java.io.*;

/**
 * 枚举模式：最安全
 * 原理:emun的构造方法只会被执行一次(jvm保证)
 */
@ThreadSafe
@Recommend
public class SingletonExample7 implements Serializable{
    private static final long serialVersionUID = -2738081597746734670L;

    // 私有构造函数
    private SingletonExample7() {

    }

    public static SingletonExample7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }


    public void iiii(int i)
    {
        System.out.println("123");
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

        public static void main(String[] args) throws IOException, ClassNotFoundException
        {
            /**
             * 任何一个readObject方法，不管是显式的还是默认的，它都会返回一个新建的实例，这个新建的实例不同于该类初始化时创建的实例。”
             * 当然，这个问题也是可以解决的，想详细了解的同学可以翻看《effective java》第77条：对于实例控制，枚举类型优于readResolve
             */
            SingletonExample7 s = SingletonExample7.getInstance();
            /*ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SerSingleton.obj"));
            oos.writeObject(s);
            oos.flush();
            oos.close();*/
            FileInputStream fis = new FileInputStream("SerSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SingletonExample7 s1 = (SingletonExample7)ois.readObject();
            ois.close();
            System.out.println(s+"\n"+s1);
            System.out.println("序列化前后两个是否同一个："+(s==s1));
        }
    }
}
