package de.ulfbiallas.imagemanager.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.client.Client;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.ulfbiallas.imagemanager.entity.ImageMetaData;
import de.ulfbiallas.imagemanager.entity.SearchIndexDocument;
import de.ulfbiallas.imagemanager.entity.Tag;
import de.ulfbiallas.imagemanager.repository.ImageMetaDataRepository;
import de.ulfbiallas.imagemanager.service.NodeClientService;

public class IndexTask implements ImageTask {

    private ImageMetaDataRepository imageMetaDataRepository;

    private NodeClientService nodeClientService;

    private String imageId;

    public IndexTask(String imageId, ImageMetaDataRepository imageMetaDataRepository, NodeClientService nodeClientService) {
        this.imageId = imageId;
        this.imageMetaDataRepository = imageMetaDataRepository;
        this.nodeClientService = nodeClientService;
    }

    @Override
    public Void call() throws Exception {
        System.out.println("process IndexTask on " + getImageId());

        ImageMetaData imageMetaData = imageMetaDataRepository.findOne(imageId);

        SearchIndexDocument searchIndexDocument = new SearchIndexDocument();
        searchIndexDocument.setId(imageMetaData.getId());
        searchIndexDocument.setOriginalFilename(imageMetaData.getOriginalFilename());
        List<String> tags = new ArrayList<String>();
        for(Tag tag : imageMetaData.getTags()) {
            tags.add(tag.getName());
        }
        Collections.sort(tags);
        searchIndexDocument.setTags(tags);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(searchIndexDocument);

        Client client = nodeClientService.getClient();
        client.prepareIndex("imagemanager", "images").setSource(json).get();

        return null;
    }

    @Override
    public String getImageId() {
        return imageId;
    }

}
