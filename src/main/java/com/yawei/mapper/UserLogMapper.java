package com.yawei.mapper;


import com.yawei.pojo.UserLog;

import java.util.List;
import java.util.Map;

public interface UserLogMapper {
    void save(UserLog userLog);
    List<UserLog> findByParam(Map<String, Object> param);

    Long countByParam(Map<String, Object> param);
}
