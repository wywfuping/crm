package com.yawei.service;

import com.yawei.mapper.TaskMapper;
import com.yawei.pojo.Task;
import com.yawei.util.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskService.class);
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

    /**
     * 新增待办事项
     * @param task
     * @param hour
     * @param min
     */
    public void saveTask(Task task, String hour, String min) {
        if(StringUtils.isNotEmpty(hour) && StringUtils.isNotEmpty(min)){
            String reminderTime=task.getStart()+" "+hour+":"+min+"00";
            logger.debug("提醒时间为：{}",reminderTime);
            task.setRemindertime(reminderTime);
        }
        taskMapper.save(task);
    }
}
