package com.dlut.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class ConnectionUtil
{
    /**
     * 获取mq的链接
     * @return
     */
    public static Connection getConnection() throws IOException
    {
        //定义一个链接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("127.0.0.1");

        //设置端口号Amqp
        factory.setPort(5672);

        //设置那台数据库
        factory.setVirtualHost("/vhost");

        //设置用户名
        factory.setUsername("fys");

        //设置密码
        factory.setPassword("123");

        //创建一个连接
        Connection connection = factory.newConnection();

        return connection;
    }
}
