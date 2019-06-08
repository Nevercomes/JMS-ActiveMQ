package com.sun.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: sun
 * @date: 2019/6/8
 */
public class AppConsumer {

    public static final String url = "tcp://39.108.210.252:61616";
    public static final String topicName = "topic-test";

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
        Destination destination = session.createTopic(topicName);

        // 5. 创建一个消费者监听消息
        MessageConsumer consumer = session.createConsumer(destination);

        // 6. 创建监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
//        connection.close();
    }

}
