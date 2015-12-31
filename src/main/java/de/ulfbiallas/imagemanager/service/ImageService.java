package de.ulfbiallas.imagemanager.service;

import java.io.IOException;

import de.ulfbiallas.imagemanager.repository.Image;

public interface ImageService {

    public void saveFile(byte[] data, String name) throws IOException;

    public Iterable<Image> getAll();

    public Image getByFilename(String filename);

    public Iterable<Image> searchFor(String query);

}
