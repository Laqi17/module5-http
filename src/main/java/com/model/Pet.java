package com.model;

import java.util.List;

public class Pet {
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public enum Status {
        AVAILABLE("available"),
        PENDING("pending"),
        SOLD("sold");

        private String status;

        public String getStatus() {
            return status;
        }

        Status(String status) {
            this.status = status;
        }
    }

    public Pet() {
    }

    public Pet(Long id, Category category, String name, List<String> photoUrls, List<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrl() {
        return photoUrls;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrls.add(photoUrl);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(Tag tags) {
        this.tags.add(tags);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrls + '\'' +
                ", tags=" + tags +
                ", status=" + status +
                '}';
    }
}
