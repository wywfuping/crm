package com.yawei.controller;

import com.yawei.pojo.Task;
import com.yawei.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
