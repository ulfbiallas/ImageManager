package de.ulfbiallas.imagemanager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.ulfbiallas.imagemanager.service.TaskService;



@Controller
public class TaskResource {

    @Autowired
    private TaskService taskService;

    @ResponseBody
    @RequestMapping(
        value="/tasks",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public int getTasks() {
        return taskService.getNumberOfTasks();
    }

}
