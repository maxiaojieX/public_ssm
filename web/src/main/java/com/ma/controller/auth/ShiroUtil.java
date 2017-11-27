package com.ma.controller.auth;

import com.ma.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Administrator on 2017/11/27 0027.
 */
public class ShiroUtil {

    public static Account getCurretnAccount() {
        return (Account) getSubject().getPrincipal();
    }
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
