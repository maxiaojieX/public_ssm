package com.ma.entity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Task {
    private Integer id;

    private String title;

    private Date finishTime;

    private Date remindTime;

    private Integer done;

    private Integer aid;

    private Integer cid;

    private Integer sid;

    private Date createTime;

    private Customer customer;

    public SaleChance getSaleChance() {
        return saleChance;
    }

    public void setSaleChance(SaleChance saleChance) {
        this.saleChance = saleChance;
    }

    private SaleChance saleChance;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    //判断是否逾期
    private boolean overTime;

    /**
     * @return true:逾期
     */
    public boolean isOverTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String stringDateTime = df.format(getFinishTime());

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime1 = dateTimeFormatter.parseDateTime(stringDateTime);

        return dateTime1.isBeforeNow();
    }

    public void setOverTime(boolean overTime) {
        this.overTime = overTime;
    }
}