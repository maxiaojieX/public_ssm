package com.ma.jobs;

import org.apache.xbean.spring.context.XmlWebApplicationContext;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Administrator on 2017/11/15 0015.
 */
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String message = (String) jobDataMap.get("message");

        System.out.println("***********" + message + "*************");

        //把消息添加至ActiveMQ
        try {
            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("springApplicationContext");

            JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
            jmsTemplate.send("wexinMessage-Quere", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    String json = "{\"id\":\"MaXiaoJie\",\"message\":\"Hello,Message from JMS\"}";
                    TextMessage textMessage = session.createTextMessage(json);
                    return textMessage;
                }
            });

        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }
}
