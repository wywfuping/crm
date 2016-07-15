package com.yawei.controller;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yawei.dto.DataTablesResult;
import com.yawei.exception.ForbiddenException;
import com.yawei.exception.NotFindException;
import com.yawei.pojo.Customer;
import com.yawei.pojo.User;
import com.yawei.service.CustomerService;
import com.yawei.service.UserService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Inject
    private CustomerService customerService;
    @Inject
    private UserService userService;

    /**
     * 显示公司的列表
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showCompanyList(Model model) {
        model.addAttribute("companyList", customerService.findAllCompany());
        return "customer/list";
    }

    /**
     * 通过Ajax获取用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Customer> loadCustomer(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");

        keyword = Strings.toUtf8(keyword);

        Map<String, Object> params = Maps.newHashMap();
        params.put("start", start);
        params.put("length", length);
        params.put("keyword", keyword);

        List<Customer> customerList = customerService.findCustomerByParams(params);
        Long count = customerService.count();
        Long filterCount = customerService.countByparam(params);

        return new DataTablesResult<>(draw, customerList, count, filterCount);
    }

    /**
     * 新增客户
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String saveCustomer(Customer customer) {
        customerService.saveCustomer(customer);
        return "success";
    }

    /**
     * 删除客户
     *
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.delCustomer(id);
        return "success";
    }

    /**
     * 在新增框中显示所有的公司（通过Ajax）
     *
     * @return
     */
    @RequestMapping(value = "/company.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> showAllCompany() {
        return customerService.findAllCompany();
    }

    /**
     * 修改客户信息
     *
     * @return
     */
    @RequestMapping(value = "/edit/{id:\\d+}.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> editCustomer(@PathVariable Integer id) {
        Customer customer = customerService.findCustomerById(id);
        Map<String, Object> result = Maps.newHashMap();

        if (customer == null) {
            result.put("state", "error");
            result.put("message", "客户信息找不到");
        } else {
            List<Customer> companyList = customerService.findAllCompany();
            result.put("state", "success");
            result.put("customer", customer);
            result.put("companyList", companyList);
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String edit(Customer customer) {
        customerService.editCustomer(customer);
        return "success";
    }


    /**
     * 显示公司客户所关联的个人客户信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String coustomerView(@PathVariable Integer id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        //判断所要查询的客户是否为空
        if (customer == null) {
            throw new NotFindException();
        }
        //判断当前的客户是否为公共的客户、及当前客户是否为自己的客户和判断自己是否是经理
        if (customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserId()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }
        model.addAttribute("customer", customer);
        if (customer.getType().equals(Customer.TYPE_COMPANY)) {
            //根据CompanyId查找所有关联的客户信息
            List<Customer> customerList = customerService.findCustomerByCompanyId(id);
            model.addAttribute("customerList", customerList);
        }
        //加载所有的员工
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "customer/view";
    }


    /**
     * 公开客户
     *
     * @return
     */
    @RequestMapping(value = "/open/{id:\\d+}", method = RequestMethod.GET)
    public String openCustomer(@PathVariable Integer id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            throw new NotFindException();
        }
        //判断当前的客户是否为公共的客户、及当前客户是否为自己的客户和判断自己是否是经理
        if (customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserId()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }
        customerService.openCustomer(customer);
        return "redirect:/customer/" + id;
    }

    /**
     * 转移客户
     *
     * @param id
     * @param userid
     * @return
     */
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public String moveCustomer(Integer id, Integer userid) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            throw new NotFindException();
        }
        //判断当前的客户是否为公共的客户、及当前客户是否为自己的客户和判断自己是否是经理
        if (customer.getUserid() != null && !customer.getUserid().equals(ShiroUtil.getCurrentUserId()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }
        customerService.moveCustomer(customer, userid);
        return "redirect:/customer";
    }

    /**
     * 将客户的信息生成二维码（电子名片）
     *
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = "/qrcode/{id:\\d+}.png", method = RequestMethod.GET)
    public void toCard(@PathVariable Integer id, HttpServletResponse response) throws WriterException, IOException {
        String toCard = customerService.toCard(id);
        Map<EncodeHintType, String> encode = Maps.newHashMap();
        encode.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(toCard, BarcodeFormat.QR_CODE, 200, 200, encode);
        OutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
