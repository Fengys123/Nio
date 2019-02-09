package com.dlut.daili.shouxiedongtaidaili;

public class SelfJDKProxyTarget implements SelfProxyInterface
{
    @Override
    public void targetMethod()
    {
        System.out.println("代理方法...");
    }
}
