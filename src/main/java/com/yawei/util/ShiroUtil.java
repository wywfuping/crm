package com.yawei.util;
import com.yawei.pojo.User;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    public static User getCurrentUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();//获得user对象
    }
    public static Integer getCurrentUserId(){
        return getCurrentUser().getId();
    }
    public static String getCurrentUserName(){
        return getCurrentUser().getUsername();
    }


}
