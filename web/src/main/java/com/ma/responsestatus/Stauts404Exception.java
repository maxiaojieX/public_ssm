package com.ma.responsestatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Administrator on 2017/11/10 0010.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class Stauts404Exception extends RuntimeException {
    public Stauts404Exception(){}
    public  Stauts404Exception(String message){
        super(message);
    }

}
