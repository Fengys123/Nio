package com.dlut.rabbitmq.confirm;

import com.dlut.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 批量模式(异步)
 */
public class sender3
{
    public static final String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmSelect 将channel设置为confirm模式  如果之前设为了tx模式,则不能设为confirm模式
        channel.confirmSelect();

        //存放未确认的消息
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //通道添加监听
        channel.addConfirmListener(new ConfirmListener()
        {
            //没有问题的handleack
            @Override
            public void handleAck(long l, boolean b) throws IOException
            {
                //multiple
                if(b)
                {
                    System.out.println("handleack-----multiple");
                    confirmSet.headSet(l+1).clear();
                }
                else
                {
                    System.out.println("handleack-----multiple----false");
                    confirmSet.remove(l);
                }
            }

            //handleNack keyi
            @Override
            public void handleNack(long l, boolean b) throws IOException
            {
                //multiple
                if(b)
                {
                    System.out.println("handleack-----multiple");
                    confirmSet.headSet(l+1).clear();
                }
                else
                {
                    System.out.println("handleack-----multiple----false");
                    confirmSet.remove(l);
                }
            }
        });




        String msg = "hello tx message!!!";
        System.out.println("[send] " + msg);

        while(true)
        {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }

    }
}
