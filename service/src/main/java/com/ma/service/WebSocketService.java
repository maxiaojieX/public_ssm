package com.ma.service;

import com.ma.entity.AccountOnline;

import java.util.List;

/**
 * Created by Administrator on 2017/11/19 0019.
 */
public interface WebSocketService {

     List<AccountOnline> findAccountOnline();

     void saveToOnline(AccountOnline accountOnline);
}
