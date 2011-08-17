package kr.co.hellodrinking.activity;

import kr.co.hellodrinking.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoDialog extends Activity{
	private ImageView mImage;
	private TextView mTextName;
	private TextView mTextComment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);
		
		mImage = (ImageView) findViewById(R.id.userinfo_imageview);
		mTextName = (TextView) findViewById(R.id.userinfo_textview_name);
		mTextComment = (TextView) findViewById(R.id.userinfo_textview_comment);
		
		
		mTextName.setText("전재형");
		mTextComment.setText("오늘 완전 시간 많음!!");
	}
}
