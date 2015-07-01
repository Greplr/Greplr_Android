package com.greplr.models.shopping.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghav on 01/07/15.
 */
public class ProductAttributes {

    private String title;
    private Object productDescription;
    private ImageUrls imageUrls;
    private MaximumRetailPrice maximumRetailPrice;
    private SellingPrice sellingPrice;
    private String productUrl;
    private String productBrand;
    private Boolean inStock;
    private Boolean codAvailable;
    private Object emiAvailable;
    private Integer discountPercentage;
    private Object cashBack;
    private List<Object> offers = new ArrayList<Object>();
    private Object size;
    private String color;
    private String sizeUnit;
    private String sizeVariants;
    private Object colorVariants;
    private Object styleCode;

}
