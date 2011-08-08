package kr.co.hellodrinking.activity;

import android.view.ViewGroup;
import kr.co.hellodrinking.R;

public class SampleFrameActivity extends FrameActivity{
	@Override
	void loadContent() {
		inflater.inflate(R.layout.postslist, (ViewGroup) findViewById(R.id.frame_layout_contents));
		
	}
}
