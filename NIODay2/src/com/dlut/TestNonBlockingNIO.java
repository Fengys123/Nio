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
        //1.��ȡͨ��
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2.�л��ɷ�����ģʽ
        sc.configureBlocking(false);

        //3.�����ƶ���С�Ļ�����
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4.�������ݵ������
        buf.put(new Date().toString().getBytes());
        buf.flip();
        sc.write(buf);
        buf.clear();

        /*//4. �������ݸ������
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            int str = scan.nextInt();
            buf.put((new Date().toString() + "\n" + str).getBytes());
            buf.flip();
            sc.write(buf);
            buf.clear();
        }*/
        //5.�ر�ͨ��
        sc.close();
    }

    @Test
    public void server() throws IOException
    {
        //1.��ȡͨ��
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //2.�л�Ϊ������ģʽ
        ssc.configureBlocking(false);

        //3.������
        ssc.bind(new InetSocketAddress(9898));

        //4.��ȡѡ����
        Selector selector = Selector.open();

        System.out.println("1");

        //5.��ͨ���󶨵�ѡ����  ����ָ�������¼�
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //6.��Ѳʽ�Ļ�ȡѡ����������ע���"ѡ���"(�Ѿ����ļ����¼�)


        while(selector.select() > 0)
        {
            System.out.println("123" + selector.select());
            //7.��ȡȫ����ѡ���   ?????????iterator�ܱ���ʲô
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while(iterator.hasNext())
            {
                SelectionKey sk = iterator.next();

                //9.�жϾ�����ʲô�¼�׼������
                if(sk.isAcceptable())
                {
                    //10.���ǿɽ��ܵ�  ���ȡ�ͻ�������
                    SocketChannel sc = ssc.accept();

                    //11.�л��ɷ�����ģʽ
                    sc.configureBlocking(false);

                    //12.����ͨ��ע�ᵽѡ����
                    sc.register(selector,SelectionKey.OP_READ);
                }
                else if(sk.isReadable())
                {
                    //13.����� �ɶ�״̬
                    SocketChannel sc = (SocketChannel) sk.channel();

                    //14.��ȡ����
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int len = 0;
                    while((len = sc.read(buf)) != -1)
                    {
                        //????flip�Լ�clear����ô���е�(��ÿ�һ��Դ��)
                        buf.flip();
                        //?????????�����Ϊ���� �Ƿ�Ϊ����
                        System.out.println(new String(buf.array(),0,len));
                        buf.clear();
                    }

                }
            }

            //15.ȡ��ѡ���
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
