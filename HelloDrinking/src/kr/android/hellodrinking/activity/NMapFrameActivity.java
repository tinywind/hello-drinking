package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapLocationManager.OnLocationChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;

public abstract class NMapFrameActivity extends NMapActivity implements OnClickListener, OnLocationChangeListener {
	protected LayoutInflater mInflater;
	protected ViewGroup mViewgroup;
	protected ImageButton mButtonPosts;
	protected ImageButton mButtonMap;
	protected ImageButton mButtonAR;
	protected ImageButton mButtonMember;
	protected Button mButtonRefresh;
	protected Button mButtonPost;
	protected EditText mEditDistance;
	protected NMapLocationManager mMapLocationManager;
	protected NGeoPoint myLocation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.frame);

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mViewgroup = (ViewGroup) findViewById(R.id.frame_frame_contents);

		mButtonPosts = (ImageButton) findViewById(R.id.frame_button_posts);
		mButtonMap = (ImageButton) findViewById(R.id.frame_button_map);
		mButtonAR = (ImageButton) findViewById(R.id.frame_button_ar);
		mButtonMember = (ImageButton) findViewById(R.id.frame_button_member);

		mButtonPosts.setOnClickListener(this);
		mButtonMap.setOnClickListener(this);
		mButtonAR.setOnClickListener(this);
		mButtonMember.setOnClickListener(this);

		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager.setOnLocationChangeListener(this);

		myLocation = new NGeoPoint(0, 0);

		mButtonPost = (Button) findViewById(R.id.frame_button_post);
		mButtonRefresh = (Button) findViewById(R.id.frame_button_refresh);
		mEditDistance = (EditText) findViewById(R.id.frame_edit_distance);

		mEditDistance.setText(HelloDrinkingApplication.DEFAULT_SEARCH_DISTANCE + "");

		mButtonPost.setOnClickListener(this);
		mButtonRefresh.setOnClickListener(this);

		loadContent();
	}

	@Override
	protected void onResume() {
		startMyLocation();
		super.onResume();
	}

	@Override
	protected void onStop() {
		stopMyLocation();
		super.onStop();
	}

	protected void stopMyLocation() {
		mMapLocationManager.disableMyLocation();
	}

	protected void startMyLocation() {
		boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false);
		if (!isMyLocationEnabled) {
			Toast.makeText(this, "Please enable a My Location source in system settings", Toast.LENGTH_LONG).show();
			Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(goToSettings);
			return;
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.frame_button_posts) {
			startActivity(new Intent(this, PostsActivity.class));
		} else if (view.getId() == R.id.frame_button_map) {
			startActivity(new Intent(this, MapActivity.class));
		} else if (view.getId() == R.id.frame_button_ar) {
			startActivity(new Intent(this, ARActivity.class));
		} else if (view.getId() == R.id.frame_button_member) {
			startActivity(new Intent(this, MemberinfoActivity.class));
		} else if (view.getId() == R.id.frame_button_post) {
			Intent intent = new Intent(this, PostActivity.class);
			intent.putExtra("kr.android.hellodrinking.MYLOCATION_LONGITUDE", myLocation.getLongitude());
			intent.putExtra("kr.android.hellodrinking.MYLOCATION_LATITUDE", myLocation.getLatitude());
			startActivity(intent);
		} else if (view.getId() == R.id.frame_button_refresh) {
			((HelloDrinkingApplication) getApplication()).refreshListPosts(myLocation, Integer.parseInt(mEditDistance.getText().toString()));
			presentPosts();
		}
	}

	@Override
	public boolean onLocationChanged(NMapLocationManager paramNMapLocationManager, NGeoPoint point) {
		myLocation = point;
		presentLocationChanged();
		return true;
	}

	@Override
	public void onLocationUpdateTimeout(NMapLocationManager paramNMapLocationManager) {
		stopMyLocation();
		Toast.makeText(this, "Your current location is temporarily unavailable", Toast.LENGTH_LONG).show();
		myLocation.set(0, 0);
	}

	protected abstract void loadContent();

	protected abstract void presentPosts();

	protected abstract void presentLocationChanged();
}
