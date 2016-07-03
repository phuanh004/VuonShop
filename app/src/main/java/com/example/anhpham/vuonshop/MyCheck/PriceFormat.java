package com.example.anhpham.vuonshop.MyCheck;

import java.text.DecimalFormat;

/**
 * Created by Anh Pham on 20/06/2016.
 */

public class PriceFormat {

    public PriceFormat() {
    }

    private static String priceWithDecimal(Float price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(price);
    }

    private static String priceWithoutDecimal(Float price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }

    public static String formatDecimal(Float price) {
        String toShow = priceWithoutDecimal(price);
        if (toShow.indexOf(".") > 0) {
            return priceWithDecimal(price);
        } else {
            return priceWithoutDecimal(price);
        }
    }
}
