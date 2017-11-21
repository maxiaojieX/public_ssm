package com.ma.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ma.entity.Account;
import com.ma.entity.Customer;
import com.ma.example.CustomerExample;
import com.ma.exception.ServiceException;
import com.ma.mapper.AccountMapper;
import com.ma.mapper.CustomerMapper;
import com.ma.service.CustomerService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/9 0009.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public List<Customer> findAll() {
        return customerMapper.selectByExample(new CustomerExample());
    }

    /**
     * 根据员工id查找对应客户
     * @param id
     * @return
     */
    @Override
    public PageInfo<Customer> findAllByAccountId(Integer id,Integer pageNum) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(id);

        PageHelper.startPage(pageNum,10);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        return new PageInfo<>(customerList);
    }

    @Override
    public Customer findByCustomerId(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存新增客户
     * @param customer
     * @return 新增客户id
     */
    @Override
    public int saveCustomer(Customer customer) {
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customerMapper.insertSelective(customer);
        return customer.getId();
    }

    @Override
    public void updateCustomer(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 删除客户
     * @param customerId
     */
    @Override
    public void deleteByCustomerId(Integer customerId) {
        customerMapper.deleteByPrimaryKey(customerId);
    }

    /**
     * 流放客户至公海
     * @param customer
     */
    @Override
    public void publicCustomer(Customer customer) {
        Account account = accountMapper.selectByPrimaryKey(customer.getAccountId());
        customer.setReminder("该用户被" + account.getUsername() + "放置公海;");
        customer.setAccountId(0);
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public void transToOthers(Customer customer,Integer accountId) throws ServiceException{
        Account account = accountMapper.selectByPrimaryKey(accountId);
        if(account == null){
            throw new ServiceException("接收人不存在");
        }
        customer.setAccountId(accountId);
        customer.setReminder("该客户由" + account.getUsername() + "转交给你。 ");
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public void getPublicCustomer(Integer id,Integer accountId) throws ServiceException {
        //判断该客户accountId是否为0
        Customer customer = customerMapper.selectByPrimaryKey(id);

        if(customer == null){
            throw new ServiceException("该客户不存在");
        }

        if(customer.getAccountId() != 0){
            throw new ServiceException("该客户您无权操作");
        }

        //更改该客户accountId
        Account account = accountMapper.selectByPrimaryKey(accountId);
        customer.setAccountId(accountId);
        customer.setReminder(account.getUsername() + "从公海用户中认领了该用户");
        customerMapper.updateByPrimaryKeySelective(customer);

    }

    /**
     * 导出我的客户为csv
     * @param outputStream
     * @param account
     * @throws IOException
     */
    @Override
    public void exportToCsv(OutputStream outputStream, Account account) throws IOException{
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());

        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("id,姓名,联系电话,地址\r\n");

        for(Customer customer : customerList){

            stringBuffer.append(customer.getId())
                    .append(",")
                    .append(customer.getCustomerName())
                    .append(",")
                    .append(customer.getCustomPhone())
                    .append(",")
                    .append(customer.getAddress())
                    .append("\r\n");

        }

        IOUtils.write(stringBuffer.toString(),outputStream,"GBK");

        outputStream.flush();
        outputStream.close();

    }

    @Override
    public void exportToXls(OutputStream outputStream, Account account) throws IOException {

        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());

        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        //创建工作表
        Workbook workbook = new HSSFWorkbook();
        //创建页签
        Sheet sheet = workbook.createSheet("我的客户");
        //创建行
        Row firstRow = sheet.createRow(0);
        //创建第几个单元格并设值
        firstRow.createCell(0).setCellValue("ID");
        firstRow.createCell(1).setCellValue("姓名");
        firstRow.createCell(2).setCellValue("联系电话");
        firstRow.createCell(3).setCellValue("地址");

        for(int i = 0;i<customerList.size();i++){
            Customer customer = customerList.get(i);

            Row row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(customer.getId());
            row.createCell(1).setCellValue(customer.getCustomerName());
            row.createCell(2).setCellValue(customer.getCustomPhone());
            row.createCell(3).setCellValue(customer.getAddress());
        }

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public List<Map<String, Object>> findEcharts() {
        return customerMapper.findEchart();
    }
}
