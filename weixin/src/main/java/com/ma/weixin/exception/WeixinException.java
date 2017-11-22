package com.ma.weixin.exception;

/**
 * Created by Administrator on 2017/11/21 0021.
 */
public class WeixinException extends RuntimeException {

    public WeixinException(){}

    public WeixinException(String message) {
        super(message);
    }

    public WeixinException(Throwable throwable) {
        super(throwable);
    }

    public WeixinException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
