package com.fox.energy.common.core.domain.tron;

import java.math.BigDecimal;

public class TronBalance {
    private BigDecimal trx;
    private BigDecimal usdt;

    public TronBalance(BigDecimal trx, BigDecimal usdt) {
        this.trx = trx;
        this.usdt = usdt;
    }

    public BigDecimal getTrx() {
        return trx;
    }

    public void setTrx(BigDecimal trx) {
        this.trx = trx;
    }

    public BigDecimal getUsdt() {
        return usdt;
    }

    public void setUsdt(BigDecimal usdt) {
        this.usdt = usdt;
    }
}
