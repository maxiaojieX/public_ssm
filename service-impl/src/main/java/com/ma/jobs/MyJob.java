package com.ma.jobs;

import com.ma.service.impl.util.MySpringContext;
import org.quartz.*;
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

        JmsTemplate jmsTemplate = (JmsTemplate) MySpringContext.wantBean("jmsTemplate");

        jmsTemplate.send("wexinMessage-Quere", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String json = "{\"id\":\"MaXiaoJie\",\"message\":\""+message+"\"}";
                TextMessage textMessage = session.createTextMessage(json);
                return textMessage;
            }
        });


    }
}
