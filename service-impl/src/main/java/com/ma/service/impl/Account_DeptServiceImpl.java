package com.ma.service.impl;

import com.ma.entity.Account_Dept;
import com.ma.service.Account_DeptService;
import com.ma.example.Account_DeptExample;
import com.ma.mapper.Account_DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
@Service
public class Account_DeptServiceImpl implements Account_DeptService {
    @Autowired
    private Account_DeptMapper account_deptMapper;

    @Override
    public void save(int aid, int did) {
        Account_Dept account_dept = new Account_Dept();
        account_dept.setAid(aid);
        account_dept.setDid(did);
        account_deptMapper.insert(account_dept);
    }
}