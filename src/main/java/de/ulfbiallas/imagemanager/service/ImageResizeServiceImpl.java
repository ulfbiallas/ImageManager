package de.ulfbiallas.imagemanager.service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagemanager.entity.Image;

@Component
public class ImageResizeServiceImpl implements ImageResizeService {

    final static Logger logger = LoggerFactory.getLogger(ImageResizeServiceImpl.class);

    @Override
    public void createCopiesWithDifferentSizes(Image image) {
        File imageFile = new File("uploads/"+image.getFilename());
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            BufferedImage imageMiniThumbnail = scale(bufferedImage, 64);
            saveCopy(imageMiniThumbnail, image, "mini");

            BufferedImage imageThumbnail = scale(bufferedImage, 200);
            saveCopy(imageThumbnail, image, "thumbnail");

            BufferedImage imageSmall = scale(bufferedImage, 640);
            saveCopy(imageSmall, image, "small");

            BufferedImage imageMedium = scale(bufferedImage, 800);
            saveCopy(imageMedium, image, "medium");

            BufferedImage imageLarge = scale(bufferedImage, 1024);
            saveCopy(imageLarge, image, "large");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void saveCopy(BufferedImage bufferedImage, Image image, String sizeSuffix) {
        try {
            ImageIO.write(bufferedImage, "jpg", new File("uploads/"+image.getId()+"_"+sizeSuffix+".jpg"));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private BufferedImage scale(BufferedImage bufferedImage, int dimension) throws IOException {
        double oldWidth = bufferedImage.getWidth();
        double oldHeight = bufferedImage.getHeight();
        double newWidth, newHeight;
        if(oldWidth > oldHeight) {
            newWidth = dimension;
            newHeight = oldHeight / oldWidth * newWidth;
        } else {
            newHeight = dimension;
            newWidth = oldWidth / oldHeight * newHeight;
        }
        int width = (int) newWidth;
        int height = (int) newHeight;

        java.awt.Image scaledImage = bufferedImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage scaledBufferedImage = new BufferedImage(width, height, bufferedImage.getType());
        Graphics graphics = scaledBufferedImage.getGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();
        return scaledBufferedImage;
    }

}
