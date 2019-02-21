package com.mmall.concurrency.example.singleton;

import java.io.*;

/**
 *  public final class  EnumSingleton extends Enum< EnumSingleton> {
 *         public static final  EnumSingleton  Instance;
 *         public static  EnumSingleton[] values();
 *         public static  EnumSingleton valueOf(String s);
 *         static {};
 *  }
 */
public enum SingletonExample8
{
    /**
     *
     */
    Instance;

    /**
     * 使用enum可以避免序列化产生新的对象
     * 原理分析:
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        SingletonExample8 s = SingletonExample8.Instance;
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SerSingleton.obj"));
        oos.writeObject(s);
        oos.flush();
        oos.close();
        FileInputStream fis = new FileInputStream("SerSingleton.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        SingletonExample8 s1 = (SingletonExample8)ois.readObject();
        ois.close();
        System.out.println(s+"\n"+s1);
        System.out.println("序列化前后两个是否同一个："+(s==s1));
    }
}
