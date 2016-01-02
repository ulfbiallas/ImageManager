package de.ulfbiallas.imagemanager.service;

import java.util.List;

import de.ulfbiallas.imagemanager.entity.Category;

public interface CategoryService {

    Category getCategoryByName(String name);

    List<Category> getCategoriesByNames(List<String> names);

}
