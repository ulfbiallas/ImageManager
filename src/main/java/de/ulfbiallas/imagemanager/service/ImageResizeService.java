package de.ulfbiallas.imagemanager.service;

import de.ulfbiallas.imagemanager.repository.Image;

public interface ImageResizeService {

    void createCopiesWithDifferentSizes(Image image);

}
