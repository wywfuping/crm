package com.yawei.controller;

import com.yawei.dto.FlshMessage;
import com.yawei.service.UserService;
import com.yawei.util.ServletUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @Inject
    private UserService userService;

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(String username, String password,
                        RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }

        try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
            subject.login(token);

            userService.saveUserLog(ServletUtil.getRemoteIp(request));
            return "redirect:/home";
        }catch (LockedAccountException e){
            redirectAttributes.addFlashAttribute("message",new FlshMessage(FlshMessage.STATE_ERROR,"账号已被禁用"));
        }catch (AuthenticationException exception){
            redirectAttributes.addFlashAttribute("message",new FlshMessage(FlshMessage.STATE_ERROR,"账号或密码错误"));
        }
        return "redirect:/";
    }
}
