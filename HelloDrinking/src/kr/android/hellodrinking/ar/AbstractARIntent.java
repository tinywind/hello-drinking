package kr.android.hellodrinking.ar;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public abstract class AbstractARIntent extends Intent {
	protected float[] latitude;
	protected float[] longitude;
	protected float[] altitude;

	protected AbstractARIntent(Application application) {
		super("kr.android.hellodrinking.action.AR_VIEW");
		if (application == null) {
			throw new IllegalArgumentException(
					"Application-Parameter must not be null.");
		}
		setUpIntent(application, null);		
	}

	protected AbstractARIntent(Application application,String applicationName) {
		super("kr.android.hellodrinking.action.AR_VIEW");
		if (application == null) {
			throw new IllegalArgumentException("Application-Parameter must not be null.");
		}
		setUpIntent(application, applicationName);
	}

	protected void setUpIntent(Application application, String applicationName) {
		if (applicationName == null) {
			try {
				Context ctx = application.getApplicationContext();
				applicationName = application.getText(application.getResources().getIdentifier("app_name","string", ctx.getPackageName())).toString();
			} catch (Exception e) {
				throw new IllegalArgumentException("String-value 'app_name' must be given. Please check the resource files in your application.");
			}
		}
		addApplicationName(applicationName);
	}

	protected void addApplicationName(String name) {
		putExtra("kr.android.hellodrinking.extra.APPLICATION_NAME", name);
	}

	public void addTitleImageResource(String imageresource) {
		putExtra("kr.android.hellodrinking.extra.TITLE_IMAGE_RESOURCE", imageresource);
	}

	public void addTitleImageUri(String imageUri) {
		putExtra("kr.android.hellodrinking.extra.TITLE_IMAGE_URI", imageUri);
	}

	protected void setArrays(int size) {
		this.latitude = new float[size];
		this.longitude = new float[size];
		this.altitude = new float[size];
	}

	public abstract void startIntent(Activity paramActivity);

	protected void commitChangeToIntent() {
		putExtra("kr.android.hellodrinking.extra.LATITUDE_ARRAY", this.latitude);
		putExtra("kr.android.hellodrinking.extra.LONGITUDE_ARRAY", this.longitude);
		putExtra("kr.android.hellodrinking.extra.ALTITUDE_ARRAY", this.altitude);
	}

}
