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

package com.greplr.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by championswimmer on 4/7/15.
 */
public class DateTimeUtils {

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
        String s = iso8601string.toUpperCase().replace("Z", "+00:00");
        try {
            s = s.substring(0, 27) + s.substring(28);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
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

}
