package de.ulfbiallas.imagemanager.service;

import de.ulfbiallas.imagemanager.task.ImageResizeTask;

public interface TaskService {

    int getNumberOfTasks();

    void create(ImageResizeTask task);

    ImageResizeTask getNextTask();

}
