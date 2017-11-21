package com.ma.mapper;

import com.ma.entity.CustomerSource;
import com.ma.example.CustomerSourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerSourceMapper {
    long countByExample(CustomerSourceExample example);

    int deleteByExample(CustomerSourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerSource record);

    int insertSelective(CustomerSource record);

    List<CustomerSource> selectByExample(CustomerSourceExample example);

    CustomerSource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CustomerSource record, @Param("example") CustomerSourceExample example);

    int updateByExample(@Param("record") CustomerSource record, @Param("example") CustomerSourceExample example);

    int updateByPrimaryKeySelective(CustomerSource record);

    int updateByPrimaryKey(CustomerSource record);
}