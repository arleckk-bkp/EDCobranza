package com.arleckk.edcobranza.global;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by arleckk on 12/07/16.
 */
public class Utilities {

    public static String account = null;

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
    }

    public static String getDatePhoto() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
    }

    public static String getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return hour  + ":" +
                minute + ":" +
                second;
    }

    public static boolean isValid(String s) {
        if(s != null) {
            if(!s.equals("")){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean areValid(String[] s) {
        boolean b = true;
        for(int i = 0; i < s.length; i++) {
            b = isValid(s[i]);
            if(!b) {
                break;
            }
        }
        return b;
    }

    public static boolean isValidNumber(String number) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher matcher = pattern.matcher(number);
        Log.v("number_debug",number);
        if(matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static void updateLabel(EditText editText, Calendar myCalendar) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
    }

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}
