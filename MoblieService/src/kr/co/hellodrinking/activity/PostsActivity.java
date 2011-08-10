package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;

public class PostsActivity extends FrameActivity{

	protected void loadContent() {
		mInflater.inflate(R.layout.posts, mViewgroup);
		mButtonPosts.setClickable(false);
	}
}