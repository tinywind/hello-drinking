package kr.co.hellodrinking.activity;

import android.view.View;
import kr.co.hellodrinking.R;

public class ARActivity extends FrameActivity {

	@Override
	protected void loadContent() {
		mInflater.inflate(R.layout.ar, mViewgroup);
		mButtonAR.setClickable(false);
	}
}