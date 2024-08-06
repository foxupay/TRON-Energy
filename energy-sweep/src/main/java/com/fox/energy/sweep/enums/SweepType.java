package com.fox.energy.sweep.enums;

public enum SweepType {
    TRX(1),
    USDT(2),
    ;

    public static SweepType getType(int type) {
        for (SweepType sweepType : values()) {
            if (sweepType.getType() == type) {
                return sweepType;
            }
        }
        return null;
    }

    SweepType(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }
}
