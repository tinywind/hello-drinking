package kr.android.hellodrinking.activity;

import java.io.File;
import java.util.List;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.dialog.PostInfoDialog;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.utillity.GraphicUtils;
import kr.android.hellodrinking.view.CompassView;
import kr.android.hellodrinking.view.MapContainerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapViewerResourceProvider;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

public class MapActivity extends NMapFrameActivity {
	private static final String LOG_TAG = "NMapViewer";
	private static final boolean DEBUG = false;
	private static final String API_KEY = "6dff5a14ec0abea130009332080cc6fc";
	private MapContainerView mMapContainerView;
	private NMapView mMapView;
	private NMapController mMapController;
	private static final NGeoPoint NMAP_LOCATION_DEFAULT = new NGeoPoint(126.978371, 37.5666091);
	private static final int NMAP_ZOOMLEVEL_DEFAULT = 11;
	private static final String KEY_ZOOM_LEVEL = "NMapViewer.zoomLevel";
	private static final String KEY_CENTER_LONGITUDE = "NMapViewer.centerLongitudeE6";
	private static final String KEY_CENTER_LATITUDE = "NMapViewer.centerLatitudeE6";
	private SharedPreferences mPreferences;
	private NMapOverlayManager mOverlayManager;
	private NMapMyLocationOverlay mMyLocationOverlay;
	private NMapCompassManager mMapCompassManager;
	private NMapViewerResourceProvider mMapViewerResourceProvider;
	private NMapPOIdataOverlay mFloatingPOIdataOverlay;
	private NMapPOIitem mFloatingPOIitem;
	private NMapPOIdataOverlay mPoiDataOverlay;
	private CompassView mCompassView;

	@Override
	protected void loadContent() {
		mInflater.inflate(R.layout.map, mViewgroup);
		mButtonMap.setClickable(false);

		mMapView = new NMapView(this);
		mMapView.setApiKey(API_KEY);
		mMapView.setClickable(true);
		mMapView.setEnabled(true);
		mMapView.setFocusable(true);
		mMapView.setFocusableInTouchMode(true);
		mMapView.requestFocus();
		mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);
		mMapView.setOnMapViewDelegate(onMapViewTouchDelegate);
		mMapView.setBuiltInZoomControls(true, new NMapView.LayoutParams(NMapView.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.WRAP_CONTENT,
				NMapView.LayoutParams.BOTTOM_RIGHT));

