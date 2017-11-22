package com.ma.test;

import com.ma.weixin.WeixinUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-weixin.xml")
public class TestCase {

    @Autowired
    private WeixinUtil weixinUtil;

    @Test
    public void sendMessage() {
        weixinUtil.sendMessage(Arrays.asList(1),"HELLO");
    }

    @Test
    public void deleteAccount() {
        weixinUtil.deleteAccount(10003);
    }

    @Test
    public void createAccount() {
        //weixinUtil.createAccount(10001,"李京","15839175992", Arrays.asList(1001));
        weixinUtil.createAccount(10003,"张浩","15993439991", Arrays.asList(1001));
    }


    @Test
    public void deleteDept() {
        weixinUtil.deleteDept(1001);
    }

    @Test
    public void createDept() {
        weixinUtil.createDept("绿巨人部",1,4);
        weixinUtil.createDept("雷神部",1,6);
        weixinUtil.createDept("蚁人部",1,7);
    }


    @Test
    public void weixinTest() {
        String token = weixinUtil.getToken(weixinUtil.TYPE_CONTACTS_TOKEN);
        System.out.println(token);
    }

}
