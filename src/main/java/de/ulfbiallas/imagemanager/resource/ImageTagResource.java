package de.ulfbiallas.imagemanager.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.ulfbiallas.imagemanager.entity.ImageMetaData;
import de.ulfbiallas.imagemanager.entity.Tag;
import de.ulfbiallas.imagemanager.service.ImageMetaDataService;
import de.ulfbiallas.imagemanager.service.TagService;

@Controller
@RequestMapping(value="/images/{imageId}/tags")
public class ImageTagResource {

    @Autowired
    private ImageMetaDataService imageMetaDataService;

    @Autowired
    private TagService tagService;

    @ResponseBody
    @RequestMapping(
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public List<String> getTagsOfImage(@PathVariable String imageId) {
        ImageMetaData imageMetaData = imageMetaDataService.getById(imageId);
        Set<Tag> tags = imageMetaData.getTags();
        List<String> tagNames = new ArrayList<String>();
        for(Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        return tagNames;
    }

    // curl -X POST http://localhost:8080/api/images/c0a993b5-ca7c-4ed1-82ef-7a9ad48d432d/tags -d '["newtag"]' -H "Content-Type: application/json"
    @ResponseBody
    @RequestMapping(
        method=RequestMethod.POST,
        headers="Content-Type=application/json"
    )
    public List<String> addTags(@PathVariable String imageId, @RequestBody List<String> tags) {
        ImageMetaData imageMetaData = imageMetaDataService.getById(imageId);
        for(String tagName : tags) {
            Tag tag = tagService.getTagByName(tagName);
            imageMetaData.getTags().add(tag);
        }
        imageMetaDataService.updateMetaData(imageMetaData);
        return getTagsOfImage(imageId);
    }

}
