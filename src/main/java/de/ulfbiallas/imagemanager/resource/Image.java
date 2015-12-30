package de.ulfbiallas.imagemanager.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.ulfbiallas.imagemanager.service.ImageService;

@Controller
public class Image {

    @Autowired
    private ImageService imageService;



    // curl -X POST "http://localhost/api/image/upload" -F "file=@test.png"
    @ResponseBody
    @RequestMapping(value="/image/upload", method=RequestMethod.POST)
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
