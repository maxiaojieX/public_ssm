package com.ma.mapper;

import com.ma.entity.GenjinRecord;
import com.ma.example.GenjinRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GenjinRecordMapper {
    long countByExample(GenjinRecordExample example);

    int deleteByExample(GenjinRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GenjinRecord record);

    int insertSelective(GenjinRecord record);

    List<GenjinRecord> selectByExample(GenjinRecordExample example);

    GenjinRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GenjinRecord record, @Param("example") GenjinRecordExample example);

    int updateByExample(@Param("record") GenjinRecord record, @Param("example") GenjinRecordExample example);

    int updateByPrimaryKeySelective(GenjinRecord record);

    int updateByPrimaryKey(GenjinRecord record);

}