package com.yawei.mapper;

import com.yawei.pojo.User;
import com.yawei.pojo.UserLog;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User findByUsername(String username);
    void updateUser(User user);

}
