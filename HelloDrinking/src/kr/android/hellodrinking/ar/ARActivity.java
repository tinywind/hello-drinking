package kr.android.hellodrinking.ar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.maplib.NGeoPoint;

public class ARActivity extends Activity implements SensorEventListener, NMapLocationManager.OnLocationChangeListener{
	private Compass mCompass;
	private CompassView mCompassView;
	
	private FrameLayout mFrame;
	private AbsoluteLayout mPOIIcons; //TEST
	private static final float VIEWING_ANGLE = 45; //TEST : 사람 시야각 약 45도
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
		
		mCompassView = (CompassView) findViewById(R.id.ar_compass);
		mCompassView.setBackgroundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_speech));
		mCompassView.setOrientationBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_angle));
		mCompassView.setCompass(mCompass);
		
		////////////////////TEST/////////////////////////
		HelloDrinkingApplication app = (HelloDrinkingApplication) getApplication();
		mListPOIs = app.getListPOIs();
		
		POI testPOI = new POI(126.8989265, 37.4859410);
		testPOI.setIconuri("/sdcard/dcim/camera/1302919924624.jpg");	
		app.addPOI(testPOI);
		
		mCompass.addListener(this);
		
		mMyGeoPoint = new NGeoPoint();
		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager.setOnLocationChangeListener(this);
		
		boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false);
		if (!isMyLocationEnabled) {
			Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(goToSettings);
			return;
		}

		mViewPOIs = new ArrayList<View>();
		Iterator<POI> it = mListPOIs.iterator();
		while(it.hasNext()){
			View view = new View(this);
		}
		/////////////////////////////////////////////////
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
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public boolean onLocationChanged(NMapLocationManager locationManager,NGeoPoint myLocation) {
		mMyGeoPoint.set(myLocation.getLongitude(),myLocation.getLatitude());
		return true;
	}
	
	@Override
	public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
		Toast.makeText(this, "Your current location is temporarily unavailable", Toast.LENGTH_SHORT).show();
		mMyGeoPoint.set(0,0);
	}
}