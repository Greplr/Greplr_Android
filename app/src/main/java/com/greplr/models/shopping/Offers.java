package com.greplr.models.shopping;

import java.util.List;

/**
 * Created by raghav on 01/07/15.
 */
public class Offers {

    public String title;
    public String description;
    public String url;
    public ImageURLs imageUrl;
    public String availability;

}
 class ImageURLs{
     public List<String> url;
 }