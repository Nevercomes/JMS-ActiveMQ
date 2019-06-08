package com.nevercome.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息的提供者
 * @author: sun
 * @date: 2019/6/7
 */
public class AppProducer {

    public static final String url = "tcp://39.108.210.252:61616";
    public static final String queueName = "queue-test";

    public static void main(String[] args) throws JMSException {
        // 可以看JMS.png来查看步骤
        // 1. 创建连接工厂 对于jms这一规范来说 它的连接工厂由服务商来提供
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // 2. 通过连接工厂来创建连接
        Connection connection = connectionFactory.createConnection();

        // 2.1 启动连接...物理启动
        connection.start();

        // 3. 创建session 第一个参数为是否开启事务 第二个参数为应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4. 指定目的地 创建目标 这里是一个接收消息的队列
        Destination destination = session.createQueue(queueName);

        // 5. 创建一个生产者向目标发送消息
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            // 6. 创建消息 这里使用最简单的text消息体 发送消息
            TextMessage textMessage = session.createTextMessage("test" + i);
            // 7. 发送消息
            producer.send(textMessage);
            System.out.println("发送消息：" + textMessage.getText());
        }
        connection.close();
    }

}
