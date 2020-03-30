package com.nanotechnology.covid_19statistic.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat



/**
 * Take the Long milliseconds returned by the Covid-19 Api,
 * and convert it to a nicely formatted string for displaying.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the nmotny
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
            .format(systemTime).toString()
}
