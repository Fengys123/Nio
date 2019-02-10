package com.dlut.daili.shouxiedongtaidaili;

import com.dlut.daili.shouxiedongtaidaili.diceng.SelfClassLoader;
import com.dlut.daili.shouxiedongtaidaili.diceng.SelfInvocationHandler;
import com.dlut.daili.shouxiedongtaidaili.diceng.SelfProxy;

import java.lang.reflect.Method;

public class SelfJDKProxy implements SelfInvocationHandler
{
    private SelfProxyInterface proxyInterface;

    public SelfJDKProxy(SelfProxyInterface proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public Object instanceProxy() {
        System.out.println("代理方法开始...");
        SelfProxyInterface instance = (SelfProxyInterface) SelfProxy.newInstance(
                new SelfClassLoader(),
                proxyInterface.getClass().getInterfaces(), this);
        return instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
    {
        try
        {
            method.invoke(proxyInterface, args);
            System.out.println("代理方法结束...");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
