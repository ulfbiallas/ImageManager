package de.ulfbiallas.imagemanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.entity.Tag;
import de.ulfbiallas.imagemanager.repository.TagRepository;

@Component
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag getTagByName(String name) {
        Tag tag = tagRepository.findByName(name);
        if(tag==null) {
            tag = new Tag();
            tag.setId(UUID.randomUUID().toString());
            tag.setName(name);
            tagRepository.save(tag);
        }
        return tag;
    }

    @Override
    public List<Tag> getTagsByNames(List<String> names) {
        List<Tag> tags = new ArrayList<Tag>();
        if(names != null) {
            for(String name : names) {
                tags.add(getTagByName(name));
            }
        }
        return tags;
    }

}
