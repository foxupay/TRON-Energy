package com.fox.energy.lease.channel.tool;

public enum ChannelPayEnums {
    PENDING(1),
    SUCCESS(2),
    FAIL(3),
    ;

    ChannelPayEnums(Integer state) {
        this.state = state;
    }

    private Integer state;

    public Integer getState() {
        return state;
    }
}
