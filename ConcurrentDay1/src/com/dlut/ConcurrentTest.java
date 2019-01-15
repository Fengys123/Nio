package com.dlut;

public class ConcurrentTest
{
    /**
     * volatitle 保证了可见性
     * 禁止进行指令重排序
     *
     * volatile关键字禁止指令重排序有两层意思：
     * 1）当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；
     * 2）在进行指令优化时，不能将在对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。
     * 实际上，这些条件表明，可以被写入 volatile 变量的这些有效值独立于任何程序的状态，包括变量的当前状态。
     */
    public volatile int count = 0;

    public synchronized void inc() {


        // 这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        count++;
        System.out.println("cout: " + count);
    }

    public static void main(String[] args) {

        // 同时启动1000个线程，去进行i++计算，看看实际结果
        final ConcurrentTest counter = new ConcurrentTest();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }

        //保证前面的线程都执行完
        while(Thread.activeCount()>2)
        {
            //System.out.println(Thread.activeCount());
            //System.out.println("123");
            //Thread.yield();
        }

        // 这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + counter.count);
    }
}
