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

/**
 * Created by championswimmer on 21/6/15.
 */
public class Restaurant {

    private RestaurantItem items;

    public RestaurantItem getItems() {
        return items;
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

        public String getAccepts_credit_card() {
            return accepts_credit_card;
        }

        public String getVotes() {
            return votes;
        }

        public String getCity() {
            return city;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUser_review_count() {
            return user_review_count;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getHas_bar() {
            return has_bar;
        }

        public String getCuisines() {
            return cuisines;
        }

        public String getHas_emirates_discount() {
            return has_emirates_discount;
        }

        public String getCost_for_two() {
            return cost_for_two;
        }

        public String getHas_citibank_discount() {
            return has_citibank_discount;
        }

        public String getHas_discount() {
            return has_discount;
        }

        public String getHas_dine_in() {
            return has_dine_in;
        }

        public String getDistance_actual() {
            return distance_actual;
        }

        public String getRating_aggregate() {
            return rating_aggregate;
        }

        public String getRating_editor_overall() {
            return rating_editor_overall;
        }

        public String getHas_delivery() {
            return has_delivery;
        }

        public String getDistance_friendly() {
            return distance_friendly;
        }

        public String getRating_text() {
            return rating_text;
        }

        public String getHas_menu() {
            return has_menu;
        }

        public String getAddress() {
            return address;
        }

        public String getLocality() {
            return locality;
        }

        public String getHas_coordinates() {
            return has_coordinates;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getIs_pure_veg() {
            return is_pure_veg;
        }
    }

}
