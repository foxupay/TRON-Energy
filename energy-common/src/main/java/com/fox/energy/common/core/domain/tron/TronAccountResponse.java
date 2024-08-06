package com.fox.energy.common.core.domain.tron;

public class TronAccountResponse {

    /**
     * 有效性
     */
    private boolean validate;

    /**
     * 是否激活
     */
    private boolean activated;

    /**
     * 总量
     */
    private int energyLimit;

    /**
     * 能量百分比
     */
    private double energyPercentage;

    /**
     * 剩余
     */
    private int enableEnergy;

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public int getEnergyLimit() {
        return energyLimit;
    }

    public void setEnergyLimit(int energyLimit) {
        this.energyLimit = energyLimit;
    }

    public double getEnergyPercentage() {
        return energyPercentage;
    }

    public void setEnergyPercentage(double energyPercentage) {
        this.energyPercentage = energyPercentage;
    }

    public int getEnableEnergy() {
        return enableEnergy;
    }

    public void setEnableEnergy(int enableEnergy) {
        this.enableEnergy = enableEnergy;
    }
}
