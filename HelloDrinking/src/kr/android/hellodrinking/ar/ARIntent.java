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
	private String[] imagefilepath;
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
				this.name[i] = poi.getName();				
				this.detailAction[i] = poi.getComment();
				this.link[i] = poi.getLink();
				this.description[i] = poi.getDescription();
				this.imagefilepath[i] = poi.getImageFilePath();
			}
		}
		putExtra("kr.android.extra.NAME_ARRAY", this.name);
		putExtra("kr.android.extra.LINKS_ARRAY", this.link);
		putExtra("kr.android.extra.DETAIL_ACTION_ARRAY", this.detailAction);
		putExtra("kr.android.extra.DESCRIPTION_ARRAY", this.description);
		putExtra("kr.android.extra.IMAGE_FILE_PATH_ARRAY", this.imagefilepath);
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
		this.imagefilepath = new String[size];
	}

	public void setPrintMarkerSubText(boolean printMarkerSubText) {
		putExtra("com.mobilizy.wikitude.DRAW_MARKER_SUB_TEXT",printMarkerSubText);
	}

	public void startIntent(Activity activity) {
		commitChangeToIntent();
		activity.startActivity(this);
	}
}
