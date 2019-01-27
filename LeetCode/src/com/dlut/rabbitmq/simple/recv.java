package com.dlut.rabbitmq.simple;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;
import sun.nio.cs.ext.MacGreek;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 消费者消费消息
 */
public class recv
{
    final static String queuename = "test_simple_queue";

    public static void main(String[] args) throws IOException, InterruptedException
    {
        newApi();
        return;
    }

    private static void newApi() throws IOException
    {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //队列声明
        channel.queueDeclare(queuename,false,false,false,null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String strMsg = new String(body,"utf-8");
                System.out.println("[recv] msg:" + strMsg);
            }
        };

        //监听这个队列(这种写法类似于android的addlistener的方法)
        channel.basicConsume(queuename,true,defaultConsumer);
    }

    private static void oldApi() throws IOException, InterruptedException
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
