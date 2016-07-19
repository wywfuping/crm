package com.yawei.controller;

import com.yawei.dto.JSONResult;
import com.yawei.pojo.Task;
import com.yawei.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Inject
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
        List<Task> timeoutTaskList=taskService.findTimeOutTasks();
        model.addAttribute("timeoutTaskList",timeoutTaskList);
        return "task/list";
    }

    /**
     * 新增待办事项
     * @param task
     * @param hour
     * @param min
     * @return
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public JSONResult saveTask(Task task, String hour, String min){
        taskService.saveTask(task,hour,min);
        return new JSONResult(task);
    }
}
