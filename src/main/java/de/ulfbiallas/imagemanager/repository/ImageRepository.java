package de.ulfbiallas.imagemanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, String> {

    public Image findByFilename(String filename);

    @Query("select i from Image i where i.originalFilename like %?1%")
    public Iterable<Image> searchFor(String query);

}
