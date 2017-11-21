package com.ma.entity;

import java.util.Date;

public class SaleChance {
    private Integer id;

    private String name;

    private Integer cid;

    private String worth;

    private String process;

    private String content;

    private Date createTime;

    private Date genjinTime;

    private Integer aid;

    /**
     * 备用字段用来存放关联客户名字
     */
    private String other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getGenjinTime() {
        return genjinTime;
    }

    public void setGenjinTime(Date genjinTime) {
        this.genjinTime = genjinTime;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}