package com.ma.weixin;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ma.weixin.exception.WeixinException;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
@Component
public class WeixinUtil {

    @Value("${CorpID}")
    private String corpid;
    @Value("${Secret}")
    private String secret;
    @Value("${Secret.Concat}")
    private String SecretConcat;
    @Value("${AgentId}")
    private Integer AgentId;

    /**
     * 管理工具secret
     */
    public static final String TYPE_NORMAL_TOKEN = "normal";
    /**
     * 企业secret
     */
    public static final String TYPE_CONTACTS_TOKEN = "contacts";

    /**
     * 获取token的URL
     */
    private static final String URL_GETTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    /**
     * 创建部门的URL
     */
    private static final String URL_CREATE_DEPT_POST = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";
    /**
     * 删除部门的URL
     */
    private static final String URL_DELETE_DEPT_GET = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=%s&id=%s";
    /**
     * 创建用户的URL
     */
    private static final String URL_CREATE_ACCOUNT_POST = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=%s";
    /**
     * 删除用的URL
     */
    private static final String URL_DELETE_ACCOUNT_GET = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=%s&userid=%s";
    /**
     * 发送信息的URL
     */
    private static final String URL_SENDMESSAGE_POST = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";


    public void sendMessage(List<Integer> accountIdList,String message) {
        String url = String.format(URL_SENDMESSAGE_POST,getToken(TYPE_CONTACTS_TOKEN));

        StringBuilder stringBuilder = new StringBuilder();
        for(Integer integer : accountIdList) {
            stringBuilder.append(integer).append("|");
        }
        String idString = stringBuilder.toString();
        idString = idString.substring(0,stringBuilder.lastIndexOf("|"));

        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("touser",idString);
        map.put("touser","MaXiaoJie");
        map.put("msgtype","text");
        map.put("agentid",AgentId);
        Map<String,String> messageMap = new HashMap<String, String>();
        messageMap.put("content",message);
        map.put("text",messageMap);

        String result = sendHttpPostRequest(url,JSON.toJSONString(map));
        Map<String,Object> resultMap = JSON.parseObject(result);

        if(!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("发送消息失败"+result);
        }


    }

    /**
     * 删除用户
     * @param accountId
     */
    public void deleteAccount(Integer accountId) {
        String url = String.format(URL_DELETE_ACCOUNT_GET,getToken(TYPE_NORMAL_TOKEN),accountId);
        String result = sendHttpRequest(url);
        Map<String,Object> resultMap = JSON.parseObject(result,HashMap.class);

        if(!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("删除账号异常"+result);
        }
    }

    /**
     * 创建用户
     * @param accountId 用户id
     * @param accountName 用户名称
     * @param phone 用户电话
     * @param deptidList 所属部门id列表
     */
    public void createAccount(Integer accountId, String accountName, String phone, List<Integer> deptidList) {
        String url = String.format(URL_CREATE_ACCOUNT_POST,getToken(TYPE_NORMAL_TOKEN));

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userid",accountId);
        map.put("name",accountName);
        map.put("mobile",phone);
        map.put("department",deptidList);

        String result = sendHttpPostRequest(url,JSON.toJSONString(map));
        Map<String,Object> resultMap = JSON.parseObject(result,HashMap.class);
        if(!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("创建账号异常"+result);
        }

    }

    /**
     * 删除部门
     * @param id 部门id
     */
    public void deleteDept(Integer id) {
        String url = String.format(URL_DELETE_DEPT_GET,getToken(TYPE_NORMAL_TOKEN),id);

        String result = sendHttpRequest(url);
        Map<String,Object> resultMap = JSON.parseObject(result,HashMap.class);

        if(!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("删除部门异常"+result);
        }
    }


    /**
     * 创建部门
     * @param name
     * @param parentId
     * @param id
     */
    public void createDept(String name,Integer parentId,Integer id) {
        String url = String.format(URL_CREATE_DEPT_POST,getToken(TYPE_NORMAL_TOKEN));

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("parentid",parentId);
        map.put("id",id);


        String result = sendHttpPostRequest(url,JSON.toJSONString(map));
        Map<String,Object> resultMap = JSON.parseObject(result,HashMap.class);

        if(!resultMap.get("errcode").equals(0)){
            throw new WeixinException("创建部门异常" + result);
        }
    }


    /**
     * 根据不同类型的secret获取不同的token
     * @param type normal:普通的token   contacts：通讯录token
     * @return
     */
    public String getToken(String type) {
        try {
            return  cacheToken.get(type);
        } catch (ExecutionException e) {
            throw new WeixinException("获取token异常",e);
        }
    }

    /**
     * 使用缓存来获取token,使用的是google的guava
     */
    private LoadingCache<String,String> cacheToken = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String type) throws Exception {
                    String url;
                    if(TYPE_NORMAL_TOKEN.equals(type)){
                        //获取普通token
                        url = String.format(URL_GETTOKEN,corpid,secret);
                    }else {
                        //获取通讯录token
                        url = String.format(URL_GETTOKEN,corpid,SecretConcat);
                    }
                    String resultJson = sendHttpRequest(url);
                    //使用阿里的fastJson把String格式的json转成map
                    Map<String, Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if(map.get("errcode").equals(0)){
                        return (String) map.get("access_token");
                    }
                    throw new WeixinException(resultJson);
                }
            });


    /**
     * 发起带有参数的POST请求
     * @param url
     * @param json String格式的json,(eg: JSON.toJSONString(map))
     * @return
     */
    private String sendHttpPostRequest(String url,String json) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient okHttpClient = new OkHttpClient();
        //构建json请求体
        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new WeixinException("发起post请求异常",e);
        }
    }


    /**
     * 使用OKhttp来发起GET请求
     * @param url
     * @return 返回发起请求返回的body并转成string格式
     */
    private String sendHttpRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new WeixinException("sendHttpRequest异常");
        }
    }

}
