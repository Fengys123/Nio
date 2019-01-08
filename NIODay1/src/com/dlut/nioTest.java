package com.dlut;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.Set;

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
    public void testBuffer()
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
    public void testDirectBuffer()
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


    /**
     * 一:java支持通道的类提供了getChannel方法
     * @throws Exception
     */
    @Test
    public void testChannel_one() throws Exception
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
     * 二:jdk1.7后,FileChannel提供了静态方法open
     * @throws Exception
     */
    @Test
    public void testChannel_two_notRedirect() throws Exception
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
    public void testChannel_two_redirect() throws Exception
    {
        FileChannel fisChannel = FileChannel.open(Paths.get("Nio.png"), StandardOpenOption.READ);
        FileChannel fosChannel = FileChannel.open(Paths.get("Nio2.png"), StandardOpenOption.WRITE , StandardOpenOption.READ, StandardOpenOption.CREATE);

        //????????????/为什么内存映射不用allocateRedirect
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

    /**
     * 通道之间的数据传输
     * @throws Exception
     */
    @Test
    public void testChannelToChannel() throws Exception
    {
        FileChannel fisChannel = FileChannel.open(Paths.get("Nio.png"), StandardOpenOption.READ);
        FileChannel fosChannel = FileChannel.open(Paths.get("Nio4.png"), StandardOpenOption.WRITE , StandardOpenOption.READ, StandardOpenOption.CREATE);

        //直接缓存区
        //fisChannel.transferTo(0,fisChannel.size(),fosChannel);
        fosChannel.transferFrom(fisChannel,0,fisChannel.size());

        fisChannel.close();
        fosChannel.close();
    }

    /**
     * 分散与聚集
     * 分散读取:
     * 聚集写入:
     * 多个buffer
     * ??????????有毛的用
     */
    public void testBuffersChannel() throws IOException
    {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt","rw");

        //1.获取通道
        FileChannel channel1 = raf1.getChannel();

        //2.分配指定大小的缓存区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(100);

        //3.分散读取
        ByteBuffer[] bufs = {buf1 , buf2};
        channel1.read(bufs);

        for(ByteBuffer byteBuffer : bufs)
        {
            byteBuffer.flip();
        }

        //System.out.println();

        RandomAccessFile raf2 = new RandomAccessFile("2.txt","rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
    }

    /**
     * 字符集Charset
     * 编码:字符串转化为字节数组
     * 解码:字节数组转化为字符串
     */
    @Test
    public void testCharsetShow()
    {
        /**
         * Map的数据结构不是很清楚
         */
        Map<String,Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();

        for(Map.Entry<String,Charset> entry : set)
        {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Test
    public void testCharset() throws CharacterCodingException
    {
        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("大工威武!!!");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);

        //查看字节
        for(int i = 0 ; i < 11 ; i++)
        {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf1 = cd.decode(bBuf);
        System.out.println(cBuf1.length());

        cBuf1.rewind();
        int length = cBuf1.length();
        for(int i = 0 ; i < length ; i++)
        {
            System.out.println(cBuf1.get());
        }

        System.out.println(cBuf1.length());

        //System.out.println(cBuf1.toString());
    }

}
