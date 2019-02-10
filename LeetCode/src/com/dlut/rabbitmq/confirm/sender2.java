package com.dlut.rabbitmq.confirm;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 批量模式(串行)
 */
public class sender2
{
    public static final String QUEUE_NAME = "test_queue_confirm2";

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmSelect 将channel设置为confirm模式  如果之前设为了tx模式,则不能设为confirm模式
        channel.confirmSelect();

        String msg = "hello tx message!!!";
        System.out.println("[send] " + msg);

        //批量发送
        for(int i = 0 ; i < 10 ; i++)
        {
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }

        if(!channel.waitForConfirms())
        {
            System.out.println("message send failed!!!");
        }
        else
        {
            System.out.println("message send ok!!!");
        }

        channel.close();
        connection.close();

    }
}
