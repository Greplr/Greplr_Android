package com.greplr.models.shopping.offers;

import java.util.List;

/**
 * Created by raghav on 01/07/15.
 */
public class Offers {

    private String title;
    private String description;
    private String url;
    private List<ImageURLs> imageUrls;
    private String availability;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ImageURLs> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageURLs> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
