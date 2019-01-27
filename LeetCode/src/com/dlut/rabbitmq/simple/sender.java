package com.dlut.rabbitmq.simple;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.IOException;

public class sender
{
    final static String queuename = "test_simple_queue";

    public static void main(String[] args) throws IOException
    {
        //获取一个链接
        Connection connection = ConnectionUtil.getConnection();

        //从链接中获取一个通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(queuename,false,false,false,null);

        String msg = "Hello simple!!!";

        channel.basicPublish("",queuename,null,msg.getBytes());

        System.out.println("-sngMsg:" + msg);

        channel.close();
        connection.close();
    }
}
