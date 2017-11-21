package com.ma.mapper;

import com.ma.entity.Account_Dept;
import com.ma.example.AccountExample;
import java.util.List;

import com.ma.example.Account_DeptExample;
import org.apache.ibatis.annotations.Param;

public interface Account_DeptMapper {
    long countByExample(Account_DeptExample example);

    int deleteByExample(Account_DeptExample example);

    int insert(Account_Dept record);

    int insertSelective(Account_Dept record);

    List<Account_Dept> selectByExample(Account_DeptExample example);

    int updateByExampleSelective(@Param("record") Account_Dept record, @Param("example") Account_DeptExample example);

    int updateByExample(@Param("record") Account_Dept record, @Param("example") Account_DeptExample example);
}