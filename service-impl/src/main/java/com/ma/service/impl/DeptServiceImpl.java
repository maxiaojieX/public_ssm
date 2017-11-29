package com.ma.service.impl;

import com.ma.exception.ServiceException;
import com.ma.service.DeptService;
import com.ma.entity.Dept;
import com.ma.example.DeptExample;
import com.ma.mapper.DeptMapper;
import com.ma.weixin.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6 0006.
 */
@Service
public class DeptServiceImpl implements DeptService {

    private static final int PID = 1;

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private WeixinUtil weixinUtil;


    @Override
    public Dept findById(Integer id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Dept> findAll() {
        return deptMapper.selectByExample(new DeptExample());
    }

    /**
     * 保存新增部门
     * @param deptName
     */
    @Override
    @Transactional
    public void saveNewDept(String deptName) throws ServiceException{
        //根据部门名称查找是否已经存在该名称
        DeptExample deptExample = new DeptExample();
        deptExample.createCriteria().andDeptNameEqualTo(deptName);
        List<Dept> deptList = deptMapper.selectByExample(deptExample);

        if(deptList != null && !deptList.isEmpty()){
            throw new ServiceException("该部门名称已存在");
        }

        Dept dept = new Dept();
        dept.setDeptName(deptName);
        dept.setPid(PID);
        deptMapper.insertSelective(dept);

        //同步到企业微信
        weixinUtil.createDept(deptName,PID,dept.getId());

    }

    @Override
    public void deleteDeptById(Integer id) {
        deptMapper.deleteByPrimaryKey(id);
    }
}
