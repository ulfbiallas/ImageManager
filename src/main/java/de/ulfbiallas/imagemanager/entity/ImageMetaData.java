 package de.ulfbiallas.imagemanager.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class ImageMetaData extends Image {

    @Column
    private String title;

    @Column
    private String description;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Category> categories;

    @ManyToMany(fetch= FetchType.EAGER)
    private Set<Tag> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
