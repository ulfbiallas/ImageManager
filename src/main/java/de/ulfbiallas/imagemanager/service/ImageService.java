package de.ulfbiallas.imagemanager.service;

import java.io.IOException;
import java.util.List;

import de.ulfbiallas.imagemanager.body.ImageResponse;
import de.ulfbiallas.imagemanager.entity.Image;

public interface ImageService {

    public Image saveFile(byte[] data, String name) throws IOException;

    public List<ImageResponse> getAll();

    public Image getById(String filename);

    public List<ImageResponse> searchFor(String query);

}
