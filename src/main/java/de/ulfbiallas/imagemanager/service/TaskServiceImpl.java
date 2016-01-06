package de.ulfbiallas.imagemanager.service;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.task.ImageTask;

@Component
public class TaskServiceImpl implements TaskService {

    private AbstractQueue<ImageTask> tasks = new LinkedBlockingQueue<ImageTask>();

    @Override
    public int getNumberOfTasks() {
        return tasks.size();
    }

    @Override
    public void create(ImageTask task) {
        tasks.add(task);
    }

    @Override
    public ImageTask getNextTask() {
        return tasks.poll();
    }

}
