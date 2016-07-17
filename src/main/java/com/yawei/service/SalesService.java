package com.yawei.service;

import com.yawei.mapper.CustomerMapper;
import com.yawei.mapper.SalesMapper;
import com.yawei.mapper.UserMapper;
import com.yawei.pojo.Customer;
import com.yawei.pojo.Sales;
import com.yawei.pojo.User;
import com.yawei.util.ShiroUtil;
import com.yawei.util.Strings;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class SalesService {
    @Inject
    private SalesMapper salesMapper;
    @Inject
    private UserMapper userMapper;
    @Inject
    private CustomerMapper customerMapper;

    /**
     * 查找所有销售机会
     *
     * @return
     */
    public List<Sales> findAllSales() {
        return salesMapper.findAll();
    }

    /**
     * 根据参数获取销售信息
     *
     * @param params
     * @return
     */
    public List<Sales> findSalesByParam(Map<String, Object> params) {
        if (ShiroUtil.isEmployee()) {
            params.put("userid", ShiroUtil.getCurrentUserId());
        }
        return salesMapper.findSalesByParam(params);
    }

    /**
     * 获取销售机会的总数量
     *
     * @return
     */
    public Long count() {
        return salesMapper.count();
    }

    /**
     * 根据参数筛选出销售机会的数量
     *
     * @param params
     * @return
     */
    public Long findCountByParam(Map<String, Object> params) {
        return salesMapper.findCountByParam(params);
    }

    /**
     * 新增销售机会
     *
     * @param sales
     */
    public void saveSales(Sales sales) {

        Integer custid = sales.getCustid();
        Customer customer = customerMapper.findById(custid);

        sales.setCustname(customer.getName());

        sales.setUserid(ShiroUtil.getCurrentUserId());
        sales.setUsername(ShiroUtil.getCurrentUserName());
        salesMapper.save(sales);
    }

    /**
     * 查找所有的用户
     *
     * @return
     */
    public List<User> findAllUser() {
        return userMapper.findAll();
    }

    /**
     * 通过id查找销售机会
     *
     * @param id
     * @return
     */
    public Sales findSalesById(Integer id) {
        return salesMapper.findById(id);
    }

    /**
     * 查找所有的客户
     *
     * @return
     */
    public List<Customer> findAllCustomer() {
        return customerMapper.findAll();
    }

    /**
     * 修改销售机会信息
     *
     * @param sales
     */
    public void editSales(Sales sales) {
        Integer salesid = sales.getId();
        sales = salesMapper.findById(salesid);

        salesMapper.update(sales);
    }

    /**
     * 删除销售机会
     *
     * @param id
     */
    public void delSales(Integer id) {
        Sales sales = salesMapper.findById(id);
        if (sales != null) {
            if (sales.getUserid() != null&&sales.getCustid()!=null) {
                sales.setCustname(null);
                sales.setUsername(null);
                salesMapper.update(sales);
            }
        }
        //TODO 删除所关联的项目及待办事项
        salesMapper.delete(id);
    }
}
