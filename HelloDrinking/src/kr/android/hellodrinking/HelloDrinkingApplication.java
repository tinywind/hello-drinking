package kr.android.hellodrinking;


import java.util.ArrayList;
import java.util.List;

import kr.android.hellodrinking.ar.POI;
import android.app.Application;

public class HelloDrinkingApplication extends Application {
	public static final String DEFAULT_SERVER = "192.168.17.139";
	public static final int DEFAULT_PORT = 5557;
	
	private List<POI> mListPOIs;
	
	public HelloDrinkingApplication(){
		super();
		mListPOIs = new ArrayList<POI>();
		
		/////Test Part//////////
		POI testPOI = new POI(38, 127);
		testPOI.setIconuri("/sdcard/dcim/camera/1302919924624.jpg");
		testPOI.setName("전재형");
		testPOI.setComment("오늘 시간 많아요. 암컷 고양이 구함!");
		addPOI(testPOI);
		
		POI testPOI2 = new POI(37.5, 127);
		testPOI2.setIconuri("/sdcard/dcim/camera/1308565308004.jpg");
		testPOI2.setName("정길수");
		testPOI2.setComment("같이 망가보실분");
		addPOI(testPOI2);

		POI testPOI3 = new POI(37, 126.5);
		testPOI3.setIconuri("/sdcard/dcim/camera/1306249807793.jpg");
		testPOI3.setName("정찬규");
		testPOI3.setComment("난 취직했음.");
		addPOI(testPOI3);
		/////Test Part//////////
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
