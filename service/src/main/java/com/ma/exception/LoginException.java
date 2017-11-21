package com.ma.exception;

/**
 * Created by Administrator on 2017/11/8 0008.
 */
public class LoginException extends RuntimeException {

    public LoginException(){}

    public LoginException(String message){
        super(message);
    }

    public LoginException(Throwable throwable) {
        super(throwable);
    }

    public LoginException(Throwable throwable,String message){
        super(message,throwable);
    }
}
