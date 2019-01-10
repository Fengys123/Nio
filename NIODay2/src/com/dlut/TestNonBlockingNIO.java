package com.dlut;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestNonBlockingNIO
{
    @Test
    public void client() throws IOException
    {
        //1.获取通道
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2.切换成非阻塞模式
        sc.configureBlocking(false);

        //3.分配制定大小的缓存区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4.发送数据到服务端
        buf.put(new Date().toString().getBytes());
        buf.flip();
        sc.write(buf);
        buf.clear();

        /*//4. 发送数据给服务端
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            int str = scan.nextInt();
            buf.put((new Date().toString() + "\n" + str).getBytes());
            buf.flip();
            sc.write(buf);
            buf.clear();
        }*/
        //5.关闭通道
        sc.close();
    }

    @Test
    public void server() throws IOException
    {
        //1.获取通道
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //2.切换为非阻塞模式
        ssc.configureBlocking(false);

        //3.绑定连接
        ssc.bind(new InetSocketAddress(9898));

        //4.获取选择器
        Selector selector = Selector.open();

        System.out.println("1");

        //5.将通道绑定到选择器  并且指定接听事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //6.轮巡式的获取选择器中所有注册的"选择键"(已就绪的监听事件)


        while(selector.select() > 0)
        {
            System.out.println("123" + selector.select());
            //7.获取全部的选择键   ?????????iterator能遍历什么
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while(iterator.hasNext())
            {
                SelectionKey sk = iterator.next();

                //9.判断具体是什么事件准备就绪
                if(sk.isAcceptable())
                {
                    //10.若是可接受的  则获取客户端连接
                    SocketChannel sc = ssc.accept();

                    //11.切换成非阻塞模式
                    sc.configureBlocking(false);

                    //12.将该通道注册到选择上
                    sc.register(selector,SelectionKey.OP_READ);
                }
                else if(sk.isReadable())
                {
                    //13.如果是 可读状态
                    SocketChannel sc = (SocketChannel) sk.channel();

                    //14.读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int len = 0;
                    while((len = sc.read(buf)) != -1)
                    {
                        //????flip以及clear是怎么运行的(最好看一下源码)
                        buf.flip();
                        //?????????如果不为整数 是否为乱码
                        System.out.println(new String(buf.array(),0,len));
                        buf.clear();
                    }

                }
            }

            //15.取消选择键
            iterator.remove();
        }
    }


    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        System.out.println(i);
    }

    @Test
    public void test()
    {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        System.out.println(i);
    }

}
