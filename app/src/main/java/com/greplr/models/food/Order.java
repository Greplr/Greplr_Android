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

package com.greplr.models.food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghav on 05/07/15.
 */
public class Order {

    private String area_id;

    public List<OrderRestaurants> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<OrderRestaurants> restaurants) {
        this.restaurants = restaurants;
    }

    private List<OrderRestaurants> restaurants;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }



    public class OrderRestaurants {
        private String rating;
        private String code;
        private Chain chain;
        private String reviewNumber;
        private Boolean isDeliveryEnabled;
        private List<String> foodCharacteristics = new ArrayList<>();
        private String webPath;
        private Boolean isTest;
        private String minimumOrderAmount;
        private String logo;
        private String urlKey;
        private String id;
        private String addressLine2;
        private City city;
        private Boolean isServiceTaxVisible;
        private String vatPercentageAmount;
        private List<String> menus = new ArrayList<>();
        private List<String> schedules = new ArrayList<>();
        private Boolean isPreorderEnabled;
        private List<Discount> discounts = new ArrayList<Discount>();
        private String customLocationUrl;
        private List<String> cuisines = new ArrayList<>();
        private String latitude;
        private List<String> specialDays = new ArrayList<>();
        private String metadata;
        private String website;
        private Boolean isVatVisible;
        private String minimumDeliveryFee;
        private String postCode;
        private String description;
        private Boolean isVatIncludedInProductPrice;
        private String minimumPickupTime;
        private String customerPhone;
        private Boolean isReplacementDishEnabled;
        private String redirectionUrl;
        private Boolean isVatDisabled;
        private List<String> paymentTypes = new ArrayList<>();
        private String address;
        private Boolean isPickupEnabled;
        private Integer serviceTaxPercentageAmount;
        private String name;
        private String longitude;
        private Boolean isCheckoutCommentEnabled;
        private String customerType;
        private Boolean isServiceTaxEnabled;
        private Boolean isVoucherEnabled;
        private String minimumDeliveryTime;
        private Boolean isServiceFeeEnabled;
        private String serviceFeePercentageAmount;

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Chain getChain() {
            return chain;
        }

        public void setChain(Chain chain) {
            this.chain = chain;
        }

        public String getReviewNumber() {
            return reviewNumber;
        }

        public void setReviewNumber(String reviewNumber) {
            this.reviewNumber = reviewNumber;
        }

        public Boolean getIsDeliveryEnabled() {
            return isDeliveryEnabled;
        }

        public void setIsDeliveryEnabled(Boolean isDeliveryEnabled) {
            this.isDeliveryEnabled = isDeliveryEnabled;
        }

        public List<String> getFoodCharacteristics() {
            return foodCharacteristics;
        }

        public void setFoodCharacteristics(List<String> foodCharacteristics) {
            this.foodCharacteristics = foodCharacteristics;
        }

        public String getWebPath() {
            return webPath;
        }

        public void setWebPath(String webPath) {
            this.webPath = webPath;
        }

        public Boolean getIsTest() {
            return isTest;
        }

        public void setIsTest(Boolean isTest) {
            this.isTest = isTest;
        }

        public String getMinimumOrderAmount() {
            return minimumOrderAmount;
        }

        public void setMinimumOrderAmount(String minimumOrderAmount) {
            this.minimumOrderAmount = minimumOrderAmount;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUrlKey() {
            return urlKey;
        }

        public void setUrlKey(String urlKey) {
            this.urlKey = urlKey;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public Boolean getIsServiceTaxVisible() {
            return isServiceTaxVisible;
        }

        public void setIsServiceTaxVisible(Boolean isServiceTaxVisible) {
            this.isServiceTaxVisible = isServiceTaxVisible;
        }

        public String getVatPercentageAmount() {
            return vatPercentageAmount;
        }

        public void setVatPercentageAmount(String vatPercentageAmount) {
            this.vatPercentageAmount = vatPercentageAmount;
        }

        public List<String> getMenus() {
            return menus;
        }

        public void setMenus(List<String> menus) {
            this.menus = menus;
        }

        public List<String> getSchedules() {
            return schedules;
        }

        public void setSchedules(List<String> schedules) {
            this.schedules = schedules;
        }

        public Boolean getIsPreorderEnabled() {
            return isPreorderEnabled;
        }

        public void setIsPreorderEnabled(Boolean isPreorderEnabled) {
            this.isPreorderEnabled = isPreorderEnabled;
        }

        public List<Discount> getDiscounts() {
            return discounts;
        }

        public void setDiscounts(List<Discount> discounts) {
            this.discounts = discounts;
        }

        public String getCustomLocationUrl() {
            return customLocationUrl;
        }

        public void setCustomLocationUrl(String customLocationUrl) {
            this.customLocationUrl = customLocationUrl;
        }

        public List<String> getCuisines() {
            return cuisines;
        }

        public void setCuisines(List<String> cuisines) {
            this.cuisines = cuisines;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public List<String> getSpecialDays() {
            return specialDays;
        }

        public void setSpecialDays(List<String> specialDays) {
            this.specialDays = specialDays;
        }

        public String getMetadata() {
            return metadata;
        }

        public void setMetadata(String metadata) {
            this.metadata = metadata;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public Boolean getIsVatVisible() {
            return isVatVisible;
        }

        public void setIsVatVisible(Boolean isVatVisible) {
            this.isVatVisible = isVatVisible;
        }

        public String getMinimumDeliveryFee() {
            return minimumDeliveryFee;
        }

        public void setMinimumDeliveryFee(String minimumDeliveryFee) {
            this.minimumDeliveryFee = minimumDeliveryFee;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getIsVatIncludedInProductPrice() {
            return isVatIncludedInProductPrice;
        }

        public void setIsVatIncludedInProductPrice(Boolean isVatIncludedInProductPrice) {
            this.isVatIncludedInProductPrice = isVatIncludedInProductPrice;
        }

        public String getMinimumPickupTime() {
            return minimumPickupTime;
        }

        public void setMinimumPickupTime(String minimumPickupTime) {
            this.minimumPickupTime = minimumPickupTime;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public Boolean getIsReplacementDishEnabled() {
            return isReplacementDishEnabled;
        }

        public void setIsReplacementDishEnabled(Boolean isReplacementDishEnabled) {
            this.isReplacementDishEnabled = isReplacementDishEnabled;
        }

        public String getRedirectionUrl() {
            return redirectionUrl;
        }

        public void setRedirectionUrl(String redirectionUrl) {
            this.redirectionUrl = redirectionUrl;
        }

        public Boolean getIsVatDisabled() {
            return isVatDisabled;
        }

        public void setIsVatDisabled(Boolean isVatDisabled) {
            this.isVatDisabled = isVatDisabled;
        }

        public List<String> getPaymentTypes() {
            return paymentTypes;
        }

        public void setPaymentTypes(List<String> paymentTypes) {
            this.paymentTypes = paymentTypes;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Boolean getIsPickupEnabled() {
            return isPickupEnabled;
        }

        public void setIsPickupEnabled(Boolean isPickupEnabled) {
            this.isPickupEnabled = isPickupEnabled;
        }

        public Integer getServiceTaxPercentageAmount() {
            return serviceTaxPercentageAmount;
        }

        public void setServiceTaxPercentageAmount(Integer serviceTaxPercentageAmount) {
            this.serviceTaxPercentageAmount = serviceTaxPercentageAmount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public Boolean getIsCheckoutCommentEnabled() {
            return isCheckoutCommentEnabled;
        }

        public void setIsCheckoutCommentEnabled(Boolean isCheckoutCommentEnabled) {
            this.isCheckoutCommentEnabled = isCheckoutCommentEnabled;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public Boolean getIsServiceTaxEnabled() {
            return isServiceTaxEnabled;
        }

        public void setIsServiceTaxEnabled(Boolean isServiceTaxEnabled) {
            this.isServiceTaxEnabled = isServiceTaxEnabled;
        }

        public Boolean getIsVoucherEnabled() {
            return isVoucherEnabled;
        }

        public void setIsVoucherEnabled(Boolean isVoucherEnabled) {
            this.isVoucherEnabled = isVoucherEnabled;
        }

        public String getMinimumDeliveryTime() {
            return minimumDeliveryTime;
        }

        public void setMinimumDeliveryTime(String minimumDeliveryTime) {
            this.minimumDeliveryTime = minimumDeliveryTime;
        }

        public Boolean getIsServiceFeeEnabled() {
            return isServiceFeeEnabled;
        }

        public void setIsServiceFeeEnabled(Boolean isServiceFeeEnabled) {
            this.isServiceFeeEnabled = isServiceFeeEnabled;
        }

        public String getServiceFeePercentageAmount() {
            return serviceFeePercentageAmount;
        }

        public void setServiceFeePercentageAmount(String serviceFeePercentageAmount) {
            this.serviceFeePercentageAmount = serviceFeePercentageAmount;
        }

        public class Chain {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
        public class City {
            private Object isTopCity;
            private String urlKey;
            private Integer id;
            private String name;

            public Object getIsTopCity() {
                return isTopCity;
            }

            public void setIsTopCity(Object isTopCity) {
                this.isTopCity = isTopCity;
            }

            public String getUrlKey() {
                return urlKey;
            }

            public void setUrlKey(String urlKey) {
                this.urlKey = urlKey;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
        public class Discount {
            private String description;
            private String endDate;
            private List<String> bogoProductBlocks = new ArrayList<>();
            private String promotionalLimit;
            private String discountAmount;
            private String vendorId;
            private String startDate;
            private String fileName;
            private String discountType;
            private String bogoDiscountUnit;
            private String conditionType;
            private String closingTime;
            private String openingTime;
            private String minimumOrderValue;
            private String conditionObject;
            private String id;
            private String discountText;
            private String name;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public List<String> getBogoProductBlocks() {
                return bogoProductBlocks;
            }

            public void setBogoProductBlocks(List<String> bogoProductBlocks) {
                this.bogoProductBlocks = bogoProductBlocks;
            }

            public String getPromotionalLimit() {
                return promotionalLimit;
            }

            public void setPromotionalLimit(String promotionalLimit) {
                this.promotionalLimit = promotionalLimit;
            }

            public String getDiscountAmount() {
                return discountAmount;
            }

            public void setDiscountAmount(String discountAmount) {
                this.discountAmount = discountAmount;
            }

            public String getVendorId() {
                return vendorId;
            }

            public void setVendorId(String vendorId) {
                this.vendorId = vendorId;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getDiscountType() {
                return discountType;
            }

            public void setDiscountType(String discountType) {
                this.discountType = discountType;
            }

            public String getBogoDiscountUnit() {
                return bogoDiscountUnit;
            }

            public void setBogoDiscountUnit(String bogoDiscountUnit) {
                this.bogoDiscountUnit = bogoDiscountUnit;
            }

            public String getConditionType() {
                return conditionType;
            }

            public void setConditionType(String conditionType) {
                this.conditionType = conditionType;
            }

            public String getClosingTime() {
                return closingTime;
            }

            public void setClosingTime(String closingTime) {
                this.closingTime = closingTime;
            }

            public String getOpeningTime() {
                return openingTime;
            }

            public void setOpeningTime(String openingTime) {
                this.openingTime = openingTime;
            }

            public String getMinimumOrderValue() {
                return minimumOrderValue;
            }

            public void setMinimumOrderValue(String minimumOrderValue) {
                this.minimumOrderValue = minimumOrderValue;
            }

            public String getConditionObject() {
                return conditionObject;
            }

            public void setConditionObject(String conditionObject) {
                this.conditionObject = conditionObject;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDiscountText() {
                return discountText;
            }

            public void setDiscountText(String discountText) {
                this.discountText = discountText;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

}