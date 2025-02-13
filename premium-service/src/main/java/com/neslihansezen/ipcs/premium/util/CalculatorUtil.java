package com.neslihansezen.ipcs.premium.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class providing calculation methods related to vehicle premiums.
 * This class is designed to be used as a utility and cannot be instantiated.
 */
@UtilityClass
public class CalculatorUtil {
    public static double calculateKmFactor(String mileage) {
        double factor;
        int mileageInt = Integer.parseInt(mileage);
        if (mileageInt <= 5000) {
            factor = 0.5;
        } else if (mileageInt <= 10000) {
            factor = 1.0;
        } else if (mileageInt <= 20000) {
            factor = 1.5;
        } else {
            factor = 2.0;
        }
        return factor;
    }
}
