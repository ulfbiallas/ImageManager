package de.ulfbiallas.imagemanager.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.ulfbiallas.imagemanager.entity.Category;
import de.ulfbiallas.imagemanager.entity.ImageMetaData;
import de.ulfbiallas.imagemanager.service.CategoryService;
import de.ulfbiallas.imagemanager.service.ImageMetaDataService;

@Controller
@RequestMapping(value="/images/{imageId}/categories")
public class ImageCategoryResource {

    @Autowired
    private ImageMetaDataService imageMetaDataService;

    @Autowired
    private CategoryService categoryService;

    @ResponseBody
    @RequestMapping(
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public List<String> getCategoriesOfImage(@PathVariable String imageId) {
        ImageMetaData imageMetaData = imageMetaDataService.getById(imageId);
        Set<Category> categories = imageMetaData.getCategories();
        List<String> categoryNames = new ArrayList<String>();
        for(Category category : categories) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }

    // curl -X POST http://localhost:8080/api/images/c0a993b5-ca7c-4ed1-82ef-7a9ad48d432d/categories -d '["newcategory"]' -H "Content-Type: application/json"
    @ResponseBody
    @RequestMapping(
        method=RequestMethod.POST,
        headers="Content-Type=application/json"
    )
    public List<String> addCategories(@PathVariable String imageId, @RequestBody List<String> categories) {
        ImageMetaData imageMetaData = imageMetaDataService.getById(imageId);
        for(String categoryName : categories) {
            Category category = categoryService.getCategoryByName(categoryName);
            imageMetaData.getCategories().add(category);
        }
        imageMetaDataService.updateMetaData(imageMetaData);
        return getCategoriesOfImage(imageId);
    }

}
