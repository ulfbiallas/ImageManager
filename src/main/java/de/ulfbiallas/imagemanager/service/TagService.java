package de.ulfbiallas.imagemanager.service;

import java.util.List;

import de.ulfbiallas.imagemanager.entity.Tag;

public interface TagService {

    Tag getTagByName(String name);

    List<Tag> getTagsByNames(List<String> names);

}
