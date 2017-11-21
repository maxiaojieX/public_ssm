package com.ma.responsestatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 无权限状态
 * Created by Administrator on 2017/11/10 0010.
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class Status403Exception extends RuntimeException {
    public Status403Exception(){}
    public Status403Exception(String message){
        super(message);
    }
}
