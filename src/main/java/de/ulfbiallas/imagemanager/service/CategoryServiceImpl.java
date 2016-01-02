package de.ulfbiallas.imagemanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.entity.Category;
import de.ulfbiallas.imagemanager.repository.CategoryRepository;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if(category==null) {
            category = new Category();
            category.setId(UUID.randomUUID().toString());
            category.setName(name);
            categoryRepository.save(category);
        }
        return category;
    }

    @Override
    public List<Category> getCategoriesByNames(List<String> names) {
        List<Category> categories = new ArrayList<Category>();
        if(names != null) {
            for(String name : names) {
                categories.add(getCategoryByName(name));
            }
        }
        return categories;
    }

}
