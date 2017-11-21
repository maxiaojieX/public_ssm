package com.ma.mapper;

import com.ma.entity.CustomerSaleChance;
import com.ma.example.CustomerSaleChanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerSaleChanceMapper {
    long countByExample(CustomerSaleChanceExample example);

    int deleteByExample(CustomerSaleChanceExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(CustomerSaleChance record);

    int insertSelective(CustomerSaleChance record);

    List<CustomerSaleChance> selectByExample(CustomerSaleChanceExample example);

    CustomerSaleChance selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("record") CustomerSaleChance record, @Param("example") CustomerSaleChanceExample example);

    int updateByExample(@Param("record") CustomerSaleChance record, @Param("example") CustomerSaleChanceExample example);

    int updateByPrimaryKeySelective(CustomerSaleChance record);

    int updateByPrimaryKey(CustomerSaleChance record);
}