package com.xjc.tool.string.geo;

import org.apache.commons.lang3.StringUtils;

/**
 * GeoUtil.java
 *
 * @author Xujc
 * @date 2021/9/18
 */
public class GeoUtil {

    public boolean checkGeo(String geoStr) {
        if (StringUtils.isBlank(geoStr)) {
            return false;
        }
        String[] geoArray = geoStr.split(",");
        if (geoArray.length != 2) {
            return false;
        }
        String longitudeRegular = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))";
        String latitudeRegular = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))";
        return geoArray[0].trim().matches(longitudeRegular) && geoArray[1].trim().matches(latitudeRegular);
    }
}
