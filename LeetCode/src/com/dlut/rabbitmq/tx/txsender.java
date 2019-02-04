package com.dlut.rabbitmq.tx;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 这种事务模式不建议使用
 */
public class txsender
{
    public static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException
    {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "hello tx message!!!";
        System.out.println("[send] " + msg);

        try
        {
            //开启事务模式
            channel.txSelect();

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            int x = 1/0;

            channel.txCommit();
        }
        catch (Exception e)
        {
            channel.txRollback();
            System.out.println("send message txRollBack");
        }
        channel.close();
        connection.close();

    }
}
