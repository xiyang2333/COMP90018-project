package com.unimelb.studypartner.common;

import java.math.BigDecimal;

/**
 * Created by xiyang on 2019/9/12
 */
public class GeoEntity {
    private BigDecimal longitudeRight;
    private BigDecimal longitudeLeft;
    private BigDecimal latitudeUp;
    private BigDecimal latitudeDown;

    public BigDecimal getLongitudeRight() {
        return longitudeRight;
    }

    public void setLongitudeRight(BigDecimal longitudeRight) {
        this.longitudeRight = longitudeRight;
    }

    public BigDecimal getLongitudeLeft() {
        return longitudeLeft;
    }

    public void setLongitudeLeft(BigDecimal longitudeLeft) {
        this.longitudeLeft = longitudeLeft;
    }

    public BigDecimal getLatitudeUp() {
        return latitudeUp;
    }

    public void setLatitudeUp(BigDecimal latitudeUp) {
        this.latitudeUp = latitudeUp;
    }

    public BigDecimal getLatitudeDown() {
        return latitudeDown;
    }

    public void setLatitudeDown(BigDecimal latitudeDown) {
        this.latitudeDown = latitudeDown;
    }
}
