package de.ulfbiallas.imagemanager.body;

import java.util.Map;

public class ImageResponse {

    private String id;

    private String originalFilename;

    private Map<String, String> sizes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public Map<String, String> getSizes() {
        return sizes;
    }

    public void setSizes(Map<String, String> sizes) {
        this.sizes = sizes;
    }

}
