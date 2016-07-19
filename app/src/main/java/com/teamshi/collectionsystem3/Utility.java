package com.teamshi.collectionsystem3;

import java.util.Calendar;

/**
 * Created by Alfred on 16/7/14.
 */
public class Utility {
    public static String formatCalendarDateString(Calendar c) {
        return c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
    }

}
