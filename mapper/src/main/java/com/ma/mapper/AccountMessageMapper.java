package com.ma.mapper;

import com.ma.entity.AccountMessage;
import com.ma.example.AccountMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountMessageMapper {
    long countByExample(AccountMessageExample example);

    int deleteByExample(AccountMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountMessage record);

    int insertSelective(AccountMessage record);

    List<AccountMessage> selectByExample(AccountMessageExample example);

    AccountMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountMessage record, @Param("example") AccountMessageExample example);

    int updateByExample(@Param("record") AccountMessage record, @Param("example") AccountMessageExample example);

    int updateByPrimaryKeySelective(AccountMessage record);

    int updateByPrimaryKey(AccountMessage record);
}