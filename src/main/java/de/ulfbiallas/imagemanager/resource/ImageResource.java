package de.ulfbiallas.imagemanager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.ulfbiallas.imagemanager.repository.Image;
import de.ulfbiallas.imagemanager.service.ImageService;

@Controller
public class ImageResource {

    @Autowired
    private ImageService imageService;



    @ResponseBody
    @RequestMapping(
        value="/images",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public Iterable<Image> getImages() {
        return imageService.getAll();
    }

    // curl -X POST "http://localhost:8080/api/images/upload" -F "file=@test.png"
    @ResponseBody
    @RequestMapping(
        value="/images/upload", 
        method=RequestMethod.POST
    )
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                imageService.saveFile(file.getBytes(), file.getOriginalFilename());
                return "Upload was successful!";
            } catch (Exception e) {
                return "There was an error: " + e.getMessage();
            }
        } else {
            return "Please provide a file!";
        }
    }

}
