package com.dlut;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * 一、使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *
 * 	   java.nio.channels.Channel 接口：
 * 			|--SelectableChannel
 * 				|--SocketChannel
 * 				|--ServerSocketChannel
 * 				|--DatagramChannel
 *
 * 				|--Pipe.SinkChannel
 * 				|--Pipe.SourceChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 *
 */


/**
 * 总结一下  channel总共有四种类型
 * 一类:FileChannel
 * 二类:网络(SocketChannel,)
 */

public class nioBlocking
{
    @Test
    public void client() throws IOException
    {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));

        FileChannel fc = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (fc.read(buf) != -1)
        {
            buf.flip();
            sc.write(buf);
            buf.clear();
        }

        /**
         * 不惜要告诉服务端我输入完毕了,这样服务端才能停止运行
         */
        sc.shutdownOutput();

        int len;
        while((len = sc.read(buf)) != -1)
        {
            buf.flip();
            System.out.println(new String(buf.array() , 0, len));
            buf.clear();
        }

        fc.close();
        sc.close();
    }

    @Test
    public void server() throws IOException
    {
        //1.获取通道
        ServerSocketChannel ssc = ServerSocketChannel.open();

        FileChannel fc = FileChannel.open(Paths.get("2.jpg"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        //2.绑定连接
        ssc.bind(new InetSocketAddress(9898));

        //3.获取客户端连接的通道
        SocketChannel sc = ssc.accept();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while(sc.read(buf) != -1)
        {
            /**
             * 这里的flip和clear的源码得看是 (明天看)
             */
            buf.flip();
            fc.write(buf);
            buf.clear();
        }

        buf.put("服务端接受数据成功!!".getBytes());
        buf.flip();
        sc.write(buf);

        sc.close();
        fc.close();
        ssc.close();

    }
}
