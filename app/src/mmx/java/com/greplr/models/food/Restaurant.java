/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       Arnav Gupta, Abhinv Sinha, Raghav Apoorv,
 *       Shubham Dokania, Yogesh Balan
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.greplr.models.food;

import java.util.List;

/**
 * Created by championswimmer on 21/6/15.
 */
public class Restaurant {

    private List<RestaurantResult> results;

    public List<RestaurantResult> getResults() {
        return results;
    }

    public void setResults(List<RestaurantResult> results) {
        this.results = results;
    }

    public class RestaurantResult {

        private RestaurantItem result;

        public RestaurantItem getResult() {
            return result;
        }

        public void setResult(RestaurantItem result) {
            this.result = result;
        }

        public class RestaurantItem {
            private String accepts_credit_card;
            private String rating_color;
            private String votes;
            private String city;
            private String id;
            private String name;
            private String user_review_count;
            private String longitude;
            private String has_bar;
            private String cuisines;
            private String has_emirates_discount;
            private String cost_for_two;
            private String has_citibank_discount;
            private String has_discount;
            private String has_dine_in;
            private String distance_actual;
            private String rating_aggregate;
            private String rating_editor_overall;
            private String has_delivery;
            private String distance_friendly;
            private String rating_text;
            private String has_menu;
            private String address;
            private String locality;
            private String has_coordinates;
            private String latitude;
            private String is_pure_veg;

            public String getRating_color() {
                return rating_color;
            }

            public void setRating_color(String rating_color) {
                this.rating_color = rating_color;
            }

            public String getAccepts_credit_card() {
                return accepts_credit_card;
            }

            public void setAccepts_credit_card(String accepts_credit_card) {
                this.accepts_credit_card = accepts_credit_card;
            }

            public String getVotes() {
                return votes;
            }

            public void setVotes(String votes) {
                this.votes = votes;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
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

            public String getUser_review_count() {
                return user_review_count;
            }

            public void setUser_review_count(String user_review_count) {
                this.user_review_count = user_review_count;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getHas_bar() {
                return has_bar;
            }

            public void setHas_bar(String has_bar) {
                this.has_bar = has_bar;
            }

            public String getCuisines() {
                return cuisines;
            }

            public void setCuisines(String cuisines) {
                this.cuisines = cuisines;
            }

            public String getHas_emirates_discount() {
                return has_emirates_discount;
            }

            public void setHas_emirates_discount(String has_emirates_discount) {
                this.has_emirates_discount = has_emirates_discount;
            }

            public String getCost_for_two() {
                return cost_for_two;
            }

            public void setCost_for_two(String cost_for_two) {
                this.cost_for_two = cost_for_two;
            }

            public String getHas_citibank_discount() {
                return has_citibank_discount;
            }

            public void setHas_citibank_discount(String has_citibank_discount) {
                this.has_citibank_discount = has_citibank_discount;
            }

            public String getHas_discount() {
                return has_discount;
            }

            public void setHas_discount(String has_discount) {
                this.has_discount = has_discount;
            }

            public String getHas_dine_in() {
                return has_dine_in;
            }

            public void setHas_dine_in(String has_dine_in) {
                this.has_dine_in = has_dine_in;
            }

            public String getDistance_actual() {
                return distance_actual;
            }

            public void setDistance_actual(String distance_actual) {
                this.distance_actual = distance_actual;
            }

            public String getRating_aggregate() {
                return rating_aggregate;
            }

            public void setRating_aggregate(String rating_aggregate) {
                this.rating_aggregate = rating_aggregate;
            }

            public String getRating_editor_overall() {
                return rating_editor_overall;
            }

            public void setRating_editor_overall(String rating_editor_overall) {
                this.rating_editor_overall = rating_editor_overall;
            }

            public String getHas_delivery() {
                return has_delivery;
            }

            public void setHas_delivery(String has_delivery) {
                this.has_delivery = has_delivery;
            }

            public String getDistance_friendly() {
                return distance_friendly;
            }

            public void setDistance_friendly(String distance_friendly) {
                this.distance_friendly = distance_friendly;
            }

            public String getRating_text() {
                return rating_text;
            }

            public void setRating_text(String rating_text) {
                this.rating_text = rating_text;
            }

            public String getHas_menu() {
                return has_menu;
            }

            public void setHas_menu(String has_menu) {
                this.has_menu = has_menu;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLocality() {
                return locality;
            }

            public void setLocality(String locality) {
                this.locality = locality;
            }

            public String getHas_coordinates() {
                return has_coordinates;
            }

            public void setHas_coordinates(String has_coordinates) {
                this.has_coordinates = has_coordinates;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getIs_pure_veg() {
                return is_pure_veg;
            }

            public void setIs_pure_veg(String is_pure_veg) {
                this.is_pure_veg = is_pure_veg;
            }
        }

    }


}
