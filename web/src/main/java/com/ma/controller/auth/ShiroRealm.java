package com.ma.controller.auth;

import com.ma.entity.Account;
import com.ma.entity.Account_Dept;
import com.ma.entity.Dept;
import com.ma.service.AccountService;
import com.ma.service.Account_DeptService;
import com.ma.service.DeptService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private Account_DeptService account_deptService;

    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Account account = (Account) principalCollection.getPrimaryPrincipal();
        List<Account_Dept> account_deptList = account_deptService.findByAccountId(account.getId());
        List<String> deptNameList = new ArrayList<>();

        for(Account_Dept account_dept : account_deptList) {
            Dept dept = deptService.findById(account_dept.getDid());
            deptNameList.add(dept.getDeptName());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(deptNameList);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userName = usernamePasswordToken.getUsername();
        char[] password = usernamePasswordToken.getPassword();
        Account account = accountService.findByPhone(userName,new String(password));

        if(account != null) {
            return new SimpleAuthenticationInfo(account,account.getPassword(),getName());
        }

        return null;
    }
}
