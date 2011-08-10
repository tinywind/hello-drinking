package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;

public class MapActivity extends FrameActivity {

	@Override
	protected void loadContent() {
		inflater.inflate(R.layout.map, viewgroup);	
		buttonMap.setClickable(false);
	}
}