package kr.android.hellodrinking.utillity;

import com.nhn.android.maps.maplib.NGeoPoint;

public class Calculations {

	public static int distanceBetweenTwoGeoPoints(NGeoPoint p1, NGeoPoint p2) {
		double EARTH_R, Rad, radLat1, radLat2, radDist;
		double distance;
		double ret;

		double lat1 = p1.getLatitude();
		double lon1 = p1.getLongitude();
		double lat2 = p2.getLatitude();
		double lon2 = p2.getLongitude();

		EARTH_R = 6371000.0;
		Rad = Math.PI / 180;
		radLat1 = Rad * lat1;
		radLat2 = Rad * lat2;
		radDist = Rad * (lon1 - lon2);

		distance = Math.sin(radLat1) * Math.sin(radLat2);
		distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
		ret = EARTH_R * Math.acos(distance);

		return Math.round(Math.round(ret));
		// 단위: m
	}

	public static double distanceBetweenTwo3DPoint(double[] p1, double[] p2) {
		return Math.sqrt(Math.pow((p1[0] - p2[0]), 2) + Math.pow((p1[1] - p2[1]), 2) + Math.pow((p1[2] - p2[2]), 2));
	}
	public static double distanceFromZero(double[] p) {
		return Math.sqrt(Math.pow((p[0]), 2) + Math.pow((p[1]), 2) + Math.pow((p[2]), 2));
	}

	public static double[] zRotationConvert(double[] coordinate, double angle) {
		double[] convert = { 0, 0, 0 };
		convert[0] = coordinate[0] * Math.cos(angle) - coordinate[1] * Math.sin(angle);
		convert[1] = coordinate[0] * Math.sin(angle) + coordinate[1] * Math.cos(angle);
		convert[2] = coordinate[2];
		return convert;
	}

	public static double[] xRotationConvert(double[] coordinate, double angle) {
		double[] convert = { 0, 0, 0 };
		convert[0] = coordinate[0];
		convert[1] = coordinate[1] * Math.cos(angle) - coordinate[2] * Math.sin(angle);
		convert[2] = coordinate[1] * Math.sin(angle) + coordinate[2] * Math.cos(angle);
		return convert;
	}

	public static double[] yRotationConvert(double[] coordinate, double angle) {
		double[] convert = { 0, 0, 0 };
		convert[0] = coordinate[0] * Math.cos(angle) - coordinate[2] * Math.sin(angle);
		convert[1] = coordinate[1];
		convert[2] = coordinate[0] * Math.sin(angle) + coordinate[2] * Math.cos(angle);
		return convert;
	}
}
