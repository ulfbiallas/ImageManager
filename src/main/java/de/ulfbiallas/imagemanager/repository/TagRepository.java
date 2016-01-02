package de.ulfbiallas.imagemanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.ulfbiallas.imagemanager.entity.Tag;

public interface TagRepository extends CrudRepository<Tag, String> {

    public Tag findByName(String name);

}
