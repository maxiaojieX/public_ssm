package com.ma.controller;

import com.ma.entity.Account;
import com.ma.entity.Account_Dept;
import com.ma.exception.ServiceException;
import com.ma.service.AccountService;
import com.ma.service.Account_DeptService;
import com.ma.service.DeptService;
import com.ma.service.impl.util.AjaxStateJson;
import com.ma.service.impl.util.DataTablesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/8 0008.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private Account_DeptService account_deptService;
    @Autowired
    private DeptService deptService;

    @GetMapping
    public String acountHome() {
        return "employee/employeeHome";
    }


    @GetMapping("/list2")
    public String employee2() {
        return "employee/employeeList";
    }

    @GetMapping("/getTable")
    @ResponseBody
    public DataTablesResult<Account> loadEmployeeList(
            Integer draw, Integer start, Integer length,
            Integer deptId,
            HttpServletRequest request) {
        //这是根据搜索框传来的值
        String keyword = request.getParameter("search[value]");

        Map<String,Object> map = new HashMap<>();
        map.put("start",start);
        map.put("length",length);
        map.put("accountName",keyword);
        map.put("deptId",deptId);


        List<Account> accountList = accountService.findByDeptId(map);
        //总数据条数需要另作查询
        Long total = accountService.findTotleByDept(deptId);
        //这是返回给table json的格式
        return new DataTablesResult<>(draw,total.intValue(),accountList);
    }






    //跳转到新增员工jsp
    @GetMapping("/addAccount")
    public String addAccount(String treeId,Model model){
        model.addAttribute("treeId",treeId);
        return "employee/employeeAdd";
    }


    //新增员工
    @PostMapping("/add")
    @ResponseBody
    public AjaxStateJson toAdd(Account account, Integer[] deptId, Model model){
        System.out.println(account);
        //插入数据
        //保存员工并获取新增主键
        try {
            int id = accountService.saveAccount(account, deptId);
            System.out.println(id);
            return new AjaxStateJson("success","新增成功");
        } catch (ServiceException se){
            return new AjaxStateJson("error",se.getMessage());
        }

    }

    @PostMapping("/delete")
    @ResponseBody
    public AjaxStateJson deleteAccount(String id){
        accountService.deleteById(Integer.parseInt(id));
        return new AjaxStateJson("success","删除成功");
    }

    @PostMapping("/delDept")
    @ResponseBody
    public AjaxStateJson deleteDept(Integer id) {
        System.out.println("id:  => "+id);
        List<Account_Dept> account_deptList = account_deptService.findByDeptId(id);
        System.out.println("account_deptList - > "+account_deptList);
        if(account_deptList.size() == 0) {
            //可以删
            deptService.deleteDeptById(id);
            return new AjaxStateJson("success","删除成功");
        }else{
            return new AjaxStateJson("error","该部门下人员不为空，不允许删除");
        }

    }


}
