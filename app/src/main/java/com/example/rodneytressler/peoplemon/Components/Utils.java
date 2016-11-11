package com.example.rodneytressler.peoplemon.Components;

import java.util.Locale;

/**
 * Created by rodneytressler on 11/2/16.
 */

public class Utils {
    public static String formatDouble(Double number) {
        String prefix = number < 0 ? "-$" : "$"; //ternary that makes sure if it's negative that it puts the negative before the $.
        return prefix + String.format(Locale.US, "%.2f", Math.abs(number)); //rounds to two decimal points.
        // $100.00 or -$9.87

    }
}
