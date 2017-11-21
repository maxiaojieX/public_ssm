package com.ma.service;

import com.github.pagehelper.PageInfo;
import com.ma.entity.Account;
import com.ma.entity.Customer;
import com.ma.exception.ServiceException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/9 0009.
 */
public interface CustomerService {

    List<Customer> findAll();

    PageInfo<Customer> findAllByAccountId(Integer id,Integer pageNum);

    Customer findByCustomerId(Integer id);

    int saveCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteByCustomerId(Integer customerId);

    void publicCustomer(Customer customer);

    void transToOthers(Customer customer,Integer accountId) throws ServiceException;

    void getPublicCustomer(Integer id ,Integer accountId) throws ServiceException;

    void exportToCsv(OutputStream outputStream, Account account) throws IOException;

    void exportToXls(OutputStream outputStream,Account account)throws IOException;

    List<Map<String,Object>> findEcharts();
}
