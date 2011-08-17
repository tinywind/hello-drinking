package kr.co.hellodrinking.utillity;

import com.nhn.android.maps.maplib.NGeoPoint;

public class CalculationOfGeoPoint {
	
    public static int distanceBetweenTwoGeoPoints(NGeoPoint p1, NGeoPoint p2){
        double EARTH_R, Rad, radLat1, radLat2, radDist; 
        double distance;
        double ret;

        double lat1 = p1.getLatitude();
        double lon1 = p1.getLongitude();
        double lat2 = p2.getLatitude();
        double lon2 = p2.getLongitude();
        
        EARTH_R = 6371000.0;
        Rad = Math.PI/180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);
        
        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        return  Math.round(Math.round(ret));
        //리턴 값의 단위는 미터(m)이다.
    }
}
