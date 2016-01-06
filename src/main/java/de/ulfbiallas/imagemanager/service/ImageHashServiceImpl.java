package de.ulfbiallas.imagemanager.service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

/**
 * Calculates hash of image byte array based on:
 * - width
 * - height
 * - magic number (data type)
 * - image data (of a thumbnail)
 */
@Component
public class ImageHashServiceImpl implements ImageHashService {

    private static final int MAGIC_NUMBER_LENGTH = 8;

    private static final int THUMBNAIL_DIMENSION = 32;

    @Override
    public String hashImageData(byte[] imageData) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        byte[] magicNumber = extractBytes(imageData, 0, MAGIC_NUMBER_LENGTH);

        byte[] thumbnailData = scale(bufferedImage, THUMBNAIL_DIMENSION, THUMBNAIL_DIMENSION);

        int byteArrayLength = 4 + 4 + magicNumber.length + thumbnailData.length;
        ByteBuffer bytes = ByteBuffer.allocate(byteArrayLength);
        bytes.putInt(width);
        bytes.putInt(height);
        bytes.put(magicNumber);
        bytes.put(thumbnailData);

        MessageDigest messageDigest = getMessageDigest();
        byte[] bytesb = bytes.array();
        messageDigest.update(bytesb, 0, byteArrayLength);
        String hash = bytesToHex(messageDigest.digest());

        return hash;
    }

    private MessageDigest getMessageDigest() {
        String algorithm = "MD5";
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            new RuntimeException("Algoritm " + algorithm + " is not available!");
        }
        return messageDigest;
    }

    private String bytesToHex(byte[] bytes) {
        String hexString = "";
        for (int i=0; i < bytes.length; i++) {
            hexString += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return hexString;
    }

    private byte[] extractBytes(byte[] data, int from, int length) {
        if(data.length < from+length) {
            throw new RuntimeException("extraction range has to be inside byte array range!");
        }
        byte[] extracted = new byte[length];
        for(int k=0; k<length; ++k) {
            extracted[k] = data[from+k];
        }
        return extracted;
    }

    private byte[] scale(BufferedImage bufferedImage, int width, int height) throws IOException {
        Image scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage scaledBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = scaledBufferedImage.getGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        graphics.dispose();

        DataBuffer scaledBufferedImageDataBuffer = scaledBufferedImage.getData().getDataBuffer();
        ByteBuffer scaledImageBytes = ByteBuffer.allocate(width * height * 4); // TYPE_INT_ARGB -> 4
        for(int k=0; k<scaledBufferedImageDataBuffer.getSize(); ++k) {
            scaledImageBytes.putInt(scaledBufferedImageDataBuffer.getElem(k));
        }
        return scaledImageBytes.array();
    }

}
