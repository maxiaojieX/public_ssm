package com.ma.service;

import com.ma.entity.Account;
import com.ma.exception.LoginException;
import com.ma.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
public interface AccountService {

    void addAccount(Account account);

//    List<Account> findByDeptId(Integer id);
    List<Account> findAll();
    List<Account> findAllWithDept();

    Account findByName(String name,String password) throws LoginException;

    Account findByPhone(String phone, String password);
    List<Account> findByDeptId(Map<String,Object> queryParam);

    Long findTotleByDept(Integer deptId);

    int saveAccount(Account account,Integer[] did) throws ServiceException;

    void deleteById(int id);

}
