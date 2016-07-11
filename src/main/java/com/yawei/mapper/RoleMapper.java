package com.yawei.mapper;


import com.yawei.pojo.Role;

import java.util.List;

public interface RoleMapper {
    Role findById(Integer id);

    List<Role> findAll();
}
