package de.ulfbiallas.imagemanager.service;

import java.io.IOException;

public interface ImageHashService {

    public String hashImageData(byte[] imageData) throws IOException;

}
