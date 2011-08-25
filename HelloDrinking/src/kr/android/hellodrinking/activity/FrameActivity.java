package kr.android.hellodrinking.activity;

import com.nhn.android.maps.NMapActivity;

import kr.android.hellodrinking.R;
import kr.android.hellodrinking.ar.ARActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public abstract class FrameActivity extends Activity implements OnClickListener {
	protected LayoutInflater mInflater;
	protected ViewGroup mViewgroup;
	protected ImageButton mButtonPosts, mButtonMap, mButtonAR, mButtonMember;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.frame);

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mViewgroup = (ViewGroup) findViewById(R.id.frame_frame_contents);

		mButtonPosts = (ImageButton) findViewById(R.id.frame_button_posts);
		mButtonMap = (ImageButton) findViewById(R.id.frame_button_map);
		mButtonAR = (ImageButton) findViewById(R.id.frame_button_ar);
		mButtonMember = (ImageButton) findViewById(R.id.frame_button_member);

		mButtonPosts.setOnClickListener(this);
		mButtonMap.setOnClickListener(this);
		mButtonAR.setOnClickListener(this);
		mButtonMember.setOnClickListener(this);

		loadContent();
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.frame_button_posts) {
			startActivity(new Intent(this, PostsActivity.class));
		} else if (view.getId() == R.id.frame_button_map) {
			startActivity(new Intent(this, MapActivity.class));
		} else if (view.getId() == R.id.frame_button_ar) {
			startActivity(new Intent(this, ARActivity.class));
		} else if (view.getId() == R.id.frame_button_member) {
			startActivity(new Intent(this, MemberinfoActivity.class));
		}
	}

	protected abstract void loadContent();
}
