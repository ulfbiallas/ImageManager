package de.ulfbiallas.imagemanager.task;

import de.ulfbiallas.imagemanager.entity.Image;
import de.ulfbiallas.imagemanager.repository.ImageRepository;
import de.ulfbiallas.imagemanager.service.ImageResizeService;

public class ImageResizeTask implements ImageTask {

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
        System.out.println("process ImageResizeTask on " + getImageId());

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
