package com.njit.buddy.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author toyknight 11/20/2015.
 */
public class DateParser {

    public String toString(long timestamp) {
        Date date = new Date(timestamp);
        return SimpleDateFormat.getInstance().format(date);
    }

}
