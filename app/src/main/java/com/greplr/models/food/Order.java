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
 *     You should immediately close your copy of code, and destroy the file. You are not authorised to
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
        private String review_number;
        private Boolean is_delivery_enabled;
        private List<String> food_characteristics = new ArrayList<>();
        private String web_path;
        private Boolean is_test;
        private String minimum_order_amount;
        private String logo;
        private String url_key;
        private String id;
        private String address_line2;
        private City city;
        private Boolean is_service_tax_visible;
        private String vat_percentage_amount;
        private Boolean is_preorder_enabled;
        private String custom_location_url;
        private Boolean is_vat_visible;
        private String minimum_delivery_fee;
        private String post_code;
        private Boolean is_vat_included_in_product_price;
        private String minimum_pickup_time;
        private String customer_phone;
        private Boolean is_replacement_dish_enabled;
        private String redirection_url;
        private Boolean is_vat_disabled;
        private Boolean is_pickup_enabled;
        private String service_tax_percentage_amount;
        private Boolean is_checkout_comment_enabled;
        private String customer_type;
        private Boolean is_service_tax_enabled;
        private Boolean is_voucher_enabled;
        private String minimum_delivery_time;
        private Boolean is_service_fee_enabled;
        private String service_fee_percentage_amount;
        private List<String> menus = new ArrayList<>();
        private List<String> schedules = new ArrayList<>();
        private List<String> cuisines = new ArrayList<>();
        private String latitude;
        private List<String> special_days = new ArrayList<>();
        private String metadata;
        private String website;
        private String description;
        private List<String> payment_types = new ArrayList<>();
        private String address;
        private String name;
        private String longitude;

        public String getRating() {
            return rating;
        }

        public List<String> getSpecial_days() {
            return special_days;
        }

        public void setSpecial_days(List<String> special_days) {
            this.special_days = special_days;
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

        public String getReview_number() {
            return review_number;
        }

        public void setReview_number(String review_number) {
            this.review_number = review_number;
        }

        public Boolean getIs_delivery_enabled() {
            return is_delivery_enabled;
        }

        public void setIs_delivery_enabled(Boolean is_delivery_enabled) {
            this.is_delivery_enabled = is_delivery_enabled;
        }

        public List<String> getFood_characteristics() {
            return food_characteristics;
        }

        public void setFood_characteristics(List<String> food_characteristics) {
            this.food_characteristics = food_characteristics;
        }

        public String getWeb_path() {
            return web_path;
        }

        public void setWeb_path(String web_path) {
            this.web_path = web_path;
        }

        public Boolean getIs_test() {
            return is_test;
        }

        public void setIs_test(Boolean is_test) {
            this.is_test = is_test;
        }

        public String getMinimum_order_amount() {
            return minimum_order_amount;
        }

        public void setMinimum_order_amount(String minimum_order_amount) {
            this.minimum_order_amount = minimum_order_amount;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUrl_key() {
            return url_key;
        }

        public void setUrl_key(String url_key) {
            this.url_key = url_key;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress_line2() {
            return address_line2;
        }

        public void setAddress_line2(String address_line2) {
            this.address_line2 = address_line2;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public Boolean getIs_service_tax_visible() {
            return is_service_tax_visible;
        }

        public void setIs_service_tax_visible(Boolean is_service_tax_visible) {
            this.is_service_tax_visible = is_service_tax_visible;
        }

        public String getVat_percentage_amount() {
            return vat_percentage_amount;
        }

        public void setVat_percentage_amount(String vat_percentage_amount) {
            this.vat_percentage_amount = vat_percentage_amount;
        }

        public Boolean getIs_preorder_enabled() {
            return is_preorder_enabled;
        }

        public void setIs_preorder_enabled(Boolean is_preorder_enabled) {
            this.is_preorder_enabled = is_preorder_enabled;
        }

        public String getCustom_location_url() {
            return custom_location_url;
        }

        public void setCustom_location_url(String custom_location_url) {
            this.custom_location_url = custom_location_url;
        }


        public Boolean getIs_vat_visible() {
            return is_vat_visible;
        }

        public void setIs_vat_visible(Boolean is_vat_visible) {
            this.is_vat_visible = is_vat_visible;
        }

        public String getMinimum_delivery_fee() {
            return minimum_delivery_fee;
        }

        public void setMinimum_delivery_fee(String minimum_delivery_fee) {
            this.minimum_delivery_fee = minimum_delivery_fee;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public Boolean getIs_vat_included_in_product_price() {
            return is_vat_included_in_product_price;
        }

        public void setIs_vat_included_in_product_price(Boolean is_vat_included_in_product_price) {
            this.is_vat_included_in_product_price = is_vat_included_in_product_price;
        }

        public String getMinimum_pickup_time() {
            return minimum_pickup_time;
        }

        public void setMinimum_pickup_time(String minimum_pickup_time) {
            this.minimum_pickup_time = minimum_pickup_time;
        }

        public String getCustomer_phone() {
            return customer_phone;
        }

        public void setCustomer_phone(String customer_phone) {
            this.customer_phone = customer_phone;
        }

        public Boolean getIs_replacement_dish_enabled() {
            return is_replacement_dish_enabled;
        }

        public void setIs_replacement_dish_enabled(Boolean is_replacement_dish_enabled) {
            this.is_replacement_dish_enabled = is_replacement_dish_enabled;
        }

        public String getRedirection_url() {
            return redirection_url;
        }

        public void setRedirection_url(String redirection_url) {
            this.redirection_url = redirection_url;
        }

        public Boolean getIs_vat_disabled() {
            return is_vat_disabled;
        }

        public void setIs_vat_disabled(Boolean is_vat_disabled) {
            this.is_vat_disabled = is_vat_disabled;
        }

        public Boolean getIs_pickup_enabled() {
            return is_pickup_enabled;
        }

        public void setIs_pickup_enabled(Boolean is_pickup_enabled) {
            this.is_pickup_enabled = is_pickup_enabled;
        }

        public String getService_tax_percentage_amount() {
            return service_tax_percentage_amount;
        }

        public void setService_tax_percentage_amount(String service_tax_percentage_amount) {
            this.service_tax_percentage_amount = service_tax_percentage_amount;
        }

        public Boolean getIs_checkout_comment_enabled() {
            return is_checkout_comment_enabled;
        }

        public void setIs_checkout_comment_enabled(Boolean is_checkout_comment_enabled) {
            this.is_checkout_comment_enabled = is_checkout_comment_enabled;
        }

        public String getCustomer_type() {
            return customer_type;
        }

        public void setCustomer_type(String customer_type) {
            this.customer_type = customer_type;
        }

        public Boolean getIs_service_tax_enabled() {
            return is_service_tax_enabled;
        }

        public void setIs_service_tax_enabled(Boolean is_service_tax_enabled) {
            this.is_service_tax_enabled = is_service_tax_enabled;
        }

        public Boolean getIs_voucher_enabled() {
            return is_voucher_enabled;
        }

        public void setIs_voucher_enabled(Boolean is_voucher_enabled) {
            this.is_voucher_enabled = is_voucher_enabled;
        }

        public String getMinimum_delivery_time() {
            return minimum_delivery_time;
        }

        public void setMinimum_delivery_time(String minimum_delivery_time) {
            this.minimum_delivery_time = minimum_delivery_time;
        }

        public Boolean getIs_service_fee_enabled() {
            return is_service_fee_enabled;
        }

        public void setIs_service_fee_enabled(Boolean is_service_fee_enabled) {
            this.is_service_fee_enabled = is_service_fee_enabled;
        }

        public String getService_fee_percentage_amount() {
            return service_fee_percentage_amount;
        }

        public void setService_fee_percentage_amount(String service_fee_percentage_amount) {
            this.service_fee_percentage_amount = service_fee_percentage_amount;
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
            return special_days;
        }

        public void setSpecialDays(List<String> special_days) {
            this.special_days = special_days;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getPayment_types() {
            return payment_types;
        }

        public void setPayment_types(List<String> payment_types) {
            this.payment_types = payment_types;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
            private String is_top_city;
            private String url_key;
            private String id;
            private String name;

            public String getIs_top_city() {
                return is_top_city;
            }

            public void setIs_top_city(String is_top_city) {
                this.is_top_city = is_top_city;
            }

            public String getUrl_key() {
                return url_key;
            }

            public void setUrl_key(String url_key) {
                this.url_key = url_key;
            }

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

    }
}