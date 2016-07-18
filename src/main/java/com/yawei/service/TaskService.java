package com.yawei.service;

import com.yawei.mapper.TaskMapper;
import com.yawei.pojo.Task;
import com.yawei.util.ShiroUtil;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class TaskService {
    @Inject
    private TaskMapper taskMapper;

    /**
     * 获取当前用户已经超时的任务
     * @return
     */
    public List<Task> findTimeOutTasks() {
        String today= DateTime.now().toString("YYYY-MM-dd");
        return taskMapper.findTimeOutTask(ShiroUtil.getCurrentUserId(),today);
    }
}
