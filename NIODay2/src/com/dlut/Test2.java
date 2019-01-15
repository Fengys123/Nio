package com.dlut;

public class Test2 extends Test1
{
    int i = 9;

    @Override
    public void t()
    {
        //System.out.println(i);
        i = 191;
    }


    public static void main(String[] args)
    {
        //1.编译时类型和运行时类型 的理解
        Test1 t1 = new Test2();
        System.out.println(t1.i);
        t1.t();
        System.out.println(t1.i);

        Test2 t2 = new Test2();
        System.out.println(t2.i);
        t2.t();
        System.out.println(t2.i);
    }
}
