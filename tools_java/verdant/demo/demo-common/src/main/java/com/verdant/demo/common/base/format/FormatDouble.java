package com.verdant.demo.common.base.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2016/4/13.
 */
public class FormatDouble {
    double f = 111231.5585;
    /**
     * 使用String.format打印
     */
    public void accurate1() {
        System.out.println(String.format("%.2f", f));
    }

    /**
     * 使用DecimalFormat
     */
    public void accurate2() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(f));
    }

    /**
     * 使用NumberFormat
     */
    public void accurate3() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(f));
    }

    /**
     * 使用BigDecimal
     */
    public void accurate4() {
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }


    public static void main(String[] args) {
        FormatDouble df = new FormatDouble();
        df.accurate1();
        df.accurate2();
        df.accurate3();
        df.accurate4();
    }
}
