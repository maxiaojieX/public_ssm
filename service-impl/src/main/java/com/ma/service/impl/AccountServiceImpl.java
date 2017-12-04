package com.ma.service.impl;

import com.ma.entity.Account_Dept;
import com.ma.example.Account_DeptExample;
import com.ma.exception.LoginException;
import com.ma.exception.ServiceException;
import com.ma.mapper.Account_DeptMapper;
import com.ma.service.AccountService;
import com.ma.entity.Account;
import com.ma.example.AccountExample;
import com.ma.mapper.AccountMapper;
import com.ma.weixin.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Integer COMPANY_ID = 1;


    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private Account_DeptMapper account_deptMapper;
    @Autowired
    private WeixinUtil weixinUtil;

    @Override
    public Account findByName(String name,String password) throws LoginException {

        Account account = accountMapper.findByUserName(name);

        if(account != null && account.getPassword().equals(password)){
            return account;
        }else {
            throw new LoginException("账号或密码错误!");
        }

    }

    //根据动态SQL查询到带有部门list的Account
    @Override
    public List<Account> findByDeptId(Map<String, Object> queryParam) {
        Integer start = (Integer) queryParam.get("start");
        Integer length = (Integer) queryParam.get("length");
        Integer deptId = (Integer) queryParam.get("deptId");
        String accountName = (String) queryParam.get("accountName");

        if(deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }

        List<Account> accountList = accountMapper.findByDeptId(accountName,deptId,start,length);
        return accountList;
    }

    //根据部门id获得account数量，如果deptId=null，那么获得全部
    @Override
    public Long findTotleByDept(Integer deptId) {
        if(deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }
        return accountMapper.countByDeptId(deptId);
    }

    @Override
    public Account findByPhone(String phone, String password) {
        Account account = accountMapper.findByPhone(phone);
        if(account != null && password.equals(account.getPassword())){
            return account;
        }
        return null;
    }

    /**
     * 保存新增员工
     * @param account 员工对象
     * @return 新增员工主键ID
     */
    @Override
    public int saveAccount(Account account ,Integer[] did) throws ServiceException{
        //先判断手机号是否存在
        Account account1 = accountMapper.findByPhone(account.getPhone());
        if(account1 != null){
            throw new ServiceException("该手机号已经被占用!");
        }

        account.setCreatTime(new Date());
        account.setUpdateTime(new Date());
        accountMapper.insertSelective(account);
        int id = account.getId();
        List<Integer> deptList = new ArrayList<>();
        for(Integer i : did){
            Account_Dept account_dept = new Account_Dept();
            account_dept.setAid(id);
            account_dept.setDid(i);
            account_deptMapper.insertSelective(account_dept);

            deptList.add(i);
        }

        //同步到企业微信
        //weixinUtil.createAccount(id,account.getUsername(),account.getPhone(),deptList);

        return id;
    }

    /**
     * 根据员工ID删除员工
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //删除关联关系表对应数据
        Account_DeptExample account_deptExample = new Account_DeptExample();
        account_deptExample.createCriteria().andAidEqualTo(id);
        account_deptMapper.deleteByExample(account_deptExample);

        //删除员工
        accountMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<Account> findAllWithDept() {
        return accountMapper.findAllWithDept();
    }

    @Override
    public List<Account> findAll() {
        return accountMapper.selectByExample(new AccountExample());
    }



    @Override
    public void addAccount(Account account) {
        accountMapper.insert(account);
    }
}
