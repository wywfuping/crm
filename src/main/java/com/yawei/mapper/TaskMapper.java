package com.yawei.mapper;


import com.yawei.pojo.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
    void save(Task task);
    List<Task> findByUserid(Integer userid);
    Task findById(Integer id);
    void del(Integer id);
    void update(Task task);

    List<Task> findTimeOutTask(@Param("userid") Integer userid, @Param("today") String today);
}
