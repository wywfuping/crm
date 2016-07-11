package com.yawei.controller;

import com.google.common.collect.Maps;
import com.yawei.dto.DataTablesResult;
import com.yawei.dto.JSONResult;
import com.yawei.pojo.Role;
import com.yawei.pojo.User;
import com.yawei.service.UserService;
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
@RequestMapping("/admin")
public class AdminController {
    @Inject
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String showUserList(Model model) {
        List<Role> roleList = userService.findAllRole();
        model.addAttribute("roleList", roleList);
        return "admin/userlist";
    }

    @RequestMapping(value = "/users/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<User> loadUsers(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword = Strings.toUtf8(keyword);

        Map<String, Object> params = Maps.newHashMap();
        params.put("start", start);
        params.put("length", length);
        params.put("keyword", keyword);

        List<User> userList = userService.findUserListByParam(params);
        Long count = userService.findUserCount();
        Long filterCount = userService.findUserCountByParam(params);

        return new DataTablesResult<>(draw, userList, count, filterCount);
    }

    /**
     * 验证用户名是否可用
     *
     * @return
     */
    @RequestMapping(value = "/user/checkusername", method = RequestMethod.GET)
    @ResponseBody
    public String checkUserName(String username) {
        User user = userService.findUserByUsername(username);
        if (user == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 新增用户
     *
     * @return
     */
    @RequestMapping(value = "/users/new", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(User user) {
        userService.saveUser(user);
        return "success";
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/users/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(Integer id) {
        userService.resetUserPassword(id);
        return "success";
    }


    /**
     * 根据Id查找用户
     *
     * @return
     */
    @RequestMapping(value = "/users/{id:\\d+}.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONResult showUser(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return new JSONResult("找不到id为"+id+"的用户");
        }else {
            return new JSONResult(user);
        }
    }

    @RequestMapping(value = "/users/edit",method = RequestMethod.POST)
    @ResponseBody
    public String edutUser(User user){
        userService.editUser(user);
        return "success";
    }
}
