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

package com.greplr.common.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by championswimmer on 4/7/15.
 */
public class DateTimeUtils {

    public static final String LOG_TAG = "Greplr/DateTimeUtils";

    /**
     * This method returns Calendar object from a ISO8601 string.
     *
     * To get each object like year, month etc from the Calendar object, use this
     *
     * Calendar cal = DateTimeUtils.toCalendar(dateString);
     * int year = now.get(Calendar.YEAR);
     * int month = now.get(Calendar.MONTH); // Note: zero based! Jan = 0
     * int day = now.get(Calendar.DAY_OF_MONTH);
     * int hour = now.get(Calendar.HOUR_OF_DAY);
     * int minute = now.get(Calendar.MINUTE);
     * int second = now.get(Calendar.SECOND);
     * int millis = now.get(Calendar.MILLISECOND);
     * @param iso8601string
     * @return
     * @throws ParseException
     */
    public static Calendar busTimeToCalendar (final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.toUpperCase().replace("Z", "+0000");
        Log.v(LOG_TAG, s);

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(s);
        calendar.setTime(date);

        return calendar;
    }

    public static Calendar flightTimeToCalendar (final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        Date date = new SimpleDateFormat("yyyy-MM-dd't'HHmm").parse(iso8601string);
        calendar.setTime(date);

        return calendar;
    }


    public static String intToHrString (int hr, boolean twentyFourHrs) {
        if (!twentyFourHrs && hr > 12) {
            hr -= 12;
        }
        if (hr<10)
            return "0"+hr;
        else
            return String.valueOf(hr);
    }

    public static String intToMinString (int min) {

        if (min<10)
            return "0"+min;
        else
            return String.valueOf(min);
    }
}
