package com.greplr.models.shopping.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghav on 01/07/15.
 */
public class ProductAttributes {

    private String title;
    private String productDescription;
    private ImageUrls imageUrls;
    private MaximumRetailPrice maximumRetailPrice;
    private SellingPrice sellingPrice;
    private String productUrl;
    private String productBrand;
    private Boolean inStock;
    private Boolean codAvailable;
    private String emiAvailable;
    private String discountPercentage;
    private String cashBack;
    private List<String> offers = new ArrayList<String>();
    private String size;
    private String color;
    private String sizeUnit;
    private String sizeVariants;
    private String colorVariants;
    private String styleCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
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

    public String getEmiAvailable() {
        return emiAvailable;
    }

    public void setEmiAvailable(String emiAvailable) {
        this.emiAvailable = emiAvailable;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
    }

    public List<String> getOffers() {
        return offers;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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

    public String getColorVariants() {
        return colorVariants;
    }

    public void setColorVariants(String colorVariants) {
        this.colorVariants = colorVariants;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }
}
