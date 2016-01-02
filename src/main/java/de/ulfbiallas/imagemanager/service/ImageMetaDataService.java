package de.ulfbiallas.imagemanager.service;

import de.ulfbiallas.imagemanager.entity.Image;
import de.ulfbiallas.imagemanager.entity.ImageMetaData;

public interface ImageMetaDataService {

    public ImageMetaData getByImage(Image image);

    public void updateMetaData(ImageMetaData imageMetaData);

    public ImageMetaData getById(String id);

}
