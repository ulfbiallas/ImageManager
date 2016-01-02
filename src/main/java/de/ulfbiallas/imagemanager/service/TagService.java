package de.ulfbiallas.imagemanager.service;

import java.util.Set;

import de.ulfbiallas.imagemanager.entity.Tag;

public interface TagService {

    Tag getTagByName(String name);

    Set<Tag> getTagsByNames(Set<String> names);

}
