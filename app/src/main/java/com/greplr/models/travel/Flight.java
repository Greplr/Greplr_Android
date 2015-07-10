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

package com.greplr.models.travel;

/**
 * Created by championswimmer on 20/6/15.
 */
public class Flight {

    private String origin;
    private String fare;
    private String flightnum;
    private String warnings;
    private String destination;
    private String duration;
    private String airline;
    private String seatingclass;
    private String depdate;
    private String arrdate;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getFlightnum() {
        return flightnum;
    }

    public void setFlightnum(String flightnum) {
        this.flightnum = flightnum;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getSeatingclass() {
        return seatingclass;
    }

    public void setSeatingclass(String seatingclass) {
        this.seatingclass = seatingclass;
    }

    public String getDepdate() {
        return depdate;
    }

    public void setDepdate(String depdate) {
        this.depdate = depdate;
    }

    public String getArrdate() {
        return arrdate;
    }

    public void setArrdate(String arrdate) {
        this.arrdate = arrdate;
    }
}