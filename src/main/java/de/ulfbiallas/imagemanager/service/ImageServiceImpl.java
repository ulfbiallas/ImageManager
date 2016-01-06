package de.ulfbiallas.imagemanager.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.body.ImageResponse;
import de.ulfbiallas.imagemanager.entity.Image;
import de.ulfbiallas.imagemanager.entity.ImageMetaData;
import de.ulfbiallas.imagemanager.repository.ImageMetaDataRepository;
import de.ulfbiallas.imagemanager.repository.ImageRepository;
import de.ulfbiallas.imagemanager.task.ImageResizeTask;
import de.ulfbiallas.imagemanager.task.IndexTask;

@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageHashService imageHashService;

    @Autowired
    private ImageResizeService imageResizeService;

    @Autowired
    private ImageMetaDataRepository imageMetaDataRepository;

    @Autowired
    private NodeClientService nodeClientService;

    @Autowired
    private TaskService taskService;



    @Override
    public Image saveFile(byte[] imageData, String originalFilename) throws IOException {
        System.out.println("receiving file: " + originalFilename);

        String id = UUID.randomUUID().toString();
        String hash = imageHashService.hashImageData(imageData);

        ImageMetaData image = new ImageMetaData();
        image.setId(id);
        image.setOriginalFilename(originalFilename);
        image.setHash(hash);
        image.setFilename(createFileName(originalFilename, id));
        image.setFinished(false);

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("uploads/"+image.getFilename()));
        stream.write(imageData);
        stream.close();

        ImageMetaData savedImage = imageMetaDataRepository.save(image);

        taskService.create(new ImageResizeTask(id, imageRepository, imageResizeService));
        taskService.create(new IndexTask(id, imageMetaDataRepository, nodeClientService));

        System.out.println("image saved.\n");
        return savedImage;
    }

    private String createFileName(String originalFilename, String id) {
        String[] components = originalFilename.split("\\.");
        if(components.length>1) {
            return id+"_original."+components[components.length-1];
        } else {
            return id+"_original.unknown";
        }
    }

    @Override
    public List<ImageResponse> getAll() {
        return createResponseBodies(imageRepository.findAll());
    }

    @Override
    public Image getById(String id) {
        return imageRepository.findOne(id);
    }

    @Override
    public List<ImageResponse> searchFor(String query) {
        return createResponseBodies(imageRepository.searchFor(query));
    }

    private List<ImageResponse> createResponseBodies(Iterable<Image> images) {
        String baseUrl = "http://localhost:8080/api/images/";
        List<ImageResponse> imageResponses = new ArrayList<ImageResponse>();

        for(Image image : images) {
            Map<String, String> sizes = new HashMap<String, String>();
            sizes.put("original", baseUrl+image.getFilename());
            sizes.put("mini", baseUrl+image.getId()+"_mini.jpg");
            sizes.put("thumbnail", baseUrl+image.getId()+"_thumbnail.jpg");
            sizes.put("small", baseUrl+image.getId()+"_small.jpg");
            sizes.put("medium", baseUrl+image.getId()+"_medium.jpg");
            sizes.put("large", baseUrl+image.getId()+"_large.jpg");

            ImageResponse imageResponse = new ImageResponse();
            imageResponse.setId(image.getId());
            imageResponse.setOriginalFilename(image.getOriginalFilename());
            imageResponse.setSizes(sizes);

            imageResponses.add(imageResponse);
        }

        return imageResponses;
    }

}
