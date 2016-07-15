package com.yawei.service;

import com.yawei.mapper.CustomerMapper;
import com.yawei.pojo.Customer;
import com.yawei.util.ShiroUtil;
import com.yawei.util.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class CustomerService {

    @Inject
    private CustomerMapper customerMapper;

    /**
     * 查找公司
     * @return
     */
    public List<Customer> findAllCompany() {
        return customerMapper.findByType(Customer.TYPE_COMPANY);
    }

    /**
     * 根据参数条件查询客户信息
     * @param params
     * @return
     */
    public List<Customer> findCustomerByParams(Map<String, Object> params) {
        if(ShiroUtil.isEmployee()){
            params.put("userid",ShiroUtil.getCurrentUserId());
        }
        return customerMapper.findByparam(params);
    }

    /**
     * 获取客户的总数量
     * @return
     */
    public Long count() {
        return customerMapper.count();
    }

    /**
     * 根据参数查询获取筛选后的客户数量
     * @param params
     * @return
     */
    public Long countByparam(Map<String,Object> params) {

        if(ShiroUtil.isEmployee()){
            params.put("userid",ShiroUtil.getCurrentUserId());
        }
        return customerMapper.findCountByParam(params);
    }

    /**
     * 新增客户
     * @param customer
     */
    public void saveCustomer(Customer customer) {
        if(customer.getCompanyid()!=null){
           Customer company=customerMapper.findById(customer.getCompanyid());
            customer.setCompany(company.getName());
        }
        customer.setUserid(ShiroUtil.getCurrentUserId());
        customer.setPinyin(Strings.toPinyin(customer.getName()));
        customerMapper.save(customer);
    }

    /**
     * 删除客户
     * @param id
     */
    @Transactional
    public void delCustomer(Integer id) {
        Customer customer = customerMapper.findById(id);
        if(customer!=null){
            if (customer.getType().equals(Customer.TYPE_COMPANY)) {
                List<Customer> customerList = customerMapper.findByCompanyid(id);
                for (Customer customer1 :customerList){
                    customer1.setCompany(null);
                    customer1.setCompanyid(null);
                    customerMapper.update(customer1);
                }
            }
            //TODO 删除所关联的项目及待办事项
            customerMapper.del(id);
        }
    }

    /**
     * 修改客户信息
     * @param customer
     */
    @Transactional
    public void editCustomer(Customer customer) {
        if (customer.getType().equals(Customer.TYPE_COMPANY)){
            //找到此公司所关联的客户，并修改客户所关联的公司信息
            List<Customer> customerList = customerMapper.findByCompanyid(customer.getId());
            for(Customer customer1:customerList){
                customer1.setCompanyid(customer.getId());
                customer1.setCompany(customer.getCompany());
                customerMapper.update(customer1);
            }
        }else {
            if(customer.getCompanyid()!=null){
                Customer company = customerMapper.findById(customer.getCompanyid());
                customer.setCompany(company.getName());
            }
        }
        customer.setPinyin(Strings.toPinyin(customer.getName()));
        customerMapper.update(customer);
    }


    /**
     * 根据客户ID查找客户信息
     * @param id
     * @return
     */
    public Customer findCustomerById(Integer id) {
        return customerMapper.findById(id);
    }

    /**
     * 根据companyid查找客户信息
     * @param id
     * @return
     */
    public List<Customer> findCustomerByCompanyId(Integer id) {
        return customerMapper.findCustomerByCompanyid(id);
    }

    /**
     * 公开客户
     * @param customer
     */
    public void openCustomer(Customer customer) {
        customer.setUserid(null);
        customerMapper.update(customer);
    }

    /**
     * 转移客户
     * @param customer
     * @param userid
     */
    public void moveCustomer(Customer customer, Integer userid) {
        customer.setUserid(userid);
        customerMapper.update(customer);
    }

    /**
     * 将客户信息转换成二维码
     * @param id
     * @return
     */
    public String toCard(Integer id) {
        Customer customer = customerMapper.findById(id);
        StringBuilder mecard = new StringBuilder("MECARD:");
        if(StringUtils.isNotEmpty(customer.getName())) {
            mecard.append("N:"+customer.getName()+";");
        }
        if(StringUtils.isNotEmpty(customer.getTel())) {
            mecard.append("TEL:"+customer.getTel()+";");
        }
        if(StringUtils.isNotEmpty(customer.getEmail())) {
            mecard.append("EMAIL:"+customer.getEmail()+";");
        }
        if(StringUtils.isNotEmpty(customer.getAddress())) {
            mecard.append("ADR:"+customer.getAddress()+";");
        }
        if(StringUtils.isNotEmpty(customer.getCompany())) {
            mecard.append("ORG:"+customer.getCompany()+";");
        }
        mecard.append(";");

        return mecard.toString();
    }
}
