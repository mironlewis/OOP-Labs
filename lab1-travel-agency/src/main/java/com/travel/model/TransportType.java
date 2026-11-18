package com.travel.model;

public enum TransportType {
    BUS(40.0),
    TRAIN(70.0),
    PLANE(250.0),
    SHIP(300.0);

    private final double baseCost;

    TransportType(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getBaseCost() {
        return baseCost;
    }
}