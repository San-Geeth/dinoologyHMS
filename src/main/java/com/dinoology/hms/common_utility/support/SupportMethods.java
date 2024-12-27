package com.dinoology.hms.common_utility.support;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author: sangeethnawa
 * Created: 12/8/2024 9:31 PM
 */
@Component
public class SupportMethods {

    @Value("${app.general.country-code}")
    private String countryCode;

    private static String staticCountryCode;

    @PostConstruct
    public void init() {
        staticCountryCode = countryCode;
    }

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

    public static String formatTo10Digit(Integer number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }

        return String.format("%010d", number);
    }

    public static Long formatContact(Long number) {
        if (number == null || number < 0) {
            throw new IllegalArgumentException("Contact number cannot be null or negative");
        }

        // Concatenate country code and contact number as a Long
        String formattedContact = staticCountryCode  + String.valueOf(number);

        // Return the result as a Long
        return Long.valueOf(formattedContact);
    }
}
