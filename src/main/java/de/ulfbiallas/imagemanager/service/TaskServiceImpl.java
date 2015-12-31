package de.ulfbiallas.imagemanager.service;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.task.ImageResizeTask;

@Component
public class TaskServiceImpl implements TaskService {

    private AbstractQueue<ImageResizeTask> tasks = new LinkedBlockingQueue<ImageResizeTask>();

    @Override
    public int getNumberOfTasks() {
        return tasks.size();
    }

    @Override
    public void create(ImageResizeTask task) {
        tasks.add(task);
    }

    @Override
    public ImageResizeTask getNextTask() {
        return tasks.poll();
    }

}
