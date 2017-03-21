package com.verdant.dm.entity;

import java.io.Serializable;

public class DataPoint implements Serializable {

    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Constructor
     *
     * @param x the x value
     * @param y the y value
     */
    public DataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
}