package com.yawei.controller;

import com.google.common.collect.Maps;
import com.yawei.dto.DataTablesResult;
import com.yawei.exception.ForbiddenException;
import com.yawei.exception.NotFindException;
import com.yawei.pojo.Customer;
import com.yawei.pojo.Sales;
import com.yawei.pojo.User;
import com.yawei.service.CustomerService;
import com.yawei.service.SalesService;
import com.yawei.util.ShiroUtil;
import com.yawei.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sales")
public class SalesController {
    @Inject
    private SalesService salesService;
    @Inject
    private CustomerService customerService;


    /**
     * 获取所有销售机会
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showSalesList(Model model){
        model.addAttribute("salesList",salesService.findAllSales());
        return "sales/list";
    }

    /**
     * 获取销售机会信息（Ajax）
     * @param request
     * @return
     */
    @RequestMapping(value = "/load",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Sales> loadSales(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword= Strings.toUtf8(keyword);

        Map<String,Object> params = Maps.newHashMap();
        params.put("start",start);
        params.put("length",length);
        params.put("keyword",keyword);

        List<Sales> salesList = salesService.findSalesByParam(params);
        Long count = salesService.count();
        Long filterCount = salesService.findCountByParam(params);
        return new DataTablesResult<>(draw,salesList,count,filterCount);
    }

    /**
     * 新增销售机会
     * @return
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public String saveSales(Sales sales){
        salesService.saveSales(sales);
        return "success";
    }

    /**
     * 在新增框中显示所有的客户（通过Ajax）
     *
     * @return
     */
    @RequestMapping(value = "/customer.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> showAllCustomer() {
        return salesService.findAllCustomer();
    }


    /**
     * 显示用户所关联的客户信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String coustomerView(@PathVariable Integer id, Model model) {
        Sales sales = salesService.findSalesById(id);
        //判断所要查询的销售机会是否为空
        if (sales == null) {
            throw new NotFindException();
        }
        //判断当前的销售机会是否为公共、及当前的销售机会是否为自己的和判断自己是否是经理
        if (sales.getUserid() != null && !sales.getUserid().equals(ShiroUtil.getCurrentUserId()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }
        model.addAttribute("sales", sales);

        return "sales/view";
    }

    /**
     * 修改销售机会信息
     *
     * @return
     */
    @RequestMapping(value = "/edit/{id:\\d+}.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> editSales(@PathVariable Integer id) {
        Sales sales = salesService.findSalesById(id);
        Map<String, Object> result = Maps.newHashMap();

        if (sales == null) {
            result.put("state", "error");
            result.put("message", "销售机会信息找不到");
        } else {
            List<Customer> customerList = customerService.findAllCustomerBy();
            result.put("state", "success");
            result.put("sales", sales);
            result.put("customerList", customerList);
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(Sales sales) {
        salesService.editSales(sales);
        return "success";
    }


    /**
     * 删除销售机会
     *
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteSales(@PathVariable Integer id) {
        salesService.delSales(id);
        return "success";
    }
}
