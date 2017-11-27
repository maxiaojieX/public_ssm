package com.ma.controller;

import com.github.pagehelper.PageInfo;
import com.ma.controller.auth.ShiroUtil;
import com.ma.entity.Account;
import com.ma.entity.Customer;
import com.ma.entity.CustomerSource;
import com.ma.entity.SaleChance;
import com.ma.exception.ServiceException;
import com.ma.responsestatus.Status403Exception;
import com.ma.responsestatus.Stauts404Exception;
import com.ma.service.AccountService;
import com.ma.service.CustomerService;
import com.ma.service.CustomerSourceService;
import com.ma.service.RecordService;
import com.ma.service.impl.util.AjaxStateJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9 0009.
 */
@Controller
@RequestMapping("/customer")
public class CustomController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerSourceService customerSourceService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RecordService recordService;

    @GetMapping
    private String customer(Model model,
                            @RequestParam(required = false,defaultValue = "0") Integer customerId,
                            HttpSession session,
                            @RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNum){
        if(customerId != 0){
            //展示指定客户的详情
            //操作权限过滤
            Customer customer0 = operationFilter(customerId,session);

            //获取所有员工
            List<Account> accountList = accountService.findAll();
            model.addAttribute("accountList",accountList);
            model.addAttribute("customer",customer0);
            //获取该客户下的所有销售机会
            List<SaleChance> saleChanceList = recordService.findByCustomerId(customer0.getId());
            model.addAttribute("saleChanceList",saleChanceList);
            return "custom/customerDetails";
        }else {
            //获取该员工下的所有客户
//            Account account = (Account) session.getAttribute("account");

            Account account = ShiroUtil.getCurretnAccount();
            PageInfo<Customer> customerPageInfo = customerService.findAllByAccountId(account.getId(),pageNum);

            model.addAttribute("page",customerPageInfo);
            return "custom/customer";
        }
    }

    /**
     * 跳转至客户新增页面
     * @return
     */
    @GetMapping("/add")
    public String customerAdd(Model model,HttpSession session){
        //获取客户来源表数据，并返回前端
        List<CustomerSource> customerSourceList = customerSourceService.findAll();
        model.addAttribute("customerSource",customerSourceList);
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        model.addAttribute("accountId",account.getId());
        return "/custom/customerAdd";
    }

    @PostMapping("/add")
    public String saveCustomer(Customer customer,RedirectAttributes redirectAttributes){
        int customerId = customerService.saveCustomer(customer);
        //理应跳至新增客户详情页，暂时先跳至客户list页
        redirectAttributes.addFlashAttribute("message","新增成功!");
        return "redirect:/customer?customerId="+customerId;
    }

    @GetMapping("/edit/{id:\\d+}")
    public String editCustomer(@PathVariable Integer id,
                               Model model,
                               HttpSession session){
        List<CustomerSource> customerSourceList = customerSourceService.findAll();
        model.addAttribute("customerSource",customerSourceList);

        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        model.addAttribute("accountId",account.getId());

        //操作权限过滤
        Customer customer = operationFilter(id,session);
        model.addAttribute("customer",customer);
        return "/custom/customerEdit";
    }

    @PostMapping("/upadte")
    private String updateCustomer(Customer customer, RedirectAttributes redirectAttributes){
        customerService.updateCustomer(customer);
        redirectAttributes.addFlashAttribute("message","修改成功!");
        return "redirect:/customer?customerId="+customer.getId();
    }

    @GetMapping("/delete/{id:\\d+}")
    public String deleteCustomer(@PathVariable Integer id,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes){
        //操作权限过滤
        Customer customer = operationFilter(id,session);

        customerService.deleteByCustomerId(id);

        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/customer";
    }

    @GetMapping("/public/{id:\\d+}")
    public String publicCustomer(@PathVariable Integer id,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes){
        Customer customer = operationFilter(id,session);

        customerService.publicCustomer(customer);
        redirectAttributes.addFlashAttribute("message","流放成功");
        return "redirect:/customer";
    }

    @GetMapping("/trans/{customerId:\\d+}/to/{accountId:\\d++}")
    public String transToOthers(@PathVariable Integer customerId,
                                @PathVariable Integer accountId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes){
        //操作权限过滤
        Customer customer = operationFilter(customerId,session);

        try {
            System.out.println(">>>>>>>>>" + accountId);
            customerService.transToOthers(customer,accountId);
            redirectAttributes.addFlashAttribute("message","转交成功!");
            return "redirect:/customer";
        }catch (ServiceException se){
            redirectAttributes.addFlashAttribute("message",se.getMessage());
            return "redirect:/customer";
        }

    }

    /**
     * 返回公海客户jsp
     * @return
     */
    @GetMapping("/public")
    public String publicCustomer(Model model,
                                 @RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNum){
        // 获取所有公海客户 accountId = 0
        int PUBLIC_CUSTOMER = 0;


        PageInfo<Customer> customerPageInfo = customerService.findAllByAccountId(PUBLIC_CUSTOMER,pageNum);

        model.addAttribute("page",customerPageInfo);

        return "custom/customerPublic";
    }


    @PostMapping("/getCustomer/{id:\\d+}")
    @ResponseBody
    public AjaxStateJson getCustomer(@PathVariable Integer id,
                                     HttpSession session){
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        customerService.getPublicCustomer(id,account.getId());
        AjaxStateJson ajaxStateJson = new AjaxStateJson("success","认领成功!");
        return ajaxStateJson;
    }

    /**
     * 导出我的客户为csv文件
     * @param session
     * @param response
     */
    @GetMapping("/export/csv")
    public void exportToCsv(HttpSession session,
                            HttpServletResponse response) {
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        try {

            //设置响应类型，默认下载名称，响应头
            response.setContentType("application/vnd.ms-excel");
            String file = new String("我的客户.csv".getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition","attachment;fileName=\""+ file + "\"");

            OutputStream outputStream = response.getOutputStream();
            customerService.exportToCsv(outputStream,account);


        } catch (IOException e) {
            throw  new ServiceException("导出csv异常");
        }

    }

    @GetMapping("/export/xls")
    public void exportToXls(HttpServletResponse response,
                            HttpSession session){
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();

        try {

            //设置响应类型，默认下载名称，响应头
            response.setContentType("application/vnd.ms-excel");
            String file = new String("我的客户.xls".getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("Content-Disposition","attachment;fileName=\""+ file + "\"");

            OutputStream outputStream = response.getOutputStream();
            customerService.exportToXls(outputStream,account);

        } catch (IOException e) {
            throw  new ServiceException("导出xls异常");
        }

    }








    /**
     * 操作权限过滤
     * @param customerId 客户id
     * @param session 当前session
     * @return
     */
    private Customer operationFilter(Integer customerId,HttpSession session){
        Customer customer0 = customerService.findByCustomerId(customerId);
        if(customer0 == null){
            //客户不存在,抛出404
            throw new Stauts404Exception("该客户不存在");
        }
        //如果存在判断该客户是否属于自己的客户
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        if(customer0.getAccountId() != account.getId()){
            //不属于自己客户
            throw new Status403Exception("对不起，您暂无权查看该客户");
        }
        return  customer0;

    }



}
