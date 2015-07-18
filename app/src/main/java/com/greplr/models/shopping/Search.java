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

package com.greplr.models.shopping;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raghav on 01/07/15.
 */
public class Search {

    public static final String LOG_TAG = "Greplr/Shopping/Search";

    public static List<Product> getProductList (String searchResult) {
        List<Product> productList = new ArrayList<>();
        try {
            JSONObject product = (new JSONObject(searchResult)).getJSONObject("RESPONSE").getJSONObject("product");
            Type prodType = new TypeToken<Map<String,Product>>() {}.getType();
            Gson gson = new Gson();
            Log.d(LOG_TAG, "" + product.toString());
            Map<String, Product> prodMap = gson.fromJson(new JsonParser().parse(product.toString()), prodType);
            Iterator prodItr = prodMap.entrySet().iterator();
            for (String key : prodMap.keySet()) {
                //Log.d(LOG_TAG, "" + prodMap.get(key).getMainTitle());
                productList.add(prodMap.get(key));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productList;
    }


    public class Product {
        public String smartUrl;
        public String mrp;
        public String productStatus;

        public String getSmartUrl() {
            return smartUrl;
        }

        public void setSmartUrl(String smartUrl) {
            this.smartUrl = smartUrl;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(String sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public String getMainTitle() {
            return mainTitle;
        }

        public void setMainTitle(String mainTitle) {
            this.mainTitle = mainTitle;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getProductAltImage() {
            return productAltImage;
        }

        public void setProductAltImage(String productAltImage) {
            this.productAltImage = productAltImage;
        }

        public Ugc getUgc() {
            return ugc;
        }

        public void setUgc(Ugc ugc) {
            this.ugc = ugc;
        }

        public OmnitureData getOmnitureData() {
            return omnitureData;
        }

        public void setOmnitureData(OmnitureData omnitureData) {
            this.omnitureData = omnitureData;
        }

        public String sellingPrice;
        public String mainTitle;
        public String subTitle;
        public String productAltImage;
        public Ugc ugc;
        public OmnitureData omnitureData;



        public class Ugc {
            RatingObj ratingObj;

            public RatingObj getRatingObj() {
                return ratingObj;
            }

            public void setRatingObj(RatingObj ratingObj) {
                this.ratingObj = ratingObj;
            }

            public class RatingObj {
                public String getOverallRating() {
                    return overallRating;
                }

                public void setOverallRating(String overallRating) {
                    this.overallRating = overallRating;
                }

                String overallRating;
            }
        }

        public class OmnitureData {
            public String getAnlt_superCategory() {
                return anlt_superCategory;
            }

            public void setAnlt_superCategory(String anlt_superCategory) {
                this.anlt_superCategory = anlt_superCategory;
            }

            public String getAnlt_category() {
                return anlt_category;
            }

            public void setAnlt_category(String anlt_category) {
                this.anlt_category = anlt_category;
            }

            public String getAnlt_vertical() {
                return anlt_vertical;
            }

            public void setAnlt_vertical(String anlt_vertical) {
                this.anlt_vertical = anlt_vertical;
            }

            public String getAnlt_subcategory() {
                return anlt_subcategory;
            }

            public void setAnlt_subcategory(String anlt_subcategory) {
                this.anlt_subcategory = anlt_subcategory;
            }

            String anlt_superCategory;
            String anlt_category;
            String anlt_vertical;
            String anlt_subcategory;
        }



    }

}
