package com.yawei.mapper;

import com.yawei.pojo.User;

public interface UserMapper {
    User findByUsername(String username);
    void updateUser(User user);
}
