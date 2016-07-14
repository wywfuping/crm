package com.yawei.service;

import com.yawei.mapper.CustomerMapper;
import com.yawei.pojo.Customer;
import com.yawei.util.ShiroUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class CustomerService {

    @Inject
    private CustomerMapper customerMapper;

    /**
     * 查找所有的客户
     * @return
     */
    public List<Customer> findAllcustomer() {
        return customerMapper.findAll();
    }
}
