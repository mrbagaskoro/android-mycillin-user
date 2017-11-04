package com.mycillin.user.util;

import android.app.Activity;

import com.mycillin.user.R;

import java.util.HashMap;

/**
 * Created by mrbagaskoro on 04-Nov-17.
 */

public class DateFormatter {
    public static final String KEY_DD = "DD";
    public static final String KEY_MM = "MM";
    public static final String KEY_YY = "YY";
    public static final String KEY_HH = "HH";
    public static final String KEY_II = "II";
    public static final String KEY_SS = "SS";

    private Activity activity;
    private String date;
    private String time;

    public DateFormatter(Activity activity, String fullDate) {
        this.activity = activity;
        this.date = fullDate.split(" ")[0];
        this.time = fullDate.split(" ")[1];
    }

    public HashMap medicalRecordFragmentDateFormat() {
        String day = date.split("-")[2];
        String month = date.split("-")[1];
        String year = date.split("-")[0];

        HashMap<String, String> result = new HashMap<>();
        result.clear();
        result.put(KEY_DD, day);
        result.put(KEY_MM, monthNameConverter(month));
        result.put(KEY_YY, year);

        return result;
    }

    private String monthNameConverter(String mm) {
        String result = "";
        switch (mm) {
            case "01":
                result = activity.getString(R.string.dateFormater_jan);
                break;
            case "02":
                result = activity.getString(R.string.dateFormater_feb);
                break;
            case "03":
                result = activity.getString(R.string.dateFormatter_mar);
                break;
            case "04":
                result = activity.getString(R.string.dateFormatter_apr);
                break;
            case "05":
                result = activity.getString(R.string.dateFormatter_may);
                break;
            case "06":
                result = activity.getString(R.string.dateFormatter_jun);
                break;
            case "07":
                result = activity.getString(R.string.dateFormatter_jul);
                break;
            case "08":
                result = activity.getString(R.string.dateFormatter_aug);
                break;
            case "09":
                result = activity.getString(R.string.dateFormatter_sep);
                break;
            case "10":
                result = activity.getString(R.string.dateFormatter_oct);
                break;
            case "11":
                result = activity.getString(R.string.dateFormatter_nov);
                break;
            case "12":
                result = activity.getString(R.string.dateFormatter_dec);
                break;
        }

        return result;
    }
}
