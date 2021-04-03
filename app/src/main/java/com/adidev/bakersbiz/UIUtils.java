package com.adidev.bakersbiz;
import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.Locale;


public class UIUtils {
    public static String getFormattedNumber(String phoneNumber) {
        return PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());
    }
}
