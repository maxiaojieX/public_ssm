package com.ma.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ma.entity.Account;
import com.ma.entity.Customer;
import com.ma.entity.GenjinRecord;
import com.ma.entity.SaleChance;
import com.ma.example.GenjinRecordExample;
import com.ma.example.SaleChanceExample;
import com.ma.mapper.CustomerMapper;
import com.ma.mapper.GenjinRecordMapper;
import com.ma.mapper.SaleChanceMapper;
import com.ma.service.RecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/13 0013.
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private GenjinRecordMapper genjinRecordMapper;

    /**
     * 根据员工id查找所有的销售机会
     * @param accountId
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<SaleChance> findAll(Integer accountId,Integer pageNum) {
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andAidEqualTo(accountId);

        PageHelper.startPage(pageNum,10);
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);

        return new PageInfo<>(saleChanceList);
    }

    /**
     * 保存新增销售机会
     * @param saleChance
     * @return
     */
    @Override
    @Transactional
    public int saveChance(SaleChance saleChance, Account account) {
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCid());

        //Other字段作为备用字段在这里储存了客户姓名
        saleChance.setOther(customer.getCustomerName());

        saleChance.setAid(account.getId());
        saleChance.setCreateTime(new Date());
        saleChanceMapper.insertSelective(saleChance);

        //更新对应客户的跟进时间
        customer.setLastChatTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

        return saleChance.getId();
    }

    @Override
    public SaleChance findByRecordId(Integer recordId) {
        return saleChanceMapper.selectByPrimaryKey(recordId);
    }

    /**
     * 查询一个销售机会下的所有跟进记录
     * @param chanceId
     * @return
     */
    @Override
    public List<GenjinRecord> findGenjinRecordByChanceId(Integer chanceId) {
        GenjinRecordExample genjinRecordExample = new GenjinRecordExample();
        genjinRecordExample.setOrderByClause("create_time desc");
        genjinRecordExample.createCriteria().andSidEqualTo(chanceId);

        List<GenjinRecord> genjinRecordList = genjinRecordMapper.selectByExample(genjinRecordExample);

        return genjinRecordList;
    }

    /**
     * 更改机会进度
     * @param process
     * @param chanceId
     */
    @Override
    @Transactional
    public void updateProcess(String process, Integer chanceId) {
        //更改销售机会表中的process和跟进时间
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(chanceId);
        saleChance.setProcess(process);
        saleChance.setGenjinTime(new Date());
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);

        //在跟进内容表中插入一条跟进数据
        GenjinRecord genjinRecord = new GenjinRecord();
        genjinRecord.setContent("将进度修改为 [ "+ process+" ]");
        genjinRecord.setSid(saleChance.getId());
        genjinRecord.setCreateTime(new Date());
        genjinRecordMapper.insert(genjinRecord);

        //修改对应客户表中的跟进时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCid());
        customer.setLastChatTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

    }

    @Override
    public void deleteByChanceId(Integer chanceId) {
        //根据chanceId获取chance对象
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(chanceId);

        //根据chanceid删除所有的对应的跟进记录
        GenjinRecordExample genjinRecordExample = new GenjinRecordExample();
        genjinRecordExample.createCriteria().andSidEqualTo(chanceId);
        genjinRecordMapper.deleteByExample(genjinRecordExample);

        //获取chance对应的客户id，并且查询该客户下的所有chance
        int customerId = saleChance.getCid();
        saleChanceMapper.deleteByPrimaryKey(chanceId);
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCidEqualTo(customerId);
        saleChanceExample.setOrderByClause("genjin_time desc");
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);

        //如果saleChancesList为空，那么把该客户的跟进时间设为空
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        if(saleChanceList == null){
            customer.setLastChatTime(null);
        }else{
            //如果不为空，把对应客户跟进时间更改为最新跟进时间
            customer.setLastChatTime(saleChanceList.get(0).getGenjinTime());
        }
        customerMapper.updateByPrimaryKeySelective(customer);

    }

    @Override
    @Transactional
    public void saveGenjin(GenjinRecord genjinRecord) {
        //新增一条跟进数据
        genjinRecord.setCreateTime(new Date());
        genjinRecordMapper.insert(genjinRecord);

        //更改机会表中的最新跟进时间
        int chanceId = genjinRecord.getSid();
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(chanceId);
        saleChance.setGenjinTime(new Date());
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);

        //更新对应客户的最新跟进时间
        int customerId = saleChance.getCid();
        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        customer.setLastChatTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

    }

    /**
     * 根据客户id查找所有的销售机会
     * @param customerId
     * @return
     */
    @Override
    public List<SaleChance> findByCustomerId(Integer customerId) {
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCidEqualTo(customerId);
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
        return saleChanceList;
    }

    @Override
    public List<Map<String, Object>> findProcessToEcharts() {
        return saleChanceMapper.findProcess();
    }
}
