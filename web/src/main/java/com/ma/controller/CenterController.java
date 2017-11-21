package com.ma.controller;

import com.ma.entity.Account;
import com.ma.entity.Dept;
import com.ma.exception.LoginException;
import com.ma.service.AccountService;
import com.ma.service.DeptService;
import com.ma.service.impl.AccountServiceImpl;
import jdk.nashorn.internal.objects.NativeJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String login(String account, String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {

        try {
            //account代表phone  使用phone作为账号登录
            Account account1 = accountService.findByPhone(account,password);

            session.setAttribute("account",account1);
            return "redirect:/account";
        }catch (LoginException lo){
            redirectAttributes.addFlashAttribute("message","账号或密码错误");
            return "redirect:/";
        }
    }




}
