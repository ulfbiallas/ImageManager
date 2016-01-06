package de.ulfbiallas.imagemanager.task;

import java.util.concurrent.Callable;

public interface ImageTask extends Callable<Void> {

    String getImageId();

}
