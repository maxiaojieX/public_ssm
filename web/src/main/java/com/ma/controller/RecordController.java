package com.ma.controller;

import com.github.pagehelper.PageInfo;
import com.ma.controller.auth.ShiroUtil;
import com.ma.entity.Account;
import com.ma.entity.Customer;
import com.ma.entity.GenjinRecord;
import com.ma.entity.SaleChance;
import com.ma.responsestatus.Status403Exception;
import com.ma.responsestatus.Stauts404Exception;
import com.ma.service.CustomerService;
import com.ma.service.RecordService;
import com.ma.service.impl.util.AjaxStateJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by Administrator on 2017/11/13 0013.
 */
@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/my")
    public String recordMy(Model model,
                           @RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNum,
                           HttpSession session){
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        //获取所有该员工下的销售机会
        PageInfo<SaleChance> saleChancePageInfo = recordService.findAll(account.getId(),pageNum);
        model.addAttribute("page",saleChancePageInfo);
        //获取所有客户
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customerList",customerList);

        return "workrecord/recordMy";
    }

    @PostMapping("/addChance")
    public String addChance(SaleChance saleChance,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            HttpSession session){
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        int recordId = recordService.saveChance(saleChance,account);

        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/record/details?recordId="+recordId;
    }

    @GetMapping("/details")
    public String chanceDetails(Integer recordId,
                                Model model,
                                HttpSession session){

        //销售详情
        //权限过滤
        SaleChance saleChance = opreationFilter(session,recordId);

        model.addAttribute("salechance",saleChance);
        //关联客户详情
        Customer customer = customerService.findByCustomerId(saleChance.getCid());
        model.addAttribute("customer",customer);

        //跟进记录
        List<GenjinRecord> genjinRecordList = recordService.findGenjinRecordByChanceId(saleChance.getId());
        model.addAttribute("genJinList",genjinRecordList);

        return "workrecord/recordDetails";
    }

    @GetMapping("delete")
    public String deleteByChanceId(Integer chanceId,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes){
        opreationFilter(session,chanceId);

        recordService.deleteByChanceId(chanceId);
        redirectAttributes.addFlashAttribute("message","删除成功!");
        return "redirect:/record/my";
    }


    @PostMapping("/updateProcess")
    @ResponseBody
    public AjaxStateJson updateProcess(String process, Integer chanceId,
                                       HttpSession session) {
        //权限过滤
        opreationFilter(session,chanceId);
        //根据chanceId更改进度
        recordService.updateProcess(process,chanceId);
        return new AjaxStateJson("success","更新成功!");
    }

    @PostMapping("/saveGenjin")
    @ResponseBody
    public AjaxStateJson saveGenjin(GenjinRecord genjinRecord) {
        recordService.saveGenjin(genjinRecord);
        return new AjaxStateJson("success","新增成功!");
    }





    /**
     * 操作权限过滤
     * @param session
     * @param chanceId
     * @return
     */
    public SaleChance opreationFilter(HttpSession session,Integer chanceId){

        SaleChance saleChance = recordService.findByRecordId(chanceId);
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        if(saleChance == null){
            throw new Stauts404Exception("该销售机会不存在");
        }

        if(!saleChance.getAid().equals(account.getId())){
            throw new Status403Exception("您无权查看");
        }
        return saleChance;
    }

}
