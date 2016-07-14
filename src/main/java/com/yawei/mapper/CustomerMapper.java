package com.yawei.mapper;


import com.yawei.pojo.Customer;
import java.util.List;

public interface CustomerMapper {
    void save(Customer customer);
    Customer findById(Integer id);
    List<Customer> findByCompanyid(Integer companyid);

    List<Customer> findAll();
}
