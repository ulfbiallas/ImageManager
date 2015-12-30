package de.ulfbiallas.imagemanager.service;

import java.io.IOException;

public interface ImageService {

    public void saveFile(byte[] data, String name) throws IOException;

    public Iterable<de.ulfbiallas.imagemanager.repository.Image> getAll();

}
