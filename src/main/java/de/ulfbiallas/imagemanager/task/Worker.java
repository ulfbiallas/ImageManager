package de.ulfbiallas.imagemanager.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.service.TaskService;

@Component
public class Worker extends Thread {

    final static Logger logger = LoggerFactory.getLogger(Worker.class);

    private TaskService taskService;

    @Autowired(required = true)
    public Worker(TaskService taskService) {
        this.taskService = taskService;
        start();
    }

    @Override
    public void run() {
        while(true) {

            ImageTask task = taskService.getNextTask();
            if(task != null) {
                logger.info("execute task: " + task.getImageId());
                try {
                    task.call();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }

        }
    }

}
