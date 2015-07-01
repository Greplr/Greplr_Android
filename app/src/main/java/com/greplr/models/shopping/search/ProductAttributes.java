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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(Object productDescription) {
        this.productDescription = productDescription;
    }

    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public MaximumRetailPrice getMaximumRetailPrice() {
        return maximumRetailPrice;
    }

    public void setMaximumRetailPrice(MaximumRetailPrice maximumRetailPrice) {
        this.maximumRetailPrice = maximumRetailPrice;
    }

    public SellingPrice getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(SellingPrice sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Boolean getCodAvailable() {
        return codAvailable;
    }

    public void setCodAvailable(Boolean codAvailable) {
        this.codAvailable = codAvailable;
    }

    public Object getEmiAvailable() {
        return emiAvailable;
    }

    public void setEmiAvailable(Object emiAvailable) {
        this.emiAvailable = emiAvailable;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Object getCashBack() {
        return cashBack;
    }

    public void setCashBack(Object cashBack) {
        this.cashBack = cashBack;
    }

    public List<Object> getOffers() {
        return offers;
    }

    public void setOffers(List<Object> offers) {
        this.offers = offers;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public String getSizeVariants() {
        return sizeVariants;
    }

    public void setSizeVariants(String sizeVariants) {
        this.sizeVariants = sizeVariants;
    }

    public Object getColorVariants() {
        return colorVariants;
    }

    public void setColorVariants(Object colorVariants) {
        this.colorVariants = colorVariants;
    }

    public Object getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(Object styleCode) {
        this.styleCode = styleCode;
    }
}
