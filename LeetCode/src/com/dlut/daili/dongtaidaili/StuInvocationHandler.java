package com.dlut.daili.dongtaidaili;

import com.dlut.daili.util.MonitorUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StuInvocationHandler implements InvocationHandler
{
    person student;

    public StuInvocationHandler(person student)
    {
        this.student = student;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        System.out.println("代理执行" + method.getName() + "方法");
        MonitorUtil.start();
        Object result = method.invoke(student, args);
        MonitorUtil.finish(method.getName());
        return result;
    }
}
