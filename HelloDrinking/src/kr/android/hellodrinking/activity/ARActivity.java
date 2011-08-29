package kr.android.hellodrinking.activity;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.nhn.android.maps.maplib.NGeoPoint;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.sensor.Compass;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.utillity.Calculations;
import kr.android.hellodrinking.utillity.GraphicUtils;
import kr.android.hellodrinking.view.CompassView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class ARActivity extends FrameActivity implements SensorEventListener {
	private static final int RE_CALC_RATE = 1;
	private static final double HORIZONAL_VIEWING_ANGLE = Math.toRadians(30);
	private static final double VERTICAL_VIEWING_ANGLE = Math.toRadians(60);
	private static final double[] STANDARD_COORDINATE = { 0, 0, 1 };
	private static final double LEFT_ON_SCREEN = Calculations.yRotationConvert(STANDARD_COORDINATE, HORIZONAL_VIEWING_ANGLE / 2)[0];
	private static final double UP_ON_SCREEN = Calculations.xRotationConvert(STANDARD_COORDINATE, VERTICAL_VIEWING_ANGLE / 2)[1];

	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;

	private Compass mCompass;
	private CompassView mCompassView;
	private AbsoluteLayout mLayoutPOIs;
	private float[] mArrayPriorOrient = { 0, 0, 0 };

	@Override
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

		if (((HelloDrinkingApplication) getApplication()).getListPosts().size() < 1) {
			((HelloDrinkingApplication) getApplication()).refreshListPosts(myLocation, Integer.parseInt(mEditDistance.getText().toString()));
		}
		presentPosts();
	}

	@Override
	protected void presentPosts() {
		List<PostBean> posts = ((HelloDrinkingApplication) getApplication()).getListPosts();
		mLayoutPOIs.removeAllViews();

		for (int index = 0; index < posts.size(); index++) {
			final PostBean post = posts.get(index);

			File file = new File(post.getImageFilePath());
			Bitmap bitmap = null;
			if (!file.getName().equals("")) {
				File imagefile = GraphicUtils.createImageFile(this, post.getBuffer(), file);
				bitmap = GraphicUtils.createBitmapFromImageFile(imagefile);
				post.setImageFilePath(imagefile.getAbsolutePath());
			}

			ImageView view = new ImageView(this);
			view.setImageDrawable(GraphicUtils.getOverlayDrawableFromBitmap(bitmap));
			AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, SCREEN_WIDTH,
					SCREEN_HEIGHT);
			view.setLayoutParams(param);
			mLayoutPOIs.addView(view);
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					Intent intent = new Intent(ARActivity.this, PostActivity.class);
					intent.putExtra("kr.android.hellodrinking.MYLOCATION_LONGITUDE", myLocation.getLongitude());
					intent.putExtra("kr.android.hellodrinking.MYLOCATION_LATITUDE", myLocation.getLatitude());
					startActivity(intent);
				}
			});
		}
	}

	@Override
	protected void presentLocationChanged() {

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

		Iterator<PostBean> it = ((HelloDrinkingApplication) getApplication()).getListPosts().iterator();
		for (int index = 0; it.hasNext(); index++) {
			PostBean post = it.next();
			double[] point = Calculations.getLocationOfViewOnScreen(event.values, myLocation, new NGeoPoint(post.getLongitude(), post.getLatitude()));
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
