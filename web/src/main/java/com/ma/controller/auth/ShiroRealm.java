package com.ma.controller.auth;

import com.ma.entity.Account;
import com.ma.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AccountService accountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
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
