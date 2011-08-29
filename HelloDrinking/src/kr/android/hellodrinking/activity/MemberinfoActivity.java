package kr.android.hellodrinking.activity;

import java.io.File;

import kr.android.hellodrinking.HelloDrinkingApplication;
import kr.android.hellodrinking.R;
import kr.android.hellodrinking.transmission.Request;
import kr.android.hellodrinking.transmission.dto.ResponceBeanPackege;
import kr.android.hellodrinking.transmission.dto.UserBean;
import kr.android.hellodrinking.utillity.GraphicUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MemberinfoActivity extends Activity implements OnClickListener {
	private static final int CAMERA_PIC_REQUEST = 1337;

	private Button mButtonSend;
	private Button mButtonTakePhoto;

	private EditText mEditId;
	private EditText mEditPw;
	private EditText mEditPw2;
	private EditText mEditName;
	private EditText mEditAge;
	private EditText mEditSex;
	private EditText mEditPhone;
	private EditText mEditJob;
	private ImageView mImagePhoto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.memberinfo);

		mEditAge = (EditText) findViewById(R.id.memberinfo_edit_age);
		mEditId = (EditText) findViewById(R.id.memberinfo_edit_id);
		mEditJob = (EditText) findViewById(R.id.memberinfo_edit_job);
		mEditName = (EditText) findViewById(R.id.memberinfo_edit_name);
		mEditPhone = (EditText) findViewById(R.id.memberinfo_edit_phone);
		mEditPw = (EditText) findViewById(R.id.memberinfo_edit_pw);
		mEditPw2 = (EditText) findViewById(R.id.memberinfo_edit_pw2);
		mEditSex = (EditText) findViewById(R.id.memberinfo_edit_sex);
		mImagePhoto = (ImageView) findViewById(R.id.memberinfo_image);
		mButtonSend = (Button) findViewById(R.id.memberinfo_button_send);
		mButtonTakePhoto = (Button) findViewById(R.id.memberinfo_button_takephoto);
		mButtonSend.setOnClickListener(this);
		mButtonTakePhoto.setOnClickListener(this);

		loadUser();
	}

	private void loadUser() {
		Request request = new Request(HelloDrinkingApplication.mServerIp, HelloDrinkingApplication.mServerPort);
		ResponceBeanPackege responce = null;
		try {
			responce = request.getUser(((HelloDrinkingApplication) getApplication()).getId());
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "인터넷 연결이 바르지 않습니다.", Toast.LENGTH_SHORT).show();
		} finally {
			request.close();
		}

		if (responce == null) {
			Toast.makeText(this, "통신이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
			onBackPressed();
		}
		if (responce.isSuccessed()) {
			UserBean user = (UserBean) responce.getObject();
			mEditAge.setText(user.getAge());
			mEditId.setText(user.getId());
			mEditJob.setText(user.getJob());
			mEditName.setText(user.getName());
			mEditPhone.setText(user.getPhone());
			mEditSex.setText(user.getSex());

			File file = new File(user.getImageFilePath());
			if (!file.getName().equals("")) {
				File imagefile = GraphicUtils.createImageFile(this, user.getBuffer(), file);
				Bitmap bitmap = GraphicUtils.createBitmapFromImageFile(imagefile);
				user.setImageFilePath(imagefile.getAbsolutePath());
				mImagePhoto.setImageBitmap(bitmap);
			}
		} else {
			Toast.makeText(this, responce.getException().getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			if (data != null) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				if (thumbnail != null) {
					mImagePhoto.setImageBitmap(thumbnail);
				}
			}
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.memberinfo_button_takephoto) {
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		} else if (view.getId() == R.id.memberinfo_button_send) {
			String id = mEditId.getText().toString().trim();
			String password = mEditPw.getText().toString().trim();
			String password2 = mEditPw2.getText().toString().trim();
			String name = mEditName.getText().toString().trim();
			String age = mEditAge.getText().toString().trim();
			String sex = mEditSex.getText().toString().trim();
			String phone = mEditPhone.getText().toString().trim();
			String job = mEditJob.getText().toString().trim();
			File imagefile = null;

			if (id.length() < 1) {
				Toast.makeText(this, "ID가 비어 있습니다.", Toast.LENGTH_SHORT).show();
				mEditId.requestFocus();
				return;
			}
			if (password.length() < 1) {
				Toast.makeText(this, "Password가 비어 있습니다.", Toast.LENGTH_SHORT).show();
				mEditPw.requestFocus();
				return;
			}
			if (!password.equals(password2)) {
				Toast.makeText(this, "Password가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
				mEditPw.setText("");
				mEditPw2.setText("");
				return;
			}

			imagefile = GraphicUtils.createTempImageFile(this, mImagePhoto.getDrawable());

			Request request = new Request(HelloDrinkingApplication.mServerIp, HelloDrinkingApplication.mServerPort);
			ResponceBeanPackege responce = null;
			try {
				responce = request.modifyUser(id, name, password, age, sex, phone, job, imagefile.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "인터넷 연결이 바르지 않습니다.", Toast.LENGTH_SHORT).show();
			}
			request.close();
			if (responce.isSuccessed()) {
				((HelloDrinkingApplication) getApplication()).setId(id);
				finish();
			} else {
				Toast.makeText(this, responce.getException().getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}
}
