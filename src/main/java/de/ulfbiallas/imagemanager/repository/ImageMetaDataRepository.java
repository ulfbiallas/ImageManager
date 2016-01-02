package de.ulfbiallas.imagemanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.ulfbiallas.imagemanager.entity.ImageMetaData;

public interface ImageMetaDataRepository extends CrudRepository<ImageMetaData, String> {

}
