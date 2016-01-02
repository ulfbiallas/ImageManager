package de.ulfbiallas.imagemanager.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.ulfbiallas.imagemanager.body.ImageMetaDataRequest;
import de.ulfbiallas.imagemanager.body.ImageResponse;
import de.ulfbiallas.imagemanager.entity.Category;
import de.ulfbiallas.imagemanager.entity.Image;
import de.ulfbiallas.imagemanager.entity.ImageMetaData;
import de.ulfbiallas.imagemanager.entity.Tag;
import de.ulfbiallas.imagemanager.service.CategoryService;
import de.ulfbiallas.imagemanager.service.ImageMetaDataService;
import de.ulfbiallas.imagemanager.service.ImageService;
import de.ulfbiallas.imagemanager.service.TagService;

@Controller
public class ImageResource {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageMetaDataService imageMetaDataService;
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @ResponseBody
    @RequestMapping(
        value="/images",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public List<ImageResponse> getImages() {
        return imageService.getAll();
    }



    @ResponseBody
    @RequestMapping(
        value="/search/{query:.+}",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public List<ImageResponse> searchImages(@PathVariable String query) {
        return imageService.searchFor(query);
    }



    @ResponseBody
    @RequestMapping(
        value="/images/{filename:.+}",
        method=RequestMethod.GET
    )
    public void getImage(@PathVariable String filename, HttpServletResponse response, HttpServletRequest request) {

        //String[] filenameParts = filename.split("_");
        //String id = filenameParts[0];
        //Image image = imageService.getById(id);

        try {
            InputStream inputStream = new FileInputStream(new File("uploads/"+filename));
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int c = 0;
            while ((c = inputStream.read(buffer, 0, buffer.length)) > 0) {
                outputStream.write(buffer, 0, c);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            response.setStatus(404);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
    }



    // curl -X POST "http://localhost:8080/api/images/upload" -F "file=@test.png" -F "meta={};type=application/json"
    @ResponseBody
    @RequestMapping(
        value="/images/upload", 
        method=RequestMethod.POST
    )
    public String uploadImage(@RequestPart("file") MultipartFile file, @RequestPart("meta") ImageMetaDataRequest metaData) {

        Set<Tag> tags = tagService.getTagsByNames(new HashSet<String>(metaData.getTags()));
        Set<Category> categories = categoryService.getCategoriesByNames(new HashSet<String>(metaData.getCategories()));

        if (!file.isEmpty()) {
            try {
                Image image = imageService.saveFile(file.getBytes(), file.getOriginalFilename());
                ImageMetaData imageMetaData = imageMetaDataService.getByImage(image);
                imageMetaData.setTags(tags);
                imageMetaData.setCategories(categories);
                imageMetaDataService.updateMetaData(imageMetaData);

                return "Upload was successful!";
            } catch (Exception e) {
                return "There was an error: " + e.getMessage();
            }
        } else {
            return "Please provide a file!";
        }
    }

}
