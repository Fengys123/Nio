package com.dlut.rabbitmq.routing;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class recv1
{
    public static final String QUEUE_BAME = "test_queue_direct_1";
    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException
    {
        Connection connection = ConnectionUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //声明交换机???
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        //队列声明(保险写上这句话??????????????)
        channel.queueDeclare(QUEUE_BAME,false,false,false,null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_BAME,EXCHANGE_NAME,"error");

        channel.basicQos(1);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String strMsg = new String(body,"utf-8");
                System.out.println("[recv1] msg:" + strMsg);

                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    System.out.println("[1] done!!!");
                    //手动回复
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };


        boolean autoAck = false;
        //监听这个队列(这种写法类似于android的addlistener的方法)
        channel.basicConsume(QUEUE_BAME,autoAck,defaultConsumer);
    }
}
