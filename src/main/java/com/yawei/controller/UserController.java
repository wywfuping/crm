package com.yawei.controller;

import com.yawei.dto.DataTablesResult;
import com.yawei.exception.NotFindException;
import com.yawei.pojo.User;
import com.yawei.pojo.UserLog;
import com.yawei.service.UserService;
import com.yawei.util.ShiroUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Inject
    private UserService userService;

    @RequestMapping(value = "/password",method = RequestMethod.GET)
    public String editPassword(){
        return "setting/password";
    }

    @RequestMapping(value = "/password",method = RequestMethod.POST)
    public String editPassword(String password){
        userService.changeUserPassword(password);
        return "success";
    }

    @RequestMapping(value = "/validate/password",method = RequestMethod.GET)
    @ResponseBody
    public String validateOldPassword(@RequestHeader("X-Requested-With") String xRequestedWith, String oldpassword){
        if("XMLHttpRequest".equals(xRequestedWith)){
            User user = ShiroUtil.getCurrentUser();
            if(user.getPassword().equals(DigestUtils.md5Hex(oldpassword))){
                return "true";
            }
            return "false";
        }else {
            throw new NotFindException();
        }
    }

    @RequestMapping(value = "/log",method = RequestMethod.GET)
    public String showUserLog(){
        return "setting/loglist";

    }


    @RequestMapping(value = "/log/load",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult userLogLoad(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        List<UserLog> userLogList = userService.findCurrentUserLog(start,length);
        Long count = userService.findCurrentUserLogCount();
        return new DataTablesResult<>(draw,userLogList,count,count);
    }
}
