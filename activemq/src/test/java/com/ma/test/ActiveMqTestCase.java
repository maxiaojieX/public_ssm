package com.ma.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
public class ActiveMqTestCase {

    @Test
    public void getMessage() throws JMSException, IOException {

        String url = "tcp://127.0.0.1:61616";
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //创建自定义重试
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        redeliveryPolicy.setInitialRedeliveryDelay(3000);
        redeliveryPolicy.setRedeliveryDelay(3000);

        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);

        Destination destination = session.createQueue("test-Message");

        MessageConsumer messageConsumer = session.createConsumer(destination);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println(textMessage.getText());
                    if(1 == 1){
                        throw new JMSException("手动引发的异常");
                    }
                    //手动签收消息
                    textMessage.acknowledge();

                } catch (JMSException e) {
                    e.printStackTrace();
                    try {
                        session.recover();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }


    @Test
    public void send() throws JMSException {

        //1.创建ConnectionFactory
        String url = "tcp://127.0.0.1:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection，并开启connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //3.用connection创建session
        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);

        //4.用session创建目的地
        Destination destination = session.createQueue("test-Message");

        //5.用session创建生产者,并设置为持久化模式
        MessageProducer messageProducer = session.createProducer(destination);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        //6.用session创建消息
        TextMessage textMessage = session.createTextMessage("ActiveMq-测试2");

        //7.用生产者发送消息,并提交事务
        messageProducer.send(textMessage);
        session.commit();

        //8.释放资源
        messageProducer.close();
        session.close();
        connection.close();


    }

}
