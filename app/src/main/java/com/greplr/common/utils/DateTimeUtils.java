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

package com.greplr.common.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.greplr.common.ui.MaterialEditText;

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
     * int year = now.get(Calendar.YEAR);            android:paddingTop="30dp"

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
        if (iso8601string == null || iso8601string.length() < 19){
            return null;
        }
        Calendar calendar = GregorianCalendar.getInstance();
        //FIXME: This should account for current locale
        String s = iso8601string.toUpperCase().replace("Z", "+0530");
        if (s.length() == 19) s+="+0530";
        Log.v(LOG_TAG, s + " len=" + s.length());

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
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

    public static void dateFormatter(final MaterialEditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }

                    if (clean.equals(cleanC))
                        sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {

                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12)
                            mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        if (year < 1900)
                            year = 1900;
                        if (year > 2100)
                            year = 2100;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s-%s-%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editText.setText(current);
                    editText.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static String calculateArrivalTime(Calendar depatureTime, String duration) {
        if (duration.equalsIgnoreCase("0h 0m"))
            return "";
        int minutes = Integer.valueOf(intToMinString(depatureTime.get(Calendar.MINUTE))) + Integer.valueOf(duration.split("h")[1].split("m")[0].trim());
        int hours = Integer.valueOf(intToHrString(depatureTime.get(Calendar.HOUR_OF_DAY), true)) + Integer.valueOf(duration.split("h")[0]);
        if (minutes >= 60) {
            minutes = minutes - 60;
            hours = hours + 1;
            if (hours >= 24)
                hours = hours - 24;

        }
        if (hours >= 24)
            hours = hours - 24;

        String hour = String.valueOf(hours);
        String min = String.valueOf(minutes);
        if (hour.length() == 1) {
            if (min.length() == 1)
                return "0" + hour + ":0" + String.valueOf(minutes);
            else
                return "0" + hour + ":" + String.valueOf(minutes);
        } else {
            if (min.length() == 1)
                return hour + ":0" + String.valueOf(minutes);
            else
                return hour + ":" + String.valueOf(minutes);
        }
    }
}
