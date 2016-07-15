package com.yawei;

import com.yawei.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

public class Test {
    public static void main(String[] args) {
        UserService userService = new UserService();
        System.out.println(userService.findUserById(2));
    }
}
