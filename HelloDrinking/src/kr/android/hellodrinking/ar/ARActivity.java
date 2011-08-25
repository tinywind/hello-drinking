package kr.android.hellodrinking.ar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.activity.FrameActivity;
import kr.android.hellodrinking.activity.UserInfoDialog;
import kr.android.hellodrinking.sensor.Compass;
import kr.android.hellodrinking.sensor.CompassView;
import kr.android.hellodrinking.utillity.Calculations;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.maplib.NGeoPoint;

@SuppressWarnings("deprecation")
public class ARActivity extends FrameActivity implements SensorEventListener {
	private static final int RE_CALC_RATE = 1;
	private static final double HORIZONAL_VIEWING_ANGLE = Math.toRadians(30);
	private static final double VERTICAL_VIEWING_ANGLE = Math.toRadians(60);
	private static final double[] STANDARD_COORDINATE = { 0, 0, 1 };
	private static final double LEFT_ON_SCREEN = Calculations.yRotationConvert(STANDARD_COORDINATE, HORIZONAL_VIEWING_ANGLE / 2)[0];
	private static final double UP_ON_SCREEN = Calculations.xRotationConvert(STANDARD_COORDINATE, VERTICAL_VIEWING_ANGLE / 2)[1];
	private static final int POIOVERLAY_IMAGE_WIDTH = 150;
	private static final int POIOVERLAY_IMAGE_HEIGHT = 150;

	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;

	private Compass mCompass;
	private CompassView mCompassView;
	private AbsoluteLayout mLayoutPOIs;
	private List<POI> mListPOIs;
	private float[] mArrayPriorOrient = { 0, 0, 0 };

	protected void loadContent() {
		mInflater.inflate(R.layout.ar, mViewgroup);
		mButtonAR.setClickable(false);

		mCompass = new Compass(this);
		mLayoutPOIs = (AbsoluteLayout) findViewById(R.id.ar_layout_pois);

		mCompassView = (CompassView) findViewById(R.id.ar_compass);
		mCompassView.setBackgroundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_compass));
		mCompassView.setOrientationBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_angle));
		mCompassView.setCompass(mCompass);

		SCREEN_WIDTH = getWindowManager().getDefaultDisplay().getWidth();
		SCREEN_HEIGHT = getWindowManager().getDefaultDisplay().getHeight();

		mCompass.addListener(this);

		setPOIOverlays();
	}

	private void setPOIOverlays() {
		List<POI> list = ((HelloDrinkingApplication) getApplication()).getListPOIs();
		mListPOIs = new ArrayList<POI>(list.size());
		mLayoutPOIs.removeAllViews();

		Iterator<POI> it = list.iterator();
		while (it.hasNext()) {
			final POI poi = it.next();
			mListPOIs.add(poi);

			ImageView view = new ImageView(this);
			view.setBackgroundColor(Color.GREEN);
			Bitmap bitmap = BitmapFactory.decodeFile(poi.getImageFilePath());
			view.setImageBitmap(Bitmap.createScaledBitmap(bitmap, POIOVERLAY_IMAGE_WIDTH, POIOVERLAY_IMAGE_HEIGHT, true));

			AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, SCREEN_WIDTH,
					SCREEN_HEIGHT);
			view.setLayoutParams(param);

			mLayoutPOIs.addView(view);

			view.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(ARActivity.this, UserInfoDialog.class);
					intent.putExtra("kr.android.POI", poi);
					startActivity(intent);
				}
			});
		}
	}

	private double[] getLocationOfViewOnScreen(float[] orientations, NGeoPoint stand, NGeoPoint dst) {
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
		// x축은 -sin15 ~ sin15, 그리고 y축은 -sin30 ~ sin30 까지 표현 (왜냐면 상하 시야각이 60도, 좌우
		// 시야각은 30도니까);
		double[] result = { 0, 0 };
		result[0] = coordinate[0] / Calculations.distanceFromZero(coordinate);
		result[1] = coordinate[1] / Calculations.distanceFromZero(coordinate);

		return result;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mCompass.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mCompass.stop();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (myLocation.getLatitude() == 0 & myLocation.getLongitude() == 0)
			return;
		if (myLocation == null)
			return;

		if (Math.abs(mArrayPriorOrient[0] - event.values[0]) < RE_CALC_RATE && Math.abs(mArrayPriorOrient[1] - event.values[1]) < RE_CALC_RATE
				&& Math.abs(mArrayPriorOrient[1] - event.values[1]) < RE_CALC_RATE)
			return;

		mArrayPriorOrient[0] = event.values[0];
		mArrayPriorOrient[1] = event.values[1];
		mArrayPriorOrient[2] = event.values[2];

		Iterator<POI> it = mListPOIs.iterator();
		for (int index = 0; it.hasNext(); index++) {
			POI poi = it.next();
			double[] point = getLocationOfViewOnScreen(event.values, myLocation, new NGeoPoint(poi.getLongitude(), poi.getLatitude()));
			if (point == null)
				continue;
			View view = mLayoutPOIs.getChildAt(index);
			int width = AbsoluteLayout.LayoutParams.WRAP_CONTENT;
			int height = AbsoluteLayout.LayoutParams.WRAP_CONTENT;
			int x = (int) (SCREEN_WIDTH * (point[0] - LEFT_ON_SCREEN * ((90 - event.values[2]) / 90)) - UP_ON_SCREEN * (event.values[2] / 90));
			int y = (int) (SCREEN_HEIGHT * (point[1] - UP_ON_SCREEN * ((90 - event.values[2]) / 90)) - LEFT_ON_SCREEN * (event.values[2] / 90));
			AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(width, height, x, y);
			view.setLayoutParams(param);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}