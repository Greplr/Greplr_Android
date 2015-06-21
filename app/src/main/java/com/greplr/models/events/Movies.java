package com.greplr.models.events;

import java.util.ArrayList;

/**
 * Created by raghav on 21/06/15.
 */
public class Movies {

    public String Ratings;
    public ArrayList<String> Actors;
    public String Language;
    public String Length;
    public String EventTitle;
    public String EventCensor;

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public ArrayList<String> getActors() {
        return Actors;
    }

    public void setActors(ArrayList<String> actors) {
        Actors = actors;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public String getEventCensor() {
        return EventCensor;
    }

    public void setEventCensor(String eventCensor) {
        EventCensor = eventCensor;
    }
}
