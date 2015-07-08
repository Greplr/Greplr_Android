/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       1. Arnav Gupta
 *       2. Abhinav Sinha
 *       3. Prempal Singh
 *       4. Raghav Apoorv
 *       5. Shubham Dokania
 *       6. Yogesh Balan
 *
 *     The source code of this program is confidential and proprietary. If you are not part of the
 *     Greplr Team (one of the above 6 named individuals) you should not be viewing this code.
 *
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.models.shopping.search;

import java.util.List;

/**
 * Created by raghav on 01/07/15.
 */
public class Search {

    private String productDescription;
    private String styleCode;
    private String title;
    private String color;
    private String productId;
    private String codAvailable;
    private String emiAvailable;
    private String sizeUnit;
    private String productUrl;
    private List<String> offers;
    private String cashBack;
    private String colorVariants;
    private String inStock;
    private MaximumRetailPrice maximumRetailPrice;
    private String productBrand;
    private String size;
    private ImageUrls imageUrls;
    private String sizeVariants;
    private String discountPercentage;
    private SellingPrice sellingPrice;

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCodAvailable() {
        return codAvailable;
    }

    public void setCodAvailable(String codAvailable) {
        this.codAvailable = codAvailable;
    }

    public String getEmiAvailable() {
        return emiAvailable;
    }

    public void setEmiAvailable(String emiAvailable) {
        this.emiAvailable = emiAvailable;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public List<String> getOffers() {
        return offers;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
    }

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
    }

    public String getColorVariants() {
        return colorVariants;
    }

    public void setColorVariants(String colorVariants) {
        this.colorVariants = colorVariants;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public MaximumRetailPrice getMaximumRetailPrice() {
        return maximumRetailPrice;
    }

    public void setMaximumRetailPrice(MaximumRetailPrice maximumRetailPrice) {
        this.maximumRetailPrice = maximumRetailPrice;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getSizeVariants() {
        return sizeVariants;
    }

    public void setSizeVariants(String sizeVariants) {
        this.sizeVariants = sizeVariants;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public SellingPrice getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(SellingPrice sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    public class MaximumRetailPrice {

        private String amount;
        private String currency;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }

    public class ImageUrls {

        private String _400x400;
        private String _75x75;
        private String _275x275;
        private String _125x125;
        private String _40x40;
        private String _1100x1100;
        private String _100x100;
        private String _200x200;
        private String unknown;

        public String get_400x400() {
            return _400x400;
        }

        public void set_400x400(String _400x400) {
            this._400x400 = _400x400;
        }

        public String get_75x75() {
            return _75x75;
        }

        public void set_75x75(String _75x75) {
            this._75x75 = _75x75;
        }

        public String get_275x275() {
            return _275x275;
        }

        public void set_275x275(String _275x275) {
            this._275x275 = _275x275;
        }

        public String get_125x125() {
            return _125x125;
        }

        public void set_125x125(String _125x125) {
            this._125x125 = _125x125;
        }

        public String get_40x40() {
            return _40x40;
        }

        public void set_40x40(String _40x40) {
            this._40x40 = _40x40;
        }

        public String get_1100x1100() {
            return _1100x1100;
        }

        public void set_1100x1100(String _1100x1100) {
            this._1100x1100 = _1100x1100;
        }

        public String get_100x100() {
            return _100x100;
        }

        public void set_100x100(String _100x100) {
            this._100x100 = _100x100;
        }

        public String get_200x200() {
            return _200x200;
        }

        public void set_200x200(String _200x200) {
            this._200x200 = _200x200;
        }

        public String getUnknown() {
            return unknown;
        }

        public void setUnknown(String unknown) {
            this.unknown = unknown;
        }
    }

    public class SellingPrice {

        private String amount;
        private String currency;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}
