package com.ma.util;

/**
 * Created by Administrator on 2017/11/9 0009.
 */
public class AjaxStateJson {


    public AjaxStateJson(){}
    public AjaxStateJson(String state,String message){
        this.state = state;
        this.message = message;
    }

    private String state;
    private String message;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
