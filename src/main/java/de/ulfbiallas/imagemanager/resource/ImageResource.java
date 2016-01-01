package de.ulfbiallas.imagemanager.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.ulfbiallas.imagemanager.entity.Image;
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



    @ResponseBody
    @RequestMapping(
        value="/search/{query:.+}",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public Iterable<Image> searchImages(@PathVariable String query) {
        return imageService.searchFor(query);
    }



    @ResponseBody
    @RequestMapping(
        value="/images/{filename:.+}",
        method=RequestMethod.GET
    )
    public void getImage(@PathVariable String filename, HttpServletResponse response, HttpServletRequest request) {

        Image image = imageService.getByFilename(filename);

        try {
            InputStream inputStream = new FileInputStream(new File("uploads/"+image.getFilename()));
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
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
