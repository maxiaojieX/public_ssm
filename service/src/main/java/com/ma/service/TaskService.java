package com.ma.service;

import com.github.pagehelper.PageInfo;
import com.ma.entity.Account;
import com.ma.entity.Task;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/14 0014.
 */
public interface TaskService {

    void saveTask(Task task, Account account,Map<String,String> type);

    PageInfo<Task> findAllByAccountIdWithCNameAndSName(Integer accountId,Integer pageNum);

    Task findByTaskId(Integer id);

    void deleteTask(Task task);

    void doneTask(Task task);
}
