package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.R;
import kr.android.hellodrinking.ar.POI;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoDialog extends Activity {
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

		POI poi = (POI) getIntent().getSerializableExtra("kr.android.POI");
		
		mImage.setImageBitmap(BitmapFactory.decodeFile(poi.getImageFilePath()));
		mTextName.setText(poi.getName());
		mTextComment.setText(poi.getComment());
	}
}
