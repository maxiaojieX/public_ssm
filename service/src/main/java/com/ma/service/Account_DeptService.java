package com.ma.service;

import com.ma.entity.Account_Dept;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
public interface Account_DeptService {
    //查一个
    //Account_DeptService findByDid(Integer id);
    void save(int aid,int did);

    List<Account_Dept> findByAccountId(Integer accountId);

    List<Account_Dept> findByDeptId(Integer deptId);
}
