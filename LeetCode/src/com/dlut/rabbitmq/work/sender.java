package com.dlut.rabbitmq.work;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class sender
{
    final static String queuename = "test_simple_queue";

    public static void main(String[] args) throws IOException, InterruptedException
    {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queuename,false,false,false,null);

        for(int i=0 ; i<50 ; i++)
        {
            String msg = "hello" + i;
            channel.basicPublish("",queuename,null,msg.getBytes());
            System.out.println("[WQ] send " + msg);
            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();
    }
}
