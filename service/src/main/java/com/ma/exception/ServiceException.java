package com.ma.exception;

/**
 * Created by Administrator on 2017/11/9 0009.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(){}
    public ServiceException(String message){
        super(message);
    }
    public ServiceException(Throwable throwable) {
        super(throwable);
    }
    public ServiceException(String message,Throwable throwable){
        super(message,throwable);

    }
}
