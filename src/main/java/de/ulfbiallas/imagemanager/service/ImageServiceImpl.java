package de.ulfbiallas.imagemanager.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.repository.Image;
import de.ulfbiallas.imagemanager.repository.ImageRepository;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void saveFile(byte[] imageData, String originalFilename) throws IOException {
        String id = UUID.randomUUID().toString();
        String hash = calculateHash(imageData);

        Image image = new Image();
        image.setId(id);
        image.setOriginalFilename(originalFilename);
        image.setHash(hash);
        image.setFilename("uploads/"+createFileName(originalFilename, id));

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image.getFilename()));
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

    private String calculateHash(byte[] imageData) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        //TODO: calculate hash from size and first bytes

        return "123";
    }
}
