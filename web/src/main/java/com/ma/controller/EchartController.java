package com.ma.controller;

import com.ma.service.CustomerService;
import com.ma.service.RecordService;
import com.ma.util.AjaxStateJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/18 0018.
 */
@Controller
@RequestMapping("/echarts")
public class EchartController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/toEcharts")
    public String toEcharts() {
        return "echarts/echarts";
    }

    @GetMapping("/getProcess.json")
    @ResponseBody
    public AjaxStateJson getProcessJson() {
        List<Map<String,Object>> mapList = recordService.findProcessToEcharts();
        AjaxStateJson ajaxStateJson = new AjaxStateJson("success","success");
        ajaxStateJson.setData(mapList);
        return ajaxStateJson;
    }


    @GetMapping("/getCustomer.json")
    @ResponseBody
    public AjaxStateJson getCustomerJson(){
        List<Map<String,Object>> mapList = customerService.findEcharts();
        AjaxStateJson ajaxStateJson = new AjaxStateJson("success","success");
        ajaxStateJson.setData(mapList);
        return ajaxStateJson;
    }





}
