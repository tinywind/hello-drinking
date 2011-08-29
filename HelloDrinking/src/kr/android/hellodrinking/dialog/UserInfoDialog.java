package kr.android.hellodrinking.dialog;

import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.PostBean;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoDialog extends Activity implements OnClickListener {
	private ImageView mImage;
	private TextView mTextId;
	private TextView mTextComment;
	private Button mButtonCall;
	private Button mButtonLetter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo);

		mImage = (ImageView) findViewById(R.id.userinfo_imageview);
		mTextId = (TextView) findViewById(R.id.userinfo_textview_id);
		mTextComment = (TextView) findViewById(R.id.userinfo_textview_comment);
		mButtonCall = (Button) findViewById(R.id.userinfo_button_call);
		mButtonLetter = (Button) findViewById(R.id.userinfo_button_letter);

		PostBean post = (PostBean) getIntent().getSerializableExtra("kr.android.hellodrinking.POST");

		mImage.setImageBitmap(BitmapFactory.decodeFile(post.getImageFilePath()));
		mTextId.setText(post.getId());
		mTextComment.setText(post.getComment());

		mButtonCall.setOnClickListener(this);
		mButtonLetter.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Request request = new Request();
		UserBean user = null;
		try {
			ResponceBeanPackege responce = request.getUser(mTextId.getText().toString());
			user = (UserBean) responce.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "인터넷 연결이 옳바르지 않습니다.", Toast.LENGTH_SHORT);
			return;
		}
		if (view.getId() == R.id.userinfo_button_call) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.getPhone()));
			startActivity(intent);
		} else if (view.getId() == R.id.userinfo_button_letter) {
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + user.getPhone()));
			startActivity(intent);
		}
	}
}
