package kr.android.hellodrinking.activity;

import java.util.List;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.ar.POI;
import kr.android.hellodrinking.map.PostsListener;
import kr.android.hellodrinking.map.PostsModel;
import kr.android.hellodrinking.map.ValueChangeEvent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCalloutBasicOverlay;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
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

public class MapActivity extends NMapActivity implements PostsListener {
	private static final String LOG_TAG = "NMapViewer";
	private static final boolean DEBUG = false;
	private static final String API_KEY = "6dff5a14ec0abea130009332080cc6fc";
	private static Drawable DEFAULT_IMAGE;
	private MapContainerView mMapContainerView;
	private NMapView mMapView;
	private NMapController mMapController;
	private static final NGeoPoint NMAP_LOCATION_DEFAULT = new NGeoPoint(126.978371, 37.5666091);
	private static final int NMAP_ZOOMLEVEL_DEFAULT = 11;
	private static final String KEY_ZOOM_LEVEL = "NMapViewer.zoomLevel";
	private static final String KEY_CENTER_LONGITUDE = "NMapViewer.centerLongitudeE6";
	private static final String KEY_CENTER_LATITUDE = "NMapViewer.centerLatitudeE6";
	private static final int POIOVERLAY_IMAGE_WIDTH = 150;
	private static final int POIOVERLAY_IMAGE_HEIGHT = 150;
	private SharedPreferences mPreferences;
	private NMapOverlayManager mOverlayManager;
	private NMapMyLocationOverlay mMyLocationOverlay;
	private NMapLocationManager mMapLocationManager;
	private NMapCompassManager mMapCompassManager;
	private NMapViewerResourceProvider mMapViewerResourceProvider;
	private NMapPOIdataOverlay mFloatingPOIdataOverlay;
	private NMapPOIitem mFloatingPOIitem;
	private NMapPOIdataOverlay mPoiDataOverlay;
	private PostsModel mPostsModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMapView = new NMapView(this);
		mMapView.setApiKey(API_KEY);
		mMapView.setClickable(true);
		mMapView.setEnabled(true);
		mMapView.setFocusable(true);
		mMapView.setFocusableInTouchMode(true);
		mMapView.requestFocus();
		mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);
		mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);
		mMapView.setOnMapViewDelegate(onMapViewTouchDelegate);
		mMapView.setBuiltInZoomControls(true, new NMapView.LayoutParams(NMapView.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.WRAP_CONTENT,
				NMapView.LayoutParams.BOTTOM_RIGHT));

		DEFAULT_IMAGE = getResources().getDrawable(R.drawable.ic_pin_01);

		mMapContainerView = new MapContainerView(this);
		mMapContainerView.addView(mMapView);
		setContentView(mMapContainerView);

		mMapController = mMapView.getMapController();
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
		setMapDataProviderListener(onDataProviderListener);
		mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
		mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
		mMapLocationManager = new NMapLocationManager(this);
		mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

		mMapCompassManager = new NMapCompassManager(this);
		mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

		startMyLocation();
		setModel();
	}

	private void setModel() {
		mPostsModel = new PostsModel(((HelloDrinkingApplication) getApplication()).getListPOIs());
		mPostsModel.addListener(this);
		modelChanged(new ValueChangeEvent(mPostsModel));
	}

	@Override
	protected void onStop() {
		stopMyLocation();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		saveInstanceState();
		super.onDestroy();
	}

	private void startMyLocation() {
		if (mMyLocationOverlay != null) {
			if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
				mOverlayManager.addOverlay(mMyLocationOverlay);
			}
			if (mMapLocationManager.isMyLocationEnabled()) {
				if (!mMapView.isAutoRotateEnabled()) {
					mMyLocationOverlay.setCompassHeadingVisible(true);
					mMapCompassManager.enableCompass();
					mMapView.setAutoRotateEnabled(true, false);
					mMapContainerView.requestLayout();
				} else {
					stopMyLocation();
				}
				mMapView.postInvalidate();
			} else {
				boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false);
				if (!isMyLocationEnabled) {
					Toast.makeText(MapActivity.this, "Please enable a My Location source in system settings", Toast.LENGTH_LONG).show();
					Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(goToSettings);
					return;
				}
			}
		}
	}

	private void stopMyLocation() {
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
			if(item == null)
				return;
			Intent intent = new Intent(MapActivity.this, UserInfoDialog.class);
			intent.putExtra("kr.android.POI", (POI) item.getTag());
			startActivity(intent);
		}
	};

	/* NMapDataProvider Listener */
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

	/* MyLocation Listener */
	private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {
		public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {
			if (mMapController != null)
				mMapController.animateTo(myLocation);
			return true;
		}

		public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
			stopMyLocation();
			Toast.makeText(MapActivity.this, "Your current location is temporarily unavailable", Toast.LENGTH_LONG).show();
		}
	};

	/* MapView State Change Listener */
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

	private final NMapView.OnMapViewTouchEventListener onMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {
		public void onLongPress(NMapView mapView, MotionEvent ev) {
		}

		public void onLongPressCanceled(NMapView mapView) {
		}

		public void onSingleTapUp(NMapView mapView, MotionEvent ev) {
		}

		public void onTouchDown(NMapView mapView, MotionEvent ev) {
		}

		public void onScroll(NMapView mapView, MotionEvent e1, MotionEvent e2) {
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
				// check if it is selected by touch event
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
			// return new NMapCalloutCustomOverlay(itemOverlay, overlayItem,
			// itemBounds, mMapViewerResourceProvider);
			return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);
		}
	};

	/* Local Functions */
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

	/* Menus */
	private static final int MENU_ITEM_CLEAR_MAP = 10;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem menuItem = menu.add(Menu.NONE, MENU_ITEM_CLEAR_MAP, Menu.CATEGORY_SECONDARY, "Clear Map");
		menuItem.setIcon(android.R.drawable.ic_menu_revert);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu pMenu) {
		super.onPrepareOptionsMenu(pMenu);
		pMenu.findItem(MENU_ITEM_CLEAR_MAP).setEnabled(mOverlayManager.sizeofOverlays() > 0);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ITEM_CLEAR_MAP:
			if (mMyLocationOverlay != null)
				mOverlayManager.removeOverlay(mMyLocationOverlay);
			mMapController.setMapViewMode(NMapView.VIEW_MODE_VECTOR);
			mMapController.setMapViewTrafficMode(false);
			mMapController.setMapViewBicycleMode(false);
			mOverlayManager.clearOverlays();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class MapContainerView extends ViewGroup {
		public MapContainerView(Context context) {
			super(context);
		}

		@Override
		protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
			final int width = getWidth();
			final int height = getHeight();
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View view = getChildAt(i);
				final int childWidth = view.getMeasuredWidth();
				final int childHeight = view.getMeasuredHeight();
				final int childLeft = (width - childWidth) / 2;
				final int childTop = (height - childHeight) / 2;
				view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
			}
			if (changed)
				mOverlayManager.onSizeChanged(width, height);
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
			int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
			int sizeSpecWidth = widthMeasureSpec;
			int sizeSpecHeight = heightMeasureSpec;

			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View view = getChildAt(i);
				if (view instanceof NMapView) {
					if (mMapView.isAutoRotateEnabled()) {
						int diag = (((int) (Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
						sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
						sizeSpecHeight = sizeSpecWidth;
					}
				}
				view.measure(sizeSpecWidth, sizeSpecHeight);
			}
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	public void modelChanged(ValueChangeEvent e) {
		List<POI> list = ((PostsModel) e.getSource()).getModel();

		NMapPOIdata poiData = new NMapPOIdata(list.size(), mMapViewerResourceProvider);
		poiData.beginPOIdata(list.size());
		for (int index = 0; index < list.size(); index++) {
			POI poi = list.get(index);

			Drawable image;
			Bitmap bitmap = BitmapFactory.decodeFile(poi.getImageFilePath());
			if (bitmap != null) {
				bitmap = Bitmap.createScaledBitmap(bitmap, POIOVERLAY_IMAGE_WIDTH, POIOVERLAY_IMAGE_HEIGHT, true);
				image = new BitmapDrawable(bitmap);
			} else
				image = DEFAULT_IMAGE;
			image.setAlpha(180);

			poiData.addPOIitem(new NGeoPoint(poi.getLongitude(), poi.getLatitude()), poi.getName(), image, poi);
		}
		poiData.endPOIdata();

		mPoiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
		mPoiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
		mPoiDataOverlay.selectPOIitem(0, true);
	}

	@Override
	public void postAdded(ValueChangeEvent e) {
	}

	@Override
	public void postRemoved(ValueChangeEvent e) {
	}
}