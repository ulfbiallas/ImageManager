package de.ulfbiallas.imagemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.entity.Image;
import de.ulfbiallas.imagemanager.entity.ImageMetaData;
import de.ulfbiallas.imagemanager.repository.ImageMetaDataRepository;

@Component
public class ImageMetaDataServiceImpl implements ImageMetaDataService {

    @Autowired
    private ImageMetaDataRepository imageMetaDataRepository;

    @Override
    public ImageMetaData getByImage(Image image) {
        return imageMetaDataRepository.findOne(image.getId());
    }

    @Override
    public void updateMetaData(ImageMetaData imageMetaData) {
        imageMetaDataRepository.save(imageMetaData);
    }

    @Override
    public ImageMetaData getById(String id) {
        return imageMetaDataRepository.findOne(id);
    }

}
