package com.dlut.daili.dongtaidaili;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class main
{
    public static void main(String[] args)
    {
        //创建一个实例对象，这个对象是被代理的对象
        person zhangsan = new student("张三");

        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new StuInvocationHandler(zhangsan);

        //创建一个代理对象stuProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        person stuProxy = (person) Proxy.newProxyInstance(person.class.getClassLoader(), new Class<?>[]{person.class}, stuHandler);

        //代理执行上交班费的方法
        stuProxy.givenMoney();
    }
}
