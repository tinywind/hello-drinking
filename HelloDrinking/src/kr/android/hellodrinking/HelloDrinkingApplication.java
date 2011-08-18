package kr.android.hellodrinking;


import java.util.ArrayList;
import java.util.List;

import kr.android.hellodrinking.ar.POI;
import android.app.Application;

public class HelloDrinkingApplication extends Application {
	private List<POI> mListPOIs;
	
	public HelloDrinkingApplication(){
		super();
		mListPOIs = new ArrayList<POI>();
	}
	
	public void addPOI(POI poi){
		mListPOIs.add(poi);
	}
	
	public void removePOI(POI poi){
		mListPOIs.remove(poi);
	}
	
	public List<POI> getListPOIs(){
		return mListPOIs;
	}

}
