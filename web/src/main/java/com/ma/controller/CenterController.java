package com.ma.controller;

import com.ma.controller.auth.ShiroUtil;
import com.ma.entity.Account;
import com.ma.entity.Dept;
import com.ma.exception.LoginException;
import com.ma.service.AccountService;
import com.ma.service.DeptService;
import com.ma.service.impl.AccountServiceImpl;
import jdk.nashorn.internal.objects.NativeJSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7 0007.
 */
@Controller
public class CenterController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private DeptService deptService;

    @GetMapping("/")
    public String hello() {
        Subject subject = ShiroUtil.getSubject();

        if(subject.isAuthenticated()){
            subject.logout();
        }
        if(!subject.isAuthenticated() && subject.isRemembered()) {
            return "redirect:/account";
        }

        return "index";
    }

    /**
     * @param account
     * @param password
     * @param redirectAttributes
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(String account, String password, boolean rememberMe,
                        RedirectAttributes redirectAttributes,
                        HttpSession session,
                        HttpServletRequest request) {

        try {
            //account代表phone  使用phone作为账号登录
            //Account account1 = accountService.findByPhone(account,password);

            //session.setAttribute("account",account1);

            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken =
                    new UsernamePasswordToken(account,password,rememberMe);
            subject.login(usernamePasswordToken);

            String url = "/account";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if(savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }

            return "redirect:" + url;
        }catch (AuthenticationException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","账号或密码错误");
            return "redirect:/";
        }
    }


    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }

}
