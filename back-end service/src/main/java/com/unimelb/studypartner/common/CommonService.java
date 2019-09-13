package com.unimelb.studypartner.common;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by xiyang on 2019/9/12
 */
@Service
public class CommonService {
    private static Logger logger = Logger.getLogger(CommonService.class);

    private static final BigDecimal EARTH_RADIUS = new BigDecimal("6371.393");

    public GeoEntity calculateRoundGeoEntity(BigDecimal longitude, BigDecimal latitude, BigDecimal distance) {
        GeoEntity geoEntity = new GeoEntity();

        Double degree = (24901 * 1609) / 360.0; //get degree unit
        double raidusMile = distance.doubleValue();

        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude.doubleValue() * (Math.PI / 180)) + "").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //max longitude
        geoEntity.setLongitudeRight(new BigDecimal(String.valueOf(longitude.doubleValue() + radiusLng)));
        //min longitude
        geoEntity.setLongitudeLeft(new BigDecimal(String.valueOf(longitude.doubleValue() - radiusLng)));

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        //max latitude
        geoEntity.setLatitudeUp(new BigDecimal(String.valueOf(latitude.doubleValue() + radiusLat)));
        //min latitude
        geoEntity.setLatitudeDown(new BigDecimal(String.valueOf(latitude.doubleValue() - radiusLat)));

        return geoEntity;
    }
}
