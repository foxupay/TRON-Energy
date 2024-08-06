package com.fox.energy.tron.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TronAmountUtil {

    /**
     * 缩小
     *
     * @param number
     * @return
     */
    public static BigDecimal down(BigDecimal number) {
        return number.divide(new BigDecimal("1000000"), 6, RoundingMode.HALF_UP);
    }


    /**
     * 放大
     *
     * @param number
     * @return
     */
    public static BigDecimal up(BigDecimal number) {
        return new BigDecimal(number.toString()).multiply(new BigDecimal("1000000"));
    }

}
