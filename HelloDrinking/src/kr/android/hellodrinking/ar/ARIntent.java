package kr.android.hellodrinking.ar;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.app.Application;

public class ARIntent extends AbstractARIntent {

	private ArrayList<POI> mListPOIs = new ArrayList<POI>();
	private String[] name;
	private String[] link;
	private String[] description;
	private String[] iconresource;
	private String[] iconuri;
	private String[] detailAction;
	
	public ARIntent(Application application) {
		super(application);
	}
	
	public ARIntent(Application application, String applicationName) {
		super(application, applicationName);
	}
	
	public boolean addPOI(POI poi) {
		if (poi == null) {
			throw new IllegalArgumentException("Parameter poi must not be null.");
		}
		checkPoisForNull();
		boolean result = this.mListPOIs.add(poi);
		commitChangeToIntent();
		return result;
	}

	public boolean addPOIs(Collection<POI> pois) {
		if (pois == null) {
			throw new IllegalArgumentException("Parameter pois must not be null.");
		}
		checkPoisForNull();
		boolean result = this.mListPOIs.addAll(pois);
		commitChangeToIntent();
		return result;
	}

	public boolean removePOI(POI poi) {
		checkPoisForNull();
		boolean result = this.mListPOIs.remove(poi);
		commitChangeToIntent();
		return result;
	}

	public POI removePOI(int position) {
		checkPoisForNull();
		POI result = (POI) this.mListPOIs.remove(position);
		commitChangeToIntent();
		return result;
	}

	public int getPOIsSize() {
		checkPoisForNull();
		return this.mListPOIs.size();
	}

	public POI getPOI(int position) {
		checkPoisForNull();
		return ((POI) this.mListPOIs.get(position));
	}

	public ArrayList<POI> getPOIs() {
		checkPoisForNull();
		return this.mListPOIs;
	}

	protected void commitChangeToIntent() {
		checkPoisForNull();
		setArrays(this.mListPOIs.size());
		for (int i = 0; i < this.mListPOIs.size(); ++i) {
			POI poi = (POI) this.mListPOIs.get(i);
			if (poi != null) {
				this.latitude[i] = new Float(poi.getLatitude()).floatValue();
				this.longitude[i] = new Float(poi.getLongitude()).floatValue();
				this.altitude[i] = new Float(poi.getAltitude()).floatValue();
				this.name[i] = poi.getName();				
				this.detailAction[i] = poi.getDetailAction();
				this.link[i] = poi.getLink();
				this.description[i] = poi.getDescription();
				this.iconresource[i] = poi.getIconresource();
				this.iconuri[i] = poi.getIconuri();
			}
		}
		putExtra("kr.android.hellodrinking.extra.NAME_ARRAY", this.name);
		putExtra("kr.android.hellodrinking.extra.LINKS_ARRAY", this.link);
		putExtra("com.mobilizy.wikitude.DETAIL_ACTION_ARRAY", this.detailAction);
		putExtra("kr.android.hellodrinking.extra.DESCRIPTION_ARRAY", this.description);
		putExtra("kr.android.hellodrinking.extra.ICON_RESOURCE_ARRAY", this.iconresource);
		putExtra("kr.android.hellodrinking.extra.ICON_URI_ARRAY", this.iconuri);
		super.commitChangeToIntent();
	}

	private void checkPoisForNull() {
		if (this.mListPOIs == null)
			this.mListPOIs = new ArrayList<POI>();
	}

	protected void setArrays(int size) {
		super.setArrays(size);
		this.name = new String[size];
		this.detailAction = new String[size];
		this.link = new String[size];
		this.description = new String[size];
		this.iconresource = new String[size];
		this.iconuri = new String[size];
	}

	public void setPrintMarkerSubText(boolean printMarkerSubText) {
		putExtra("com.mobilizy.wikitude.DRAW_MARKER_SUB_TEXT",printMarkerSubText);
	}

	public void startIntent(Activity activity) {
		commitChangeToIntent();
		activity.startActivity(this);
	}
}
