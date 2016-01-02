package de.ulfbiallas.imagemanager.repository;

import org.springframework.data.repository.CrudRepository;

import de.ulfbiallas.imagemanager.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {

    public Category findByName(String name);

}
