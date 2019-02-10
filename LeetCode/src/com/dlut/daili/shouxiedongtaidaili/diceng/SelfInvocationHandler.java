package com.dlut.daili.shouxiedongtaidaili.diceng;

import java.lang.reflect.Method;

/**
 * 定义一个接口
 */
public interface SelfInvocationHandler
{
    Object invoke(Object proxy, Method method, Object[] args);
}
