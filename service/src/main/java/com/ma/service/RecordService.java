package com.ma.service;

import com.github.pagehelper.PageInfo;
import com.ma.entity.Account;
import com.ma.entity.GenjinRecord;
import com.ma.entity.SaleChance;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/13 0013.
 */
public interface RecordService {
    PageInfo<SaleChance> findAll(Integer accountId,Integer pageNum);

    int saveChance(SaleChance saleChance, Account account);

    SaleChance findByRecordId(Integer recordId);

    List<GenjinRecord> findGenjinRecordByChanceId(Integer ChanceId);

    void updateProcess(String process,Integer chanceId);

    void deleteByChanceId(Integer chanceId);

    void saveGenjin(GenjinRecord genjinRecord);

    List<SaleChance> findByCustomerId(Integer customerId);

    List<Map<String,Object>> findProcessToEcharts();
}
