package com.dlut.rabbitmq.simple;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import sun.nio.cs.ext.MacGreek;

import java.io.IOException;

/**
 * 消费者消费消息
 */
public class recv
{
    final static String queuename = "test_simple_queue";

    public static void main(String[] args) throws IOException, InterruptedException
    {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //老版本的api   定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //监听队列
        channel.basicConsume(queuename,true,queueingConsumer);

        while(true)
        {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            byte[] body = delivery.getBody();
            String strMsg = new String(body);
            System.out.println("[recv] msg:" + strMsg);
        }

    }
}
