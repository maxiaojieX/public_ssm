package com.ma.service.impl;

import com.ma.entity.CustomerSource;
import com.ma.example.CustomerSourceExample;
import com.ma.mapper.CustomerSourceMapper;
import com.ma.service.CustomerSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/10 0010.
 */
@Service
public class CustomerSourceServiceImpl implements CustomerSourceService {
    @Autowired
    private CustomerSourceMapper customerSourceMapper;
    @Override
    public List<CustomerSource> findAll() {
        return customerSourceMapper.selectByExample(new CustomerSourceExample());
    }
}
