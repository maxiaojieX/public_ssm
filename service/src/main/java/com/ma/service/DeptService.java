package com.ma.service;

import com.ma.entity.Dept;
import com.ma.exception.ServiceException;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
public interface DeptService {

    Dept findById(Integer id);

    List<Dept> findAll();

    void saveNewDept(String deptName) throws ServiceException;

    void deleteDeptById(Integer id);
}
