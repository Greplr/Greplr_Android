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

package com.greplr.models.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghav on 21/06/15.
 */
public class Plays {

    private String BannerURL;
    private List<Dates> arrDates;
    private List<Venues> arrVenues;
    private String Ratings;
    private List<String> GenreArray = new ArrayList<String>();
    private String FShareURL;
    private String Actors;
    private String Event_CriticsRatingsCount;
    private String Event_UserReviewCount;
    private String EventSynopsis;
    private String EventCode;
    private String ImageCode;
    private String Director;
    private String Genre;
    private String Language;
    private String ReleaseDateCode;
    private String Length;
    private String TrailerURL;
    private String EventTitle;
    private String EventCensor;

    public List<Dates> getArrDates() {
        return arrDates;
    }

    public void setArrDates(List<Dates> arrDates) {
        this.arrDates = arrDates;
    }

    public List<Venues> getArrVenues() {
        return arrVenues;
    }

    public void setArrVenues(List<Venues> arrVenues) {
        this.arrVenues = arrVenues;
    }

    public List<String> getGenreArray() {
        return GenreArray;
    }

    public void setGenreArray(List<String> genreArray) {
        GenreArray = genreArray;
    }

    public String getFShareURL() {
        return FShareURL;
    }

    public void setFShareURL(String FShareURL) {
        this.FShareURL = FShareURL;
    }

    public String getEvent_CriticsRatingsCount() {
        return Event_CriticsRatingsCount;
    }

    public void setEvent_CriticsRatingsCount(String event_CriticsRatingsCount) {
        Event_CriticsRatingsCount = event_CriticsRatingsCount;
    }

    public String getEvent_UserReviewCount() {
        return Event_UserReviewCount;
    }

    public void setEvent_UserReviewCount(String event_UserReviewCount) {
        Event_UserReviewCount = event_UserReviewCount;
    }

    public String getEventSynopsis() {
        return EventSynopsis;
    }

    public void setEventSynopsis(String eventSynopsis) {
        EventSynopsis = eventSynopsis;
    }

    public String getEventCode() {
        return EventCode;
    }

    public void setEventCode(String eventCode) {
        EventCode = eventCode;
    }

    public String getImageCode() {
        return ImageCode;
    }

    public void setImageCode(String imageCode) {
        ImageCode = imageCode;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getReleaseDateCode() {
        return ReleaseDateCode;
    }

    public void setReleaseDateCode(String releaseDateCode) {
        ReleaseDateCode = releaseDateCode;
    }

    public String getTrailerURL() {
        return TrailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        TrailerURL = trailerURL;
    }

    public String getEventCensor() {
        return EventCensor;
    }

    public void setEventCensor(String eventCensor) {
        EventCensor = eventCensor;
    }

    public String getBannerURL() { return BannerURL; }

    public void setBannerURL(String bannerURL) { BannerURL = bannerURL; }

    public String getRatings() {
        return Ratings;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
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
    public class Dates {

        private String ShowDateCode;
        private String ShowDateDisplay;
        private String EventCode;

        public String getShowDateCode() {
            return ShowDateCode;
        }

        public void setShowDateCode(String showDateCode) {
            ShowDateCode = showDateCode;
        }

        public String getShowDateDisplay() {
            return ShowDateDisplay;
        }

        public void setShowDateDisplay(String showDateDisplay) {
            ShowDateDisplay = showDateDisplay;
        }

        public String getEventCode() {
            return EventCode;
        }

        public void setEventCode(String eventCode) {
            EventCode = eventCode;
        }
    }

    public class Venues {

        private String Distance;
        private String Region_strName;
        private String EventCode;
        private String SubRegionCode;
        private String VenueName;
        private String VenueLongitude;
        private String Region_strCode;
        private String RegionCode;
        private String VenueLatitude;
        private String SubRegion_strCode;
        private String SubRegion_strName;
        private String VenueCode;

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String distance) {
            Distance = distance;
        }

        public String getRegion_strName() {
            return Region_strName;
        }

        public void setRegion_strName(String region_strName) {
            Region_strName = region_strName;
        }

        public String getEventCode() {
            return EventCode;
        }

        public void setEventCode(String eventCode) {
            EventCode = eventCode;
        }

        public String getSubRegionCode() {
            return SubRegionCode;
        }

        public void setSubRegionCode(String subRegionCode) {
            SubRegionCode = subRegionCode;
        }

        public String getVenueName() {
            return VenueName;
        }

        public void setVenueName(String venueName) {
            VenueName = venueName;
        }

        public String getVenueLongitude() {
            return VenueLongitude;
        }

        public void setVenueLongitude(String venueLongitude) {
            VenueLongitude = venueLongitude;
        }

        public String getRegion_strCode() {
            return Region_strCode;
        }

        public void setRegion_strCode(String region_strCode) {
            Region_strCode = region_strCode;
        }

        public String getRegionCode() {
            return RegionCode;
        }

        public void setRegionCode(String regionCode) {
            RegionCode = regionCode;
        }

        public String getVenueLatitude() {
            return VenueLatitude;
        }

        public void setVenueLatitude(String venueLatitude) {
            VenueLatitude = venueLatitude;
        }

        public String getSubRegion_strCode() {
            return SubRegion_strCode;
        }

        public void setSubRegion_strCode(String subRegion_strCode) {
            SubRegion_strCode = subRegion_strCode;
        }

        public String getSubRegion_strName() {
            return SubRegion_strName;
        }

        public void setSubRegion_strName(String subRegion_strName) {
            SubRegion_strName = subRegion_strName;
        }

        public String getVenueCode() {
            return VenueCode;
        }

        public void setVenueCode(String venueCode) {
            VenueCode = venueCode;
        }
    }
}
