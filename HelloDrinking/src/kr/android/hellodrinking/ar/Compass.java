package kr.android.hellodrinking.ar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Compass {
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private List<SensorEventListener> mListSensorListeners;

	public Compass(Context context) {
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		mListSensorListeners = new ArrayList<SensorEventListener>();
	}
	
	public void addListener(SensorEventListener listener){
		mListSensorListeners.add(listener);
	}
	
	public void removeListener(SensorEventListener listener){
		mListSensorListeners.remove(listener);
	}
	
	public void start() {
		Iterator<SensorEventListener> it = mListSensorListeners.iterator();
		while(it.hasNext()){
			mSensorManager.registerListener(it.next(), mSensor,	SensorManager.SENSOR_DELAY_NORMAL);
		}		
	}

	public void stop() {
		Iterator<SensorEventListener> it = mListSensorListeners.iterator();
		while(it.hasNext()){
			mSensorManager.unregisterListener(it.next());
		}
	}
}
