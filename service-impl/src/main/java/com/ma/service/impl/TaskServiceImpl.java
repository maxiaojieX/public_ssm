package com.ma.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ma.entity.Account;
import com.ma.entity.Customer;
import com.ma.entity.SaleChance;
import com.ma.entity.Task;
import com.ma.example.TaskExample;
import com.ma.exception.ServiceException;
import com.ma.jobs.MyJob;
import com.ma.mapper.CustomerMapper;
import com.ma.mapper.SaleChanceMapper;
import com.ma.mapper.TaskMapper;
import com.ma.service.TaskService;
import com.ma.weixin.WeixinUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/14 0014.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private WeixinUtil weixinUtil;

    @Override
    @Transactional
    public void saveTask(Task task, Account account, Map<String,String> type) {
            task.setAid(account.getId());
            task.setCreateTime(new Date());

            taskMapper.insert(task);

            //添加调度任务
        if(task.getRemindTime() != null){
            //创建一个Map给job传值
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("message",task.getTitle());
            jobDataMap.put("email",type.get("email"));
            jobDataMap.put("Email",type.get("Email"));

            //根据job类动态创建一个jobDetail
            JobDetail jobDetail = JobBuilder
                    .newJob(MyJob.class)
                    .setJobData(jobDataMap)
                    .withIdentity(new JobKey("taskID:"+task.getId(),"sendMessageGroup"))
                    .build();

            //根据提醒时间拼装出cron表达式
            Date date = task.getRemindTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formatDate = df.format(date);

            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = formatter.parseDateTime(formatDate);

            StringBuilder cron = new StringBuilder("0")
                    .append(" ")
                    .append(dateTime.getMinuteOfHour())
                    .append(" ")
                    .append(dateTime.getHourOfDay())
                    .append(" ")
                    .append(dateTime.getDayOfMonth())
                    .append(" ")
                    .append(dateTime.getMonthOfYear())
                    .append(" ? ")
                    .append(dateTime.getYear());


            //创建Trigger
            ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron.toString());
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            //把创建好的job和trigger交给调度者
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.scheduleJob(jobDetail,trigger);
                scheduler.start();
            } catch (SchedulerException e) {
                throw new ServiceException("添加调度任务异常");
            }

        }
    }


    @Override
    public PageInfo<Task> findAllByAccountIdWithCNameAndSName(Integer accountId, Integer pageNum) {
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAidEqualTo(accountId);
        PageHelper.startPage(pageNum,10);
        List<Task> taskList = taskMapper.selectByExample(taskExample);

        for(Task task : taskList){
            if(task.getCid() != null){
                //如果Cid！=null，查取该Customer并封装进task
                Customer customer = customerMapper.selectByPrimaryKey(task.getId());
                task.setCustomer(customer);
            }
            if(task.getSid() != null){
                //如果Sid！=null，查取该Chance并封装进task
                SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(task.getSid());
                task.setSaleChance(saleChance);
            }
        }
        return new PageInfo<>(taskList);

    }

    @Override
    public Task findByTaskId(Integer id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskMapper.deleteByPrimaryKey(task.getId());

        //删除任务调度
        if(task.getRemindTime() != null){
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskID:"+task.getId(),"sendMessageGroup"));

            } catch (SchedulerException e) {
                throw new ServiceException("任务调度删除异常");
            }
        }
    }

    @Override
    public void doneTask(Task task) {
        task.setDone(1);
        taskMapper.updateByPrimaryKeySelective(task);

        if(task.getRemindTime() != null){
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskID:"+task.getId(),"sendMessageGroup"));

            } catch (SchedulerException e) {
                throw new ServiceException("任务调度删除异常");
            }
        }
    }





}
