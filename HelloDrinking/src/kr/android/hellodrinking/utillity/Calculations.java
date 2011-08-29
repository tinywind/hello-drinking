package kr.android.hellodrinking.utillity;

import com.nhn.android.maps.maplib.NGeoPoint;

public class Calculations {
	private Calculations() {
	}

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
		// unit: m
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

	public static double[] getLocationOfViewOnScreen(float[] orientations, NGeoPoint stand, NGeoPoint dst) {
		double[] radiansOrient = { Math.toRadians(orientations[0]), Math.toRadians(orientations[1]), Math.toRadians(orientations[2]) };
		// 1) 내가 보는 방향을 기준으로 목표체의 새로운 좌표를 구한다.
		// 초기 y축은 모두 0 : 모든 GPS 좌표의 높이가 동일하다고 가정하는 까닭 : 실제로는 고도 정보도 받을 수 있다.
		// z축 이거 재섭다. z축 바뀌면 x, y 좌표 영향준다.
		double[] coordinate = { dst.getLongitude() - stand.getLongitude(), 0, dst.getLatitude() - stand.getLatitude() };
		coordinate = Calculations.yRotationConvert(coordinate, radiansOrient[0] + radiansOrient[2]);
		coordinate = Calculations.xRotationConvert(coordinate, radiansOrient[1] - radiansOrient[2] - Math.toRadians(-90));
		coordinate = Calculations.zRotationConvert(coordinate, radiansOrient[2]);// 이해안됨

		if (coordinate[2] < 0)
			return null;

		// 2) 시야각을 고려한 화면 내 상의 위치
		// 기본 공식은 (좌표/거리)이다. 이중 z축 좌표는 화면상에 표현되지 않고, 단지 거리 계측에만 사용되는 변수이다.
		// 3차원 좌표상 거리가 1일 때, 0 이면 화면의 중앙이다.
		// x축은 -sin15 ~ sin15, 그리고 y축은 -sin30 ~ sin30 까지 표현 (왜냐면 상하 시야각이 60도, 좌우 시야각은 30도니까);
		double[] result = { 0, 0 };
		result[0] = coordinate[0] / Calculations.distanceFromZero(coordinate);
		result[1] = coordinate[1] / Calculations.distanceFromZero(coordinate);

		return result;
	}
}
