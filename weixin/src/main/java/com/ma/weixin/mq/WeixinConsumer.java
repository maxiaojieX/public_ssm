package com.ma.weixin.mq;

import com.alibaba.fastjson.JSON;
import com.ma.weixin.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/22 0022.
 */
@Component
public class WeixinConsumer {

    @Autowired
    private WeixinUtil weixinUtil;

    @JmsListener(destination = "wexinMessage-Quere")
    public void sendMessage(String json) {
        Map<String,Object> map = JSON.parseObject(json);
        weixinUtil.sendMessage(Arrays.asList(1),map.get("message").toString());
    }

}
