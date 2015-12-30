package de.ulfbiallas.imagemanager.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class ImageServiceImpl implements ImageService {

    @Override
    public void saveFile(byte[] data, String name) throws IOException {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("uploads/" + name)));
        stream.write(data);
        stream.close();
    }

}
