package de.ulfbiallas.imagemanager.service;

import de.ulfbiallas.imagemanager.entity.Image;

public interface ImageResizeService {

    void createCopiesWithDifferentSizes(Image image);

}
