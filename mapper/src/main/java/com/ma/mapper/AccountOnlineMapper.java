package com.ma.mapper;

import com.ma.entity.AccountOnline;
import com.ma.example.AccountOnlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountOnlineMapper {
    long countByExample(AccountOnlineExample example);

    int deleteByExample(AccountOnlineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountOnline record);

    int insertSelective(AccountOnline record);

    List<AccountOnline> selectByExample(AccountOnlineExample example);

    AccountOnline selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountOnline record, @Param("example") AccountOnlineExample example);

    int updateByExample(@Param("record") AccountOnline record, @Param("example") AccountOnlineExample example);

    int updateByPrimaryKeySelective(AccountOnline record);

    int updateByPrimaryKey(AccountOnline record);

    void saveOnline(@Param("aid") Integer aid);
}