		mMapController = mMapView.getMapController();
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
		setMapDataProviderListener(onDataProviderListener);
		mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
		mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);

		mMapContainerView = (MapContainerView) findViewById(R.id.map_mapcontainer);
		mMapContainerView.addView(mMapView);
		mMapContainerView.setInit(mOverlayManager);

		mMapCompassManager = new NMapCompassManager(this);
		mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

		mCompassView = (CompassView) findViewById(R.id.map_compass);
		mCompassView.setBackgroundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_compass));

		startMyLocation();
		presentPosts();
	}

	@Override
	protected void presentPosts() {
		List<PostBean> posts = ((HelloDrinkingApplication) getApplication()).getListPosts();

		NMapPOIdata poiData = new NMapPOIdata(posts.size(), mMapViewerResourceProvider);
		poiData.beginPOIdata(posts.size());
		for (int index = 0; index < posts.size(); index++) {
			PostBean post = posts.get(index);
			File file = new File(post.getImageFilePath());
			Bitmap bitmap = null;
			if (!file.getName().equals("")) {
				File imagefile = GraphicUtils.createImageFile(this, post.getBuffer(), file);
				bitmap = GraphicUtils.createBitmapFromImageFile(imagefile);
				post.setImageFilePath(imagefile.getAbsolutePath());
			}
			Drawable image = GraphicUtils.getOverlayDrawableFromBitmap(bitmap);
			poiData.addPOIitem(new NGeoPoint(post.getLongitude(), post.getLatitude()), post.getId(), image, post);
		}
		poiData.endPOIdata();

		mPoiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
		mPoiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
	}

	@Override
	protected void presentLocationChanged() {
	}
	
	@Override
	protected void onDestroy() {
		saveInstanceState();
		super.onDestroy();
	}

	@Override
	protected void startMyLocation() {
		if (mMyLocationOverlay != null) {
			if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
				mOverlayManager.addOverlay(mMyLocationOverlay);
			}
			if (mMapLocationManager.isMyLocationEnabled()) {
				if (!mMapView.isAutoRotateEnabled()) {
					mMyLocationOverlay.setCompassHeadingVisible(true);
					mMapCompassManager.enableCompass();
					mMapView.setAutoRotateEnabled(false, false);
					mMapContainerView.requestLayout();
				} else {
					stopMyLocation();
				}
				mMapView.postInvalidate();
			} else {
				super.startMyLocation();
			}
		}
	}

	@Override
	protected void stopMyLocation() {
		if (mMyLocationOverlay != null) {
			mMapLocationManager.disableMyLocation();
			if (mMapView.isAutoRotateEnabled()) {
				mMyLocationOverlay.setCompassHeadingVisible(false);
				mMapCompassManager.disableCompass();
				mMapView.setAutoRotateEnabled(false, false);
				mMapContainerView.requestLayout();
			}
		}
	}
	

	private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {
		public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
		}

		public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
			if (item == null)
				return;

			PostBean post = (PostBean) item.getTag();
			Intent intent = new Intent(MapActivity.this, PostInfoDialog.class);
			intent.putExtra("kr.android.hellodrinking.POST", post);
			startActivity(intent);
		}
	};
	

	private final NMapActivity.OnDataProviderListener onDataProviderListener = new NMapActivity.OnDataProviderListener() {
		public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {
			if (DEBUG)
				Log.i(LOG_TAG, "onReverseGeocoderResponse: placeMark=" + ((placeMark != null) ? placeMark.toString() : null));
			if (errInfo != null) {
				Log.e(LOG_TAG, "Failed to findPlacemarkAtLocation: error=" + errInfo.toString());
				Toast.makeText(MapActivity.this, errInfo.toString(), Toast.LENGTH_LONG).show();
				return;
			}
			if (mFloatingPOIitem != null && mFloatingPOIdataOverlay != null) {
				mFloatingPOIdataOverlay.deselectFocusedPOIitem();
				if (placeMark != null)
					mFloatingPOIitem.setTitle(placeMark.toString());
				mFloatingPOIdataOverlay.selectPOIitemBy(mFloatingPOIitem.getId(), false);
			}
		}
	};

	private final NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {
		public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
			if (errorInfo == null) { // success
				restoreInstanceState();
			} else { // fail
				Log.e(LOG_TAG, "onFailedToInitializeWithError: " + errorInfo.toString());
				Toast.makeText(MapActivity.this, errorInfo.toString(), Toast.LENGTH_LONG).show();
			}
		}

		public void onAnimationStateChange(NMapView mapView, int animType, int animState) {
			if (DEBUG)
				Log.i(LOG_TAG, "onAnimationStateChange: animType=" + animType + ", animState=" + animState);
		}

		public void onMapCenterChange(NMapView mapView, NGeoPoint center) {
			if (DEBUG)
				Log.i(LOG_TAG, "onMapCenterChange: center=" + center.toString());
		}

		public void onZoomLevelChange(NMapView mapView, int level) {
			if (DEBUG)
				Log.i(LOG_TAG, "onZoomLevelChange: level=" + level);
		}

		public void onMapCenterChangeFine(NMapView mapView) {
		}
	};

	private final NMapView.OnMapViewDelegate onMapViewTouchDelegate = new NMapView.OnMapViewDelegate() {
		public boolean isLocationTracking() {
			if (mMapLocationManager != null)
				if (mMapLocationManager.isMyLocationEnabled())
					return mMapLocationManager.isMyLocationFixed();
			return false;
		}
	};

	private final NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener = new NMapOverlayManager.OnCalloutOverlayListener() {
		public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem, Rect itemBounds) {
			if (itemOverlay instanceof NMapPOIdataOverlay) {
				NMapPOIdataOverlay poiDataOverlay = (NMapPOIdataOverlay) itemOverlay;
				if (!poiDataOverlay.isFocusedBySelectItem()) {
					int countOfOverlappedItems = 1;
					NMapPOIdata poiData = poiDataOverlay.getPOIdata();
					for (int i = 0; i < poiData.count(); i++) {
						NMapPOIitem poiItem = poiData.getPOIitem(i);
						if (poiItem == overlayItem)
							continue;
						if (Rect.intersects(poiItem.getBoundsInScreen(), overlayItem.getBoundsInScreen()))
							countOfOverlappedItems++;
					}
					if (countOfOverlappedItems > 1) {
						Toast.makeText(MapActivity.this, countOfOverlappedItems + " overlapped items for " + overlayItem.getTitle(),
								Toast.LENGTH_LONG).show();
						return null;
					}
				}
			}

			mMapController.animateTo(overlayItem.getPoint());
			return null;
		}
	};

	private void restoreInstanceState() {
		mPreferences = getPreferences(MODE_PRIVATE);
		int longitudeE6 = mPreferences.getInt(KEY_CENTER_LONGITUDE, NMAP_LOCATION_DEFAULT.getLongitudeE6());
		int latitudeE6 = mPreferences.getInt(KEY_CENTER_LATITUDE, NMAP_LOCATION_DEFAULT.getLatitudeE6());
		int level = mPreferences.getInt(KEY_ZOOM_LEVEL, NMAP_ZOOMLEVEL_DEFAULT);
		mMapController.setMapCenter(new NGeoPoint(longitudeE6, latitudeE6), level);
	}

	private void saveInstanceState() {
		if (mPreferences == null)
			return;
		NGeoPoint center = mMapController.getMapCenter();
		int level = mMapController.getZoomLevel();
		SharedPreferences.Editor edit = mPreferences.edit();
		edit.putInt(KEY_CENTER_LONGITUDE, center.getLongitudeE6());
		edit.putInt(KEY_CENTER_LATITUDE, center.getLatitudeE6());
		edit.putInt(KEY_ZOOM_LEVEL, level);
		edit.commit();
	}
}
