package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.R;

public class PostsActivity extends FrameActivity{
	
	protected void loadContent() {
		mInflater.inflate(R.layout.posts, mViewgroup);
		mButtonPosts.setClickable(false);
		
		
		
	}
	
}