package com.dlut.rabbitmq.routing;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class sender
{
    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException
    {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机???
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        //发送消息
        String msg = "hello direct!!!";

        String routingkey = "info";

        //选择这种redirect模式,则routingkey不能为空
        channel.basicPublish(EXCHANGE_NAME,routingkey,null,msg.getBytes());

        System.out.println("[sned] " + msg);

        channel.close();

        connection.close();
    }
}
