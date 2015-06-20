package com.greplr.models.travel;

/**
 * Created by championswimmer on 20/6/15.
 */
public class Cab {

    private String display_name;
    private String min_price;
    private String time_of_arrival;
    private String price_per_km;
    private String provider;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getTime_of_arrival() {
        return time_of_arrival;
    }

    public void setTime_of_arrival(String time_of_arrival) {
        this.time_of_arrival = time_of_arrival;
    }

    public String getPrice_per_km() {
        return price_per_km;
    }

    public void setPrice_per_km(String price_per_km) {
        this.price_per_km = price_per_km;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return getDisplay_name() +
                getMin_price() +
                getPrice_per_km() +
                getProvider() +
                getTime_of_arrival();
    }
}
