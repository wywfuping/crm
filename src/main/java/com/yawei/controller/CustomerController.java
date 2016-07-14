package com.yawei.controller;

import com.yawei.dto.DataTablesResult;
import com.yawei.pojo.Customer;
import com.yawei.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Inject
    private CustomerService customerService;

    /**
     * 显示所有客户的列表
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showList(Model model){
        model.addAttribute("customerList",customerService.findAllcustomer());
        return "customer/list";
    }


    /**
     * 通过Ajax获取用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/list/load",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Customer> loadCustomer(HttpServletRequest request){
        
    }

}
