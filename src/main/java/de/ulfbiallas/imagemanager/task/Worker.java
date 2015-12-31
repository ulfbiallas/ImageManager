package de.ulfbiallas.imagemanager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.service.TaskService;

@Component
public class Worker extends Thread {

    private TaskService taskService;

    @Autowired(required = true)
    public Worker(TaskService taskService) {
        this.taskService = taskService;
        start();
    }

    @Override
    public void run() {
        while(true) {

            ImageResizeTask task = taskService.getNextTask();
            if(task != null) {
                System.out.println("execute task: " + task.getImageId());
                try {
                    task.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
