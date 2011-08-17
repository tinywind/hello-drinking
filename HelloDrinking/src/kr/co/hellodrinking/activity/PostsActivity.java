package kr.co.hellodrinking.activity;

import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.maplib.NGeoPoint;

import kr.co.hellodrinking.R;
import android.content.Intent;
import android.provider.Settings;
import android.widget.TextView;

public class PostsActivity extends FrameActivity{
	private TextView text;
	private NMapLocationManager mMapLocationManager;
	
	protected void loadContent() {
		mInflater.inflate(R.layout.posts, mViewgroup);
		mButtonPosts.setClickable(false);
		
		text = (TextView) findViewById(R.id.posts_textview);
		
		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
		
		boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false);
		if (!isMyLocationEnabled) {
			text.setText("Please enable a My Location source in system settings");
			Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(goToSettings);
			return;
		}
	}
	
	private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {
		public boolean onLocationChanged(NMapLocationManager locationManager,NGeoPoint myLocation) {
			text.setText("My Location:("+myLocation.getLongitude()+","+myLocation.getLatitude()+")");
			return true;
		}
		public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
			text.setText("Your current location is temporarily unavailable");
		}
	};
}