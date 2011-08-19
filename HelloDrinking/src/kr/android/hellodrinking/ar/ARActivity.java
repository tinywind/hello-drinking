package kr.android.hellodrinking.ar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.utillity.CalculationOfGeoPoint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.maplib.NGeoPoint;

@SuppressWarnings("deprecation")
public class ARActivity extends Activity implements SensorEventListener,
		NMapLocationManager.OnLocationChangeListener {
	private Compass mCompass;
	private CompassView mCompassView;

	private FrameLayout mFrame;
	private AbsoluteLayout mLayoutPOIs; // TEST
	private static final float VIEWING_ANGLE = 120; // TEST : 시야각
	private NMapLocationManager mMapLocationManager;
	private NGeoPoint mMyGeoPoint;
	private List<POI> mListPOIs;
	private List<View> mViewPOIs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ar);

		mCompass = new Compass(this);
		mFrame = (FrameLayout) findViewById(R.id.ar_frame);
		mLayoutPOIs = (AbsoluteLayout) findViewById(R.id.ar_layout_pois);

		mCompassView = (CompassView) findViewById(R.id.ar_compass);
		mCompassView.setBackgroundBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.bg_speech));
		mCompassView.setOrientationBitmap(BitmapFactory.decodeResource(
				getResources(), R.drawable.ic_angle));
		mCompassView.setCompass(mCompass);

		// //////////////////TEST/////////////////////////
		HelloDrinkingApplication app = (HelloDrinkingApplication) getApplication();
		mListPOIs = app.getListPOIs();

		POI testPOI = new POI(38, 127);
		testPOI.setIconuri("/sdcard/dcim/camera/1302919924624.jpg");
		testPOI.setName("전재형");
		app.addPOI(testPOI);

		mCompass.addListener(this);

		mMyGeoPoint = new NGeoPoint();
		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager.setOnLocationChangeListener(this);

		boolean isMyLocationEnabled = mMapLocationManager
				.enableMyLocation(false);
		if (!isMyLocationEnabled) {
			Intent goToSettings = new Intent(
					Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(goToSettings);
			return;
		}

		mViewPOIs = new ArrayList<View>();
		Iterator<POI> it = mListPOIs.iterator();
		while (it.hasNext()) {
			TextView view = new TextView(this);
			view.setBackgroundColor(Color.GREEN);
			view.setText(it.next().getName());
			mLayoutPOIs.addView(view);
		}
		// ///////////////////////////////////////////////
	}

	// TEST : View 위치 계산
	private double getPresentLocation(float orientation, NGeoPoint myPoint,
			NGeoPoint dst) {
		double distance = CalculationOfGeoPoint.distanceBetweenTwoGeoPoints(
				myPoint, dst);

		double atan = (-1) * Math.atan((dst.getLongitude() - myPoint.getLongitude())
				/ (dst.getLatitude() - myPoint.getLatitude()));

		double degAtan = Math.toDegrees(atan);

		double angleToDst = atan + Math.toRadians(orientation);

		// TEST
		((TextView) mLayoutPOIs.getChildAt(0)).setText(""
				+ Math.toDegrees(angleToDst));

		double lengthOfStandLine = distance * Math.cos(angleToDst);
		double halfOfExpansionScreen = lengthOfStandLine
				* Math.tan(Math.toRadians(VIEWING_ANGLE) / 2);
		double lengthFromExpansionScreenCenterToDst = lengthOfStandLine
				* Math.tan(angleToDst);
		double presentLocation = (halfOfExpansionScreen + lengthFromExpansionScreenCenterToDst)
				/ (halfOfExpansionScreen * 2);
		return presentLocation;
		// 0~1 : 0미만 또는 1이상은 화면 밖
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
		if (mMyGeoPoint.getLatitude() == 0 & mMyGeoPoint.getLongitude() == 0)
			return;

		Iterator<POI> it = mListPOIs.iterator();
		for (int index = 0; it.hasNext(); index++) {
			POI poi = it.next();
			NGeoPoint point = new NGeoPoint(poi.getLongitude(),
					poi.getLatitude());
			double x = getPresentLocation(event.values[0], mMyGeoPoint, point);
			// ((TextView) mLayoutPOIs.getChildAt(index)).setText("" + x);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public boolean onLocationChanged(NMapLocationManager locationManager,
			NGeoPoint myLocation) {
		mMyGeoPoint.set(myLocation.getLongitude(), myLocation.getLatitude());
		return true;
	}

	@Override
	public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
		Toast.makeText(this,
				"Your current location is temporarily unavailable",
				Toast.LENGTH_SHORT).show();
		mMyGeoPoint.set(0, 0);
	}
}