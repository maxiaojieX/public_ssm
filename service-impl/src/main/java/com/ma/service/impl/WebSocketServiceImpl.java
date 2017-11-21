package com.ma.service.impl;

import com.ma.entity.AccountOnline;
import com.ma.example.AccountOnlineExample;
import com.ma.mapper.AccountOnlineMapper;
import com.ma.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/19 0019.
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private AccountOnlineMapper accountOnlineMapper;

    @Override
    public  List<AccountOnline> findAccountOnline() {
        return accountOnlineMapper.selectByExample(new AccountOnlineExample());
    }

    @Override
    public void saveToOnline(AccountOnline accountOnline) {
        accountOnlineMapper.saveOnline(accountOnline.getAid());
    }
}
