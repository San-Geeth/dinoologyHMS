package com.dinoology.hms.common_utility.support;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 9:31 PM
 */
public class SupportMethods {
    public static String formatNumber(int number) {
        if (number > 9999 || number < 0)
            return String.valueOf(number);
        if (number == 0) {
            return "0000";
        } else if (number <= 9) {
            return "000" + (number);
        } else if (number <= 99) {
            return "00" + (number);
        } else if (number <= 999) {
            return "0" + (number);
        } else {
            return String.valueOf(number);
        }
    }
}
