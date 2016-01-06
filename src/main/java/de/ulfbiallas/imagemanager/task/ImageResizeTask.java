package de.ulfbiallas.imagemanager.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ulfbiallas.imagemanager.entity.Image;
import de.ulfbiallas.imagemanager.repository.ImageRepository;
import de.ulfbiallas.imagemanager.service.ImageResizeService;

public class ImageResizeTask implements ImageTask {

    final static Logger logger = LoggerFactory.getLogger(ImageResizeTask.class);

    private final String imageId;

    private final ImageRepository imageRepository;

    private final ImageResizeService imageResizeService;

    public ImageResizeTask(String imageId, ImageRepository imageRepository, ImageResizeService imageResizeService) {
        this.imageId = imageId;
        this.imageRepository = imageRepository;
        this.imageResizeService = imageResizeService; 
    }

    @Override
    public Void call() throws Exception {
        logger.info("process ImageResizeTask on " + getImageId());

        Image image = imageRepository.findOne(imageId);

        imageResizeService.createCopiesWithDifferentSizes(image);

        image.setFinished(true);
        imageRepository.save(image);

        return null;
    }

    public String getImageId() {
        return imageId;
    }

}
