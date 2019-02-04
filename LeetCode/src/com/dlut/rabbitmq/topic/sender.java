package com.dlut.rabbitmq.topic;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class sender
{
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException
    {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        //发送消息
        String msg = "商品的数据!!!";

        channel.basicPublish(EXCHANGE_NAME,"goods.delete",null,msg.getBytes());

        System.out.println("[sned] " + msg);

        channel.close();

        connection.close();
    }
}
