package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.R;
import android.view.ViewGroup;

public class SampleFrameActivity extends FrameActivity{

	protected void loadContent() {
		mInflater.inflate(R.layout.posts, (ViewGroup) findViewById(R.id.frame_linear_contents));
		
	}
}
