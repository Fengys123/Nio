package com.dlut.daili.dongtaidaili;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class main
{
    public static void main(String[] args) throws IOException
    {
        //创建一个实例对象，这个对象是被代理的对象
        person zhangsan = new student("张三");

        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new StuInvocationHandler(zhangsan);

        /**
         * 代码分析
         * 一.Proxy.newProxyInstance(person.class.getClassLoader(), new Class<?>[]{person.class}, stuHandler);
         * 1.第一个参数为目标类的类加载器
         * 2.第二个参数为目标类的接口集合
         * 3.第三个参数为invocationHandle的实现类
         *
         * 二.InvocationHandler接口实现类
         * public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
         * 1.第一个参数为生成的动态代理对象
         * 2.第二个参数为动态代理在客户端执行的方法
         * 3.第三个参数为该方法的参数列表
         * 4.通过反射来完成方法调用
         *
         * 三.通过第一步,jdk会虚拟生成的代理类
         *      public final void givenMoney() throws  {
         *         try {
         *             super.h.invoke(this, m3, (Object[])null);
         *         } catch (RuntimeException | Error var2) {
         *             throw var2;
         *         } catch (Throwable var3) {
         *             throw new UndeclaredThrowableException(var3);
         *         }
         *     }
         *
         *     static {
         *         try {
         *             m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
         *             m2 = Class.forName("java.lang.Object").getMethod("toString");
         *             m3 = Class.forName("com.dlut.daili.dongtaidaili.person").getMethod("givenMoney");
         *             m0 = Class.forName("java.lang.Object").getMethod("hashCode");
         *         } catch (NoSuchMethodException var2) {
         *             throw new NoSuchMethodError(var2.getMessage());
         *         } catch (ClassNotFoundException var3) {
         *             throw new NoClassDefFoundError(var3.getMessage());
         *         }
         *     }
         *
         *     不管代理类调用什么方法都会执行invoke方法
         */
        //创建一个代理对象stuProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        person stuProxy = (person) Proxy.newProxyInstance(person.class.getClassLoader(), new Class<?>[]{person.class}, stuHandler);

        //代理执行上交班费的方法
        stuProxy.givenMoney();

        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{person.class});
        FileOutputStream outputStream = new FileOutputStream("D:\\$Proxy0.class");
        outputStream.write(bytes);
        outputStream.close();
    }
}
