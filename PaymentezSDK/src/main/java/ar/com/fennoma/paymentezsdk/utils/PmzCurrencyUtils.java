package ar.com.fennoma.paymentezsdk.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class PmzCurrencyUtils {

    public static String formatPrice(Long price) {
        long value = 0;
        if(price != null) {
            value = price;
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "ES"));
        return "$ ".concat(new DecimalFormat("#,###,##0", symbols).format(value));
    }

    public static String formatPrice(Double price) {
        double value = 0;
        if(price != null) {
            value = price;
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "ES"));
        return "$ ".concat(new DecimalFormat("#,###,##0", symbols).format(value));
    }

}
