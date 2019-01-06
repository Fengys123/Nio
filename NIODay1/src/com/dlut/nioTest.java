package com.dlut;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Administrator on 2019/1/5.
 * 除了,都提供了相应类型的缓冲区
 * 缓存区的管理方式一样,通过allocate()获取缓存区
 * 存储数据的核心方法 put(将数据存入缓存区) get(从缓存区中获取数据)
 * 缓存区的核心属性:
 *      capacity: 缓存区最大存储数据的容量.(一旦声明不能改变,和数组特性相关)
 *      limit: 界限,缓存区可以操作数据的大小(limit后面的数据是不能进行读写的)
 *      position: 表示缓存区中正在操作的位置
 * flip() 读数据模式
 *
 * Mark:记住当前position的位置 ,可以通过reset恢复到Mark的位置
 *
 * 直接缓存区与非直接缓存区
 *
 * 直接:allocateDirect 缓存区在jvm内存中
 * 非直接:allocate  缓存区在物理内存中
 */
public class nioTest
{
    @Test
    public void test1()
    {
        String str = "abcde";

        //1.分配一个制定大小的缓存区
        ByteBuffer buffer =  ByteBuffer.allocate(1024);

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        buffer.put(str.getBytes());

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //切换为读模式
        buffer.flip();

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        String str1 = "qwert";
        buffer.put(str1.getBytes());

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        /**
         * public final Buffer flip() {
         * limit = position;
         * position = 0;
         * mark = -1;
         * return this;
         * }
         */
        buffer.flip();
        String getStr1;
        byte[] getBytes = new byte[buffer.limit()];
        buffer.get(getBytes);
        getStr1 = new String(getBytes,0,buffer.limit());
        System.out.println(getStr1);

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //但是和buffer.flip有什么区别  可重复读取,但是没有改变limit的值
        buffer.rewind();

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //清空缓存区,但是里面的数据还在,但是出于被遗忘状态
        buffer.clear();

        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());


    }

    @Test
    public void test2()
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        if(buffer.isDirect())
        {
            System.out.println("This buffer is direct");
        }
    }

    /**
     * 1.java支持通道的类提供了getChannel()方法
     * 2.jdk1.7针对各个通道提供了静态方法 open()
     * 3.Files工具类提供了newByteChannel()
     *
     * !这里应该写try catch  但是为了省事直接throws
     *
     * 测试channel  第一种方式创建通道
     */
    @Test
    public void test3() throws Exception
    {
        FileInputStream fis = new FileInputStream("Nio.png");
        FileOutputStream fos = new FileOutputStream("Nio1.png");

        //获取通道
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();

        //分配缓存区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //将通道的数据存入缓存区
        while(fisChannel.read(buf) != -1)
        {
            buf.flip();
            fosChannel.write(buf);
            //数据不清空怎么办
            buf.clear();
        }

        fosChannel.close();
        fisChannel.close();
        fis.close();
        fos.close();

    }

    /**
     * 第二种方式创建通道
     */
    @Test
    public void test4() throws Exception
    {
        FileChannel fisChannel = FileChannel.open(Paths.get("Nio.png"), StandardOpenOption.READ);
        FileChannel fosChannel = FileChannel.open(Paths.get("Nio1.png"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        //分配缓存区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //将通道的数据存入缓存区
        while(fisChannel.read(buf) != -1)
        {
            buf.flip();
            fosChannel.write(buf);
            //数据不清空怎么办
            buf.clear();
        }

        fosChannel.close();
        fisChannel.close();
    }

    /**
     * 第二种方法创建通道
     *
     * 直接缓存区只有Bytebuffer支持
     */
    @Test
    public void test5() throws Exception
    {
        FileChannel fisChannel = FileChannel.open(Paths.get("Nio.png"), StandardOpenOption.READ);
        FileChannel fosChannel = FileChannel.open(Paths.get("Nio2.png"), StandardOpenOption.WRITE , StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer fisMapByteBuffer = fisChannel.map(FileChannel.MapMode.READ_ONLY, 0, fisChannel.size());
        //这里用到的是读写模式,所以上面文件必须要有这两个权限
        MappedByteBuffer fosMapByteBuffer = fosChannel.map(FileChannel.MapMode.READ_WRITE, 0, fisChannel.size());

        //直接写入本机缓存区  以及 从缓存区获取数据
        byte[] dst = new byte[fisMapByteBuffer.limit()];
        fisMapByteBuffer.get(dst);
        fosMapByteBuffer.put(dst);

        fosChannel.close();
        fisChannel.close();
    }

    @Test
    public void test6() throws Exception
    {
        FileChannel fisChannel = FileChannel.open(Paths.get("Nio.png"), StandardOpenOption.READ);
        FileChannel fosChannel = FileChannel.open(Paths.get("Nio4.png"), StandardOpenOption.WRITE , StandardOpenOption.READ, StandardOpenOption.CREATE);

        //直接缓存区
        //fisChannel.transferTo(0,fisChannel.size(),fosChannel);
        fosChannel.transferFrom(fisChannel,0,fisChannel.size());

        fisChannel.close();
        fosChannel.close();
    }

}
