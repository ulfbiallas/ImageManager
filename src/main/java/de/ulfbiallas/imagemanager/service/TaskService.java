package de.ulfbiallas.imagemanager.service;

import de.ulfbiallas.imagemanager.task.ImageTask;

public interface TaskService {

    int getNumberOfTasks();

    void create(ImageTask task);

    ImageTask getNextTask();

}
