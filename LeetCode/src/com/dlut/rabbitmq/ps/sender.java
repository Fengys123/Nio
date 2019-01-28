package com.dlut.rabbitmq.ps;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class sender
{
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException
    {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机???
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //发送消息
        String msg = "hello ps!!!";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println("[sned] " + msg);

        channel.close();

        connection.close();
    }
}
