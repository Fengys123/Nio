package com.dlut.daili.dongtaidaili;

/**
 * 静态代理
 */
public class main
{
    public static void main(String[] args)
    {
        person stu = new student();
        person proxystu = new proxystudent(stu);
        proxystu.giveMoney();
    }
}
