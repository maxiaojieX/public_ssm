package com.ma.controller;

import com.ma.entity.Account;
import com.ma.entity.AccountOnline;
import com.ma.service.WebSocketService;
import com.ma.util.AjaxStateJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by Administrator on 2017/11/17 0017.
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private WebSocketService webSocketService;

    @GetMapping("/toChat")
    public String toChat(HttpSession session,
                         Model model) {
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("accountId",account.getId());
        model.addAttribute("accountName",account.getUsername());
        return "chat/chat";
    }

    @PostMapping("/saveOnline")
    @ResponseBody
    public AjaxStateJson saveOnline(Integer accountId) {
        System.out.println(accountId+"accountId");
        List<AccountOnline> accountOnlineList = webSocketService.findAccountOnline();
        boolean FLAG = true;
        if(accountOnlineList != null){
            System.out.println("AccoutOnlineList不为NULL");
            for(AccountOnline accountOnline : accountOnlineList) {
                if(accountOnline.getAid() == accountId){
                    FLAG = false;
                }
            }
        }
        if(FLAG){
            //在线表中没有此用户，保存至在线表
            System.out.println("向在线表中新增");
            AccountOnline accountOnline = new AccountOnline();
            accountOnline.setAid(accountId);
            System.out.println(accountOnline.getAid()+"aid");
            webSocketService.saveToOnline(accountOnline);
        }

        return new AjaxStateJson("success","加入群聊成功");
    }

}
