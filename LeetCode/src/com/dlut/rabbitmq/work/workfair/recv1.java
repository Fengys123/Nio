package com.dlut.rabbitmq.work.workfair;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者消费消息
 */
public class recv1
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

        //队列声明(保险写上这句话??????????????)
        channel.queueDeclare(queuename,false,false,false,null);

        channel.basicQos(1);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String strMsg = new String(body,"utf-8");
                System.out.println("[recv2] msg:" + strMsg);

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
                    System.out.println("[2] done!!!");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };


        boolean autoAck = false;
        //监听这个队列(这种写法类似于android的addlistener的方法)
        channel.basicConsume(queuename,autoAck,defaultConsumer);
    }
}
