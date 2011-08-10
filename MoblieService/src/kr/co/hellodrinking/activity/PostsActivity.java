package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;

public class PostsActivity extends FrameActivity{

	protected void loadContent() {
		inflater.inflate(R.layout.posts, viewgroup);
		buttonPosts.setClickable(false);
	}
}