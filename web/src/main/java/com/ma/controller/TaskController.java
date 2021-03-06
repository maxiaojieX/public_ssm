package com.ma.controller;

import com.github.pagehelper.PageInfo;
import com.ma.controller.auth.ShiroUtil;
import com.ma.entity.Account;
import com.ma.entity.Task;
import com.ma.exception.ServiceException;
import com.ma.responsestatus.Status403Exception;
import com.ma.service.TaskService;
import com.ma.service.impl.util.AjaxStateJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/14 0014.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/my")
    public String task(Model model,
                       @RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNum,
                       HttpSession session) {
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();

        PageInfo<Task> page = taskService.findAllByAccountIdWithCNameAndSName(account.getId(),pageNum);
        model.addAttribute("page",page);
        return "task/task";
    }

//    @PostMapping("/add")
//    @ResponseBody
//    public AjaxStateJson addTask(Task task,
//                                 HttpSession session) {
//        Account account = (Account) session.getAttribute("account");
//
//        taskService.saveTask(task,account);
//
//        return new AjaxStateJson("success","保存待办事项成功");
//    }

    @GetMapping("/add")
    public String add() {
        return "task/taskNew";
    }

    @PostMapping("/add")
    public String saveTask(String title,
                           String finishTime,
                           @RequestParam(required = false,defaultValue = "") String remindTime,
                           Model model,HttpSession session,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String Email,
                           @RequestParam(required = false) String weixin) {

        Account account = ShiroUtil.getCurretnAccount();

        System.out.println(">>>>>>>"+email+"<<<<<<<<<<<<<");
        Map<String,String> type = new HashMap<>();
        type.put("email",email);
        type.put("Email",Email);
        type.put("weixin",weixin);
        publicSave(title,finishTime,remindTime,account,type);

        return "redirect:/task/my";
    }


    @PostMapping("/delete/{id:\\d+}")
    @ResponseBody
    public AjaxStateJson delete(@PathVariable Integer id,
                                HttpSession session) {
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        Task task = taskService.findByTaskId(id);

        if(task == null){
            throw new ServiceException("Not found this task");
        }
        if(!task.getAid().equals(account.getId())){
            throw  new Status403Exception("无操作权限");
        }

        taskService.deleteTask(task);

        return new AjaxStateJson("success","删除提醒成功!");
    }

    @GetMapping("/toEdit")
    public String toEdit(Model model,String taskId) {
        Task task = taskService.findByTaskId(Integer.parseInt(taskId));
        model.addAttribute("task",task);

        return "task/taskEdit";
    }


    @PostMapping("/edit")
    public String edit(String title,
                       Integer oldId,
                       String finishTime,
                       @RequestParam(required = false,defaultValue = "") String remindTime,
                       Model model,HttpSession session,
                       @RequestParam(required = false) String email,
                       @RequestParam(required = false) String Email,
                       @RequestParam(required = false) String weixin) {
        //删除
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        Task task = taskService.findByTaskId(oldId);

        if(task == null){
            throw new ServiceException("Not found this task");
        }
        if(!task.getAid().equals(account.getId())){
            throw  new Status403Exception("无操作权限");
        }
        taskService.deleteTask(task);

        Map<String,String> type = new HashMap<>();
        type.put("email",email);
        type.put("Email",Email);
        type.put("weixin",weixin);
        //新增
        publicSave(title,finishTime,remindTime,account,type);

        return "redirect:/task/my";
    }

    @GetMapping("/toOtherAdd")
    public String toOtherAdd(@RequestParam(required = false) Integer cid,
                             @RequestParam(required = false) Integer sid,
                             Model model){
        if(cid != null){
            model.addAttribute("cid",cid);
        }
        if(sid != null){
            model.addAttribute("sid",sid);
        }
        return "task/taskOtherNew";
    }

    @PostMapping("/otherAdd")
    public String customerOrRecordSaveTask(String title,
                                                  String finishTime,
                                                  @RequestParam(required = false,defaultValue = "") String remindTime,
                                                  @RequestParam(required = false) Integer cid,
                                                  @RequestParam(required = false) Integer sid,
                                                  HttpSession session,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String Email,
                                           @RequestParam(required = false) String weixin) {
        Account account = ShiroUtil.getCurretnAccount();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date finish = sdf.parse(finishTime);
            Task t = new Task();

            t.setTitle(title);
            t.setFinishTime(finish);
            if(cid != null){
                t.setCid(cid);
            }
            if(sid != null){
                t.setSid(sid);
            }
            if(StringUtils.isNotEmpty(remindTime)){
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date remind = sdf2.parse(remindTime);
                t.setRemindTime(remind);
            }

            //拓展提醒方式
            Map<String,String> type = new HashMap<>();
            type.put("email",email);
            type.put("Email",Email);
            type.put("weixin",weixin);

            taskService.saveTask(t,account,type);

        } catch (ParseException e) {
            throw new ServiceException("日期格式异常");
        }

        return "redirect:/task/my";
    }



    @PostMapping("/done")
    @ResponseBody
    public AjaxStateJson done(Integer taskId,
                              HttpSession session) {
        //Account account = (Account) session.getAttribute("account");
        Account account = ShiroUtil.getCurretnAccount();
        Task task = taskService.findByTaskId(taskId);

        if(task == null){
            throw new ServiceException("Not found this task");
        }
        if(!task.getAid().equals(account.getId())){
            throw  new Status403Exception("无操作权限");
        }

        taskService.doneTask(task);

        return new AjaxStateJson("success","更新完成");
    }




    public void publicSave(String title,
                           String finishTime,
                           String remindTime,
                           Account account,
                           Map<String,String> type){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date finish = sdf.parse(finishTime);
            Task t = new Task();

            t.setTitle(title);
            t.setFinishTime(finish);
            if(StringUtils.isNotEmpty(remindTime)){
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date remind = sdf2.parse(remindTime);
                t.setRemindTime(remind);
            }


            taskService.saveTask(t,account,type);

        } catch (ParseException e) {
            throw new ServiceException("日期格式异常");
        }
    }

}
