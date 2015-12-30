package de.ulfbiallas.imagemanager.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.repository.Image;
import de.ulfbiallas.imagemanager.repository.ImageRepository;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageHashService imageHashService;

    @Override
    public void saveFile(byte[] imageData, String originalFilename) throws IOException {
        String id = UUID.randomUUID().toString();
        String hash = imageHashService.hashImageData(imageData);

        Image image = new Image();
        image.setId(id);
        image.setOriginalFilename(originalFilename);
        image.setHash(hash);
        image.setFilename(createFileName(originalFilename, id));

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("uploads/"+image.getFilename()));
        stream.write(imageData);
        stream.close();

        imageRepository.save(image);
    }

    private String createFileName(String originalFilename, String id) {
        String[] components = originalFilename.split("\\.");
        if(components.length>1) {
            return id+"."+components[components.length-1];
        } else {
            return id+".unknown";
        }
    }

}
