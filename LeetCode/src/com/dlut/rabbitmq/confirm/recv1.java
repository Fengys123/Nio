package com.dlut.rabbitmq.confirm;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class recv1
{
    public static final String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws IOException
    {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                System.out.println("[recv tx msg]" +  new String(body,"utf-8"));
            }
        });
    }
}
