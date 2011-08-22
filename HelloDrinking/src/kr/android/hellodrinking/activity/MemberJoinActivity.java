package kr.android.hellodrinking.activity;

import kr.android.hellodrinking.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MemberJoinActivity extends Activity implements OnClickListener {
	private static final int CAMERA_PIC_REQUEST = 1337;

	private Button mButtonTakePhoto;
	private Button mButtonSend;
	private EditText mEditId;
	private EditText mEditPw;
	private EditText mEditPw2;
	private EditText mEditName;
	private EditText mEditAge;
	private EditText mEditSex;
	private EditText mEditPhone;
	private EditText mEditJob;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memberjoin);

		mButtonTakePhoto = (Button) findViewById(R.id.memberjoin_button_takephoto);
		mButtonSend = (Button) findViewById(R.id.memberjoin_button_send);

		mEditId = (EditText) findViewById(R.id.memberjoin_edit_id);
		mEditPw = (EditText) findViewById(R.id.memberjoin_edit_pw);
		mEditPw2 = (EditText) findViewById(R.id.memberjoin_edit_pw2);
		mEditName = (EditText) findViewById(R.id.memberjoin_edit_name);
		mEditAge = (EditText) findViewById(R.id.memberjoin_edit_age);
		mEditSex = (EditText) findViewById(R.id.memberjoin_edit_sex);
		mEditPhone = (EditText) findViewById(R.id.memberjoin_edit_phone);
		mEditJob = (EditText) findViewById(R.id.memberjoin_edit_job);
		
		mButtonTakePhoto.setOnClickListener(this);
		mButtonSend.setOnClickListener(this);
		
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			if (data != null) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				if (thumbnail != null) {
					ImageView image = (ImageView) findViewById(R.id.memberjoin_image);
					image.setImageBitmap(thumbnail);
				}
			}
		}
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.memberjoin_button_takephoto){
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		}else if(view.getId() == R.id.memberjoin_button_send){
			//////////예외 확인
			//////////Bitmap 객체 -> 파일 저장
			
//			Request fc = new Request();
//			fc.register(mEditId.getText().toString(), 
//					mEditName.getText().toString(), 
//					mEditPw.getText().toString(), 
//					mEditAge.getText().toString(), 
//					mEditSex.getText().toString(), 
//					mEditPhone.getText().toString(), 
//					mEditJob.getText().toString());
		}
	}
}