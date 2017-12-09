package com.ma.jobs;

import com.ma.send.SendMail;
import com.ma.service.impl.util.MySpringContext;
import org.apache.commons.lang3.StringUtils;
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
        String email = (String) jobDataMap.get("email");
        String Email = (String) jobDataMap.get("Email");
        String weixin = (String) jobDataMap.get("wexin");

        if(StringUtils.isNotEmpty(email)) {
            //需要发送邮件
            SendMail.send("15239131507@163.com","ma000000",Email,"任务提醒",message);
        }

        if(StringUtils.isNotEmpty(weixin)){
            //微信提醒
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
}
