package com.dlut.daili.shouxiedongtaidaili;

/**
 * 手写动态代理步骤分析
 * 一.SelfProxy类，定义newInstance()方法，进行代理对象创建
 * 1.在该方法中首先需要输出代理类的.java文件
 * 2.编译 .java文件->.class文件
 * 3.通过类加载器加载 .class文件
 * 4.生成该 .class文件的实例对象作为代理对象返回
 * 二.
 * 三.SelfClassloader，自定义类加载器，继承JDK的ClassLoader类，实现自定义的加载方式
 * 四.SelfJDKProxy，实现SelfInvocationHandler接口，重写invoke()方法，实现最终方法运行
 */
public class main
{
}
