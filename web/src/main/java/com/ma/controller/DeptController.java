package com.ma.controller;

import com.ma.entity.Dept;
import com.ma.exception.ServiceException;
import com.ma.service.DeptService;
import com.ma.service.impl.util.AjaxStateJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8 0008.
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;



    //获得部门json
    @GetMapping("/dept.json")
    @ResponseBody
    public List<Dept> getTree() {
        List<Dept> deptList = deptService.findAll();
        return deptList;
    }

    @PostMapping("/add")
    @ResponseBody
    public AjaxStateJson addDept(String deptName){
        try {
            deptService.saveNewDept(deptName);
            return new AjaxStateJson("success","保存成功");
        }catch (ServiceException se){
            return new AjaxStateJson("error",se.getMessage());
        }
    }

}
