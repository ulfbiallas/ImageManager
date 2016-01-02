package de.ulfbiallas.imagemanager.service;

import java.util.Set;

import de.ulfbiallas.imagemanager.entity.Category;

public interface CategoryService {

    Category getCategoryByName(String name);

    Set<Category> getCategoriesByNames(Set<String> names);

}
