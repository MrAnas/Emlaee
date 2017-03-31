package com.app.emlaee.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;

import com.app.emlaee.R;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import dmax.dialog.SpotsDialog;

/**
 * Created by sahil on 1/10/2017.
 */

public class Utils {

    public static AlertDialog CustomProgressbarDialog(Context context) {
        AlertDialog progressDialog;
        progressDialog = new SpotsDialog(context, R.style.CustomDialog);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static String fixEncodingUnicode(String response) {
        String str = "";
        try {
            // displayed as    desired encoding
            str = new String(response.getBytes("windows-1254"), "UTF-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        String decodedStr = Html.fromHtml(str).toString();
        return decodedStr;
    }


    static final String decode(final String in)
    {
        String working = in;
        int index;
        index = working.indexOf("\\u");
        while(index > -1)
        {
            int length = working.length();
            if(index > (length-6))break;
            int numStart = index + 2;
            int numFinish = numStart + 4;
            String substring = working.substring(numStart, numFinish);
            int number = Integer.parseInt(substring,16);
            String stringStart = working.substring(0, index);
            String stringEnd   = working.substring(numFinish);
            working = stringStart + ((char)number) + stringEnd;
            index = working.indexOf("\\u");
        }
        return working;
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